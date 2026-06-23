package api.angia.com.modules.membership.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MembershipLevelJpaRepository extends JpaRepository<MembershipLevelEntity, String> {
}
