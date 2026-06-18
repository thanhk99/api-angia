package api.angia.com.modules.customer.api.v1;

import api.angia.com.modules.customer.api.v1.dto.request.*;
import api.angia.com.modules.customer.api.v1.dto.response.AuthResponse;
import api.angia.com.modules.customer.api.v1.dto.response.CustomerResponse;
import api.angia.com.modules.customer.application.service.CustomerAuthApplicationService;
import api.angia.com.shared.dto.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/customer/auth")
@RequiredArgsConstructor
@Tag(name = "Customer Auth", description = "API xác thực và quản lý thông tin khách hàng")
public class CustomerAuthController {

    private final CustomerAuthApplicationService customerService;

    @PostMapping("/login")
    @Operation(summary = "Đăng nhập khách hàng", description = "Trả về JWT access token và refresh token")
    public ResponseEntity<ApiResponse<AuthResponse>> login(
            @Valid @RequestBody LoginRequest request) {
        AuthResponse response = customerService.login(request);
        return ResponseEntity.ok(ApiResponse.success("Đăng nhập thành công", response));
    }

    @PostMapping("/refresh")
    @Operation(summary = "Làm mới Access Token", description = "Sử dụng Refresh Token để lấy Access Token mới")
    public ResponseEntity<ApiResponse<AuthResponse>> refreshToken(
            @Valid @RequestBody RefreshTokenRequest request) {
        AuthResponse response = customerService.refreshToken(request.getRefreshToken());
        return ResponseEntity.ok(ApiResponse.success("Làm mới token thành công", response));
    }

    @PostMapping("/logout")
    @Operation(summary = "Đăng xuất", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<ApiResponse<Void>> logout(
            @RequestHeader(value = "Authorization", required = false) String bearerToken) {
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            String accessToken = bearerToken.substring(7);
            customerService.logout(accessToken);
        }
        return ResponseEntity.ok(ApiResponse.success("Đăng xuất thành công", null));
    }

    @PostMapping("/register")
    @Operation(summary = "Đăng ký tài khoản khách hàng")
    public ResponseEntity<ApiResponse<CustomerResponse>> register(
            @Valid @RequestBody RegisterRequest request) {
        CustomerResponse response = customerService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Đăng ký thành công", response));
    }

    @GetMapping("/me")
    @Operation(summary = "Lấy thông tin cá nhân", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<ApiResponse<CustomerResponse>> getProfile(Authentication auth) {
        CustomerResponse response = customerService.getProfile(auth.getName());
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @PutMapping("/me")
    @Operation(summary = "Cập nhật thông tin cá nhân", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<ApiResponse<CustomerResponse>> updateProfile(
            Authentication auth,
            @Valid @RequestBody UpdateProfileRequest request) {
        CustomerResponse response = customerService.updateProfile(auth.getName(), request);
        return ResponseEntity.ok(ApiResponse.success("Cập nhật thành công", response));
    }

    @PatchMapping("/me/password")
    @Operation(summary = "Đổi mật khẩu", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<ApiResponse<Void>> changePassword(
            Authentication auth,
            @Valid @RequestBody ChangePasswordRequest request) {
        customerService.changePassword(auth.getName(), request);
        return ResponseEntity.ok(ApiResponse.success("Đổi mật khẩu thành công", null));
    }
}
