package api.angia.com.shared.token.service;

import api.angia.com.shared.security.JwtTokenProvider;
import api.angia.com.shared.token.entity.BlacklistTokenEntity;
import api.angia.com.shared.token.entity.RefreshTokenEntity;
import api.angia.com.shared.token.repository.BlacklistTokenRepository;
import api.angia.com.shared.token.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class TokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final BlacklistTokenRepository blacklistTokenRepository;
    private final JwtTokenProvider jwtTokenProvider;

    /**
     * Tạo Refresh Token mới và lưu vào DB.
     */
    @Transactional
    public String createRefreshToken(Long ownerId, String ownerType) {
        // Dùng UUID làm refresh token cho đơn giản và an toàn
        String rawToken = UUID.randomUUID().toString() + "-" + UUID.randomUUID().toString();
        String tokenHash = jwtTokenProvider.hashToken(rawToken);
        
        // Tính thời gian hết hạn
        LocalDateTime expiresAt = LocalDateTime.now()
                .plus(jwtTokenProvider.getJwtRefreshExpirationMs(), ChronoUnit.MILLIS);

        RefreshTokenEntity entity = RefreshTokenEntity.builder()
                .tokenHash(tokenHash)
                .ownerId(ownerId)
                .ownerType(ownerType)
                .expiresAt(expiresAt)
                .revoked(false)
                .build();

        refreshTokenRepository.save(entity);
        return rawToken;
    }

    /**
     * Xác thực Refresh Token và Rotate (xoay vòng token).
     * Trả về entity cũ để biết owner, đồng thời đánh dấu token cũ là bị thu hồi (revoked).
     */
    @Transactional
    public RefreshTokenEntity validateAndRotate(String rawRefreshToken) {
        String tokenHash = jwtTokenProvider.hashToken(rawRefreshToken);
        
        RefreshTokenEntity entity = refreshTokenRepository.findByTokenHash(tokenHash)
                .orElseThrow(() -> new RuntimeException("Refresh token không tồn tại."));

        if (entity.isRevoked()) {
            // Nếu token đã bị thu hồi mà vẫn được dùng -> Cảnh báo bảo mật!
            // Cần vô hiệu hoá toàn bộ token của user này
            log.warn("Cảnh báo: Phát hiện sử dụng Refresh Token đã bị thu hồi! OwnerId: {}, OwnerType: {}", 
                    entity.getOwnerId(), entity.getOwnerType());
            refreshTokenRepository.revokeAllByOwner(entity.getOwnerId(), entity.getOwnerType());
            throw new RuntimeException("Refresh token không hợp lệ (đã bị thu hồi).");
        }

        if (entity.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Refresh token đã hết hạn.");
        }

        // Đánh dấu token này đã bị thu hồi (Rotation)
        entity.setRevoked(true);
        refreshTokenRepository.save(entity);

        return entity;
    }

    /**
     * Đưa Access Token vào blacklist khi người dùng logout.
     */
    @Transactional
    public void blacklistAccessToken(String rawAccessToken, Date expiresAt) {
        String tokenHash = jwtTokenProvider.hashToken(rawAccessToken);
        
        // Không thêm nếu đã có
        if (blacklistTokenRepository.existsByTokenHash(tokenHash)) {
            return;
        }

        LocalDateTime expiryTime = expiresAt.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();

        BlacklistTokenEntity entity = BlacklistTokenEntity.builder()
                .tokenHash(tokenHash)
                .expiresAt(expiryTime)
                .build();

        blacklistTokenRepository.save(entity);
    }

    /**
     * Kiểm tra Access Token có bị blacklist không.
     */
    @Transactional(readOnly = true)
    public boolean isBlacklisted(String rawAccessToken) {
        String tokenHash = jwtTokenProvider.hashToken(rawAccessToken);
        return blacklistTokenRepository.existsByTokenHash(tokenHash);
    }
    
    /**
     * Revoke toàn bộ refresh token của một owner
     */
    @Transactional
    public void revokeAllRefreshTokens(Long ownerId, String ownerType) {
        refreshTokenRepository.revokeAllByOwner(ownerId, ownerType);
    }

    /**
     * Chạy định kỳ mỗi 1 giờ để dọn dẹp token hết hạn trong DB.
     */
    @Scheduled(fixedRate = 3600000)
    @Transactional
    public void cleanupExpiredTokens() {
        LocalDateTime now = LocalDateTime.now();
        log.info("Bắt đầu dọn dẹp các token hết hạn lúc {}", now);
        refreshTokenRepository.deleteExpiredAndRevoked(now);
        blacklistTokenRepository.deleteExpired(now);
    }
}
