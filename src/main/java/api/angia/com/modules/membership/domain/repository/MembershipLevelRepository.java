package api.angia.com.modules.membership.domain.repository;

import api.angia.com.modules.membership.domain.model.MembershipLevel;

import java.util.List;
import java.util.Optional;

public interface MembershipLevelRepository {
    Optional<MembershipLevel> findById(String id);
    List<MembershipLevel> findAll();
    MembershipLevel save(MembershipLevel level);
    void deleteById(String id);
}
