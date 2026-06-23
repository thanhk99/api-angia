package api.angia.com.modules.membership.domain.repository;

import api.angia.com.modules.membership.domain.model.MembershipLevelHistory;

public interface MembershipLevelHistoryRepository {
    MembershipLevelHistory save(MembershipLevelHistory history);
}
