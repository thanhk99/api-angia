package api.angia.com.modules.membership.domain.repository;

import api.angia.com.modules.membership.domain.model.MembershipPackageHistory;

public interface MembershipPackageHistoryRepository {
    MembershipPackageHistory save(MembershipPackageHistory history);
}
