package api.angia.com.modules.membership.infrastructure.persistence;

import api.angia.com.modules.membership.domain.model.MembershipLevel;
import api.angia.com.modules.membership.domain.repository.MembershipLevelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class MembershipLevelRepositoryImpl implements MembershipLevelRepository {

    private final MembershipLevelJpaRepository jpaRepository;

    @Override
    public Optional<MembershipLevel> findById(String id) {
        return jpaRepository.findById(id).map(this::toDomain);
    }

    @Override
    public List<MembershipLevel> findAll() {
        return jpaRepository.findAll().stream().map(this::toDomain).collect(Collectors.toList());
    }

    @Override
    public MembershipLevel save(MembershipLevel domain) {
        MembershipLevelEntity entity = toEntity(domain);
        MembershipLevelEntity saved = jpaRepository.save(entity);
        return toDomain(saved);
    }

    @Override
    public void deleteById(String id) {
        jpaRepository.deleteById(id);
    }

    private MembershipLevel toDomain(MembershipLevelEntity entity) {
        return MembershipLevel.builder()
                .id(entity.getId())
                .level(entity.getLevel())
                .minSpent(entity.getMinSpent())
                .discount(entity.getDiscount())
                .pointMultiplier(entity.getPointMultiplier())
                .freeShipping(entity.isFreeShipping())
                .prioritySupport(entity.isPrioritySupport())
                .exclusiveOffers(entity.getExclusiveOffers())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

    private MembershipLevelEntity toEntity(MembershipLevel domain) {
        return MembershipLevelEntity.builder()
                .id(domain.getId())
                .level(domain.getLevel())
                .minSpent(domain.getMinSpent())
                .discount(domain.getDiscount())
                .pointMultiplier(domain.getPointMultiplier())
                .freeShipping(domain.isFreeShipping())
                .prioritySupport(domain.isPrioritySupport())
                .exclusiveOffers(domain.getExclusiveOffers())
                .createdAt(domain.getCreatedAt())
                .updatedAt(domain.getUpdatedAt())
                .build();
    }
}
