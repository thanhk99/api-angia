package api.angia.com.shared.token.repository;

import api.angia.com.shared.token.entity.BlacklistTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface BlacklistTokenRepository extends JpaRepository<BlacklistTokenEntity, Long> {

    boolean existsByTokenHash(String tokenHash);

    // Xoá token blacklist đã hết hạn (chạy định kỳ)
    @Modifying
    @Query("DELETE FROM BlacklistTokenEntity b WHERE b.expiresAt < :now")
    void deleteExpired(LocalDateTime now);
}
