package api.angia.com.modules.membership.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MembershipLevelHistoryJpaRepository extends JpaRepository<MembershipLevelHistoryEntity, Long> {
}
