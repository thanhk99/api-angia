package api.angia.com.modules.membership.infrastructure.persistence;

import api.angia.com.modules.membership.domain.model.MembershipPackageHistory;
import api.angia.com.modules.membership.domain.repository.MembershipPackageHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MembershipPackageHistoryRepositoryImpl implements MembershipPackageHistoryRepository {

    private final MembershipPackageHistoryJpaRepository jpaRepository;

    @Override
    public MembershipPackageHistory save(MembershipPackageHistory domain) {
        MembershipPackageHistoryEntity entity = MembershipPackageHistoryEntity.builder()
                .packageId(domain.getPackageId())
                .action(domain.getAction())
                .actionBy(domain.getActionBy())
                .name(domain.getName())
                .price(domain.getPrice())
                .description(domain.getDescription())
                .benefit(domain.getBenefit())
                .image(domain.getImage())
                .featured(domain.getFeatured())
                .createdAt(domain.getCreatedAt())
                .build();
        
        MembershipPackageHistoryEntity saved = jpaRepository.save(entity);
        domain.setId(saved.getId());
        return domain;
    }
}
