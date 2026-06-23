package api.angia.com.modules.membership.domain.repository;

import api.angia.com.modules.membership.domain.model.MembershipPackage;

import java.util.List;
import java.util.Optional;

public interface MembershipPackageRepository {
    Optional<MembershipPackage> findById(String id);
    List<MembershipPackage> findAll();
    MembershipPackage save(MembershipPackage membershipPackage);
    void deleteById(String id);
}
