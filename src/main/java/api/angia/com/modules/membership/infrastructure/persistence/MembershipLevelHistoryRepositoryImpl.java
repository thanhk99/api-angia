package api.angia.com.modules.membership.infrastructure.persistence;

import api.angia.com.modules.membership.domain.model.MembershipLevelHistory;
import api.angia.com.modules.membership.domain.repository.MembershipLevelHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MembershipLevelHistoryRepositoryImpl implements MembershipLevelHistoryRepository {

    private final MembershipLevelHistoryJpaRepository jpaRepository;

    @Override
    public MembershipLevelHistory save(MembershipLevelHistory domain) {
        MembershipLevelHistoryEntity entity = MembershipLevelHistoryEntity.builder()
                .levelId(domain.getLevelId())
                .action(domain.getAction())
                .actionBy(domain.getActionBy())
                .level(domain.getLevel())
                .minSpent(domain.getMinSpent())
                .discount(domain.getDiscount())
                .pointMultiplier(domain.getPointMultiplier())
                .freeShipping(domain.isFreeShipping())
                .prioritySupport(domain.isPrioritySupport())
                .exclusiveOffers(domain.getExclusiveOffers())
                .createdAt(domain.getCreatedAt())
                .build();
        
        MembershipLevelHistoryEntity saved = jpaRepository.save(entity);
        domain.setId(saved.getId());
        return domain;
    }
}
