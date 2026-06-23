package api.angia.com.modules.membership.infrastructure.persistence;

import api.angia.com.modules.membership.domain.model.MembershipPackage;
import api.angia.com.modules.membership.domain.repository.MembershipPackageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class MembershipPackageRepositoryImpl implements MembershipPackageRepository {

    private final MembershipPackageJpaRepository jpaRepository;

    @Override
    public Optional<MembershipPackage> findById(String id) {
        return jpaRepository.findById(id).map(this::toDomain);
    }

    @Override
    public List<MembershipPackage> findAll() {
        return jpaRepository.findAll().stream().map(this::toDomain).collect(Collectors.toList());
    }

    @Override
    public MembershipPackage save(MembershipPackage domain) {
        MembershipPackageEntity entity = toEntity(domain);
        MembershipPackageEntity saved = jpaRepository.save(entity);
        return toDomain(saved);
    }

    @Override
    public void deleteById(String id) {
        jpaRepository.deleteById(id);
    }

    private MembershipPackage toDomain(MembershipPackageEntity entity) {
        return MembershipPackage.builder()
                .id(entity.getId())
                .name(entity.getName())
                .price(entity.getPrice())
                .description(entity.getDescription())
                .benefit(entity.getBenefit())
                .image(entity.getImage())
                .featured(entity.getFeatured())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

    private MembershipPackageEntity toEntity(MembershipPackage domain) {
        return MembershipPackageEntity.builder()
                .id(domain.getId())
                .name(domain.getName())
                .price(domain.getPrice())
                .description(domain.getDescription())
                .benefit(domain.getBenefit())
                .image(domain.getImage())
                .featured(domain.getFeatured())
                .createdAt(domain.getCreatedAt())
                .updatedAt(domain.getUpdatedAt())
                .build();
    }
}
