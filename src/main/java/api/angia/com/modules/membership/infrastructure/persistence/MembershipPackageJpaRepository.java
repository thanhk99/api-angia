package api.angia.com.modules.membership.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MembershipPackageJpaRepository extends JpaRepository<MembershipPackageEntity, String> {
}
