package api.angia.com.modules.membership.application.port;

import api.angia.com.modules.membership.domain.model.MembershipLevel;

import java.util.Optional;

public interface MembershipPort {
    Optional<MembershipLevel> getMembershipLevelById(String levelId);
}
