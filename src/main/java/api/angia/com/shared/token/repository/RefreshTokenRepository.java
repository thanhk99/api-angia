package api.angia.com.shared.token.repository;

import api.angia.com.shared.token.entity.RefreshTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshTokenEntity, Long> {

    Optional<RefreshTokenEntity> findByTokenHash(String tokenHash);

    // Thu hồi tất cả token của 1 chủ sở hữu (logout all devices)
    @Modifying
    @Query("UPDATE RefreshTokenEntity r SET r.revoked = true WHERE r.ownerId = :ownerId AND r.ownerType = :ownerType")
    void revokeAllByOwner(String ownerId, String ownerType);

    // Xoá token hết hạn (chạy định kỳ)
    @Modifying
    @Query("DELETE FROM RefreshTokenEntity r WHERE r.expiresAt < :now OR r.revoked = true")
    void deleteExpiredAndRevoked(LocalDateTime now);
}
