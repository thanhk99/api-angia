package api.angia.com.modules.admin.infrastructure.persistence;

import api.angia.com.modules.admin.domain.model.Admin;
import api.angia.com.modules.admin.domain.model.Role;
import api.angia.com.modules.admin.domain.repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class AdminRepositoryImpl implements AdminRepository {

    private final AdminJpaRepository jpaRepository;

    @Override
    public Optional<Admin> findByUsername(String username) {
        return jpaRepository.findByUsername(username)
                .map(this::toModel);
    }

    @Override
    public Admin save(Admin admin) {
        AdminEntity entity = toEntity(admin);
        AdminEntity savedEntity = jpaRepository.save(entity);
        return toModel(savedEntity);
    }

    private Admin toModel(AdminEntity entity) {
        if (entity == null) return null;
        return Admin.builder()
                .id(entity.getId())
                .username(entity.getUsername())
                .password(entity.getPassword())
                .name(entity.getName())
                .active(entity.isActive())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .createdBy(entity.getCreatedBy())
                .updatedBy(entity.getUpdatedBy())
                .roles(entity.getRoles().stream()
                        .map(r -> new Role(r.getId(), r.getName()))
                        .collect(Collectors.toSet()))
                .build();
    }

    private AdminEntity toEntity(Admin model) {
        if (model == null) return null;
        return AdminEntity.builder()
                .id(model.getId())
                .username(model.getUsername())
                .password(model.getPassword())
                .name(model.getName())
                .active(model.isActive())
                .createdAt(model.getCreatedAt())
                .updatedAt(model.getUpdatedAt())
                .createdBy(model.getCreatedBy())
                .updatedBy(model.getUpdatedBy())
                .roles(model.getRoles().stream()
                        .map(r -> new RoleEntity(r.getId(), r.getName()))
                        .collect(Collectors.toSet()))
                .build();
    }
}
