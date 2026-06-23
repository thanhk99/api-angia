package api.angia.com.modules.customer.application.service;

import api.angia.com.modules.customer.api.v1.dto.request.*;
import api.angia.com.modules.customer.api.v1.dto.response.AuthResponse;
import api.angia.com.modules.customer.api.v1.dto.response.CustomerResponse;
import api.angia.com.modules.customer.application.mapper.CustomerMapper;
import api.angia.com.modules.customer.domain.exception.CustomerException;
import api.angia.com.modules.customer.domain.model.Customer;
import api.angia.com.modules.customer.domain.repository.CustomerRepository;
import api.angia.com.shared.security.JwtTokenProvider;
import api.angia.com.shared.token.entity.RefreshTokenEntity;
import api.angia.com.shared.token.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class CustomerAuthApplicationService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final TokenService tokenService;

    // Đăng nhập
    public AuthResponse login(LoginRequest request) {
        Customer customer = customerRepository.findByUsername(request.getUsername())
                .orElseThrow(CustomerException::invalidCredentials);

        if (!customer.isActive()) {
            throw CustomerException.accountDisabled();
        }

        if (!passwordEncoder.matches(request.getPassword(), customer.getPassword())) {
            throw CustomerException.invalidCredentials();
        }

        String token = jwtTokenProvider.generateTokenForUser(
                customer.getUsername(), "ROLE_CUSTOMER"
        );

        String refreshToken = tokenService.createRefreshToken(customer.getId(), "CUSTOMER");

        return AuthResponse.builder()
                .accessToken(token)
                .refreshToken(refreshToken)
                .tokenType("Bearer")
                .expiresIn(jwtTokenProvider.getJwtExpirationMs() / 1000)
                .customer(customerMapper.toResponse(customer))
                .build();
    }

    // Refresh Token
    public AuthResponse refreshToken(String rawRefreshToken) {
        RefreshTokenEntity entity = tokenService.validateAndRotate(rawRefreshToken);

        if (!"CUSTOMER".equals(entity.getOwnerType())) {
            throw CustomerException.unauthorized();
        }

        Customer customer = customerRepository.findById(entity.getOwnerId().toString())
                .orElseThrow(() -> CustomerException.notFoundById(entity.getOwnerId().toString()));

        if (!customer.isActive()) {
            throw CustomerException.accountDisabled();
        }

        String newAccessToken = jwtTokenProvider.generateTokenForUser(
                customer.getUsername(), "ROLE_CUSTOMER"
        );

        String newRefreshToken = tokenService.createRefreshToken(customer.getId(), "CUSTOMER");

        return AuthResponse.builder()
                .accessToken(newAccessToken)
                .refreshToken(newRefreshToken)
                .tokenType("Bearer")
                .expiresIn(jwtTokenProvider.getJwtExpirationMs() / 1000)
                .customer(customerMapper.toResponse(customer))
                .build();
    }

    // Đăng xuất
    public void logout(String rawAccessToken) {
        Date expiresAt = jwtTokenProvider.getExpirationDateFromJwt(rawAccessToken);
        tokenService.blacklistAccessToken(rawAccessToken, expiresAt);
        
        String username = jwtTokenProvider.getUsernameFromJwt(rawAccessToken);
        customerRepository.findByUsername(username).ifPresent(customer -> 
            tokenService.revokeAllRefreshTokens(customer.getId(), "CUSTOMER")
        );
    }

    // Đăng ký
    @Transactional
    public CustomerResponse register(RegisterRequest request) {
        if (customerRepository.existsByUsername(request.getUsername())) {
            throw CustomerException.usernameAlreadyExists(request.getUsername());
        }

        if (request.getEmail() != null && customerRepository.existsByEmail(request.getEmail())) {
            throw CustomerException.emailAlreadyExists(request.getEmail());
        }

        Customer newCustomer = Customer.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .email(request.getEmail())
                .fullName(request.getFullName())
                .provider("local")
                .active(true)
                .build();

        return customerMapper.toResponse(customerRepository.save(newCustomer));
    }

    // Lấy thông tin cá nhân
    @Transactional(readOnly = true)
    public CustomerResponse getProfile(String username) {
        Customer customer = customerRepository.findByUsername(username)
                .orElseThrow(() -> CustomerException.notFound(username));
        return customerMapper.toResponse(customer);
    }

    // Cập nhật thông tin cá nhân
    @Transactional
    public CustomerResponse updateProfile(String username, UpdateProfileRequest request) {
        Customer customer = customerRepository.findByUsername(username)
                .orElseThrow(() -> CustomerException.notFound(username));

        if (request.getEmail() != null
                && !request.getEmail().equals(customer.getEmail())
                && customerRepository.existsByEmail(request.getEmail())) {
            throw CustomerException.emailAlreadyExists(request.getEmail());
        }

        if (request.getFullName() != null) customer.setFullName(request.getFullName());
        if (request.getEmail() != null) customer.setEmail(request.getEmail());

        return customerMapper.toResponse(customerRepository.save(customer));
    }

    // Đổi mật khẩu
    @Transactional
    public void changePassword(String username, ChangePasswordRequest request) {
        Customer customer = customerRepository.findByUsername(username)
                .orElseThrow(() -> CustomerException.notFound(username));

        if (!passwordEncoder.matches(request.getOldPassword(), customer.getPassword())) {
            throw CustomerException.invalidCredentials();
        }

        customer.setPassword(passwordEncoder.encode(request.getNewPassword()));
        customerRepository.save(customer);
    }
}
