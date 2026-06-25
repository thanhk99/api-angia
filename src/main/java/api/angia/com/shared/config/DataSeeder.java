package api.angia.com.shared.config;

import api.angia.com.modules.admin.infrastructure.persistence.AdminEntity;
import api.angia.com.modules.admin.infrastructure.persistence.AdminJpaRepository;
import api.angia.com.modules.admin.infrastructure.persistence.RoleEntity;
import api.angia.com.modules.admin.infrastructure.persistence.RoleJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final RoleJpaRepository roleJpaRepository;
    private final AdminJpaRepository adminJpaRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        seedRoles();
        // seedSuperAdmin();
    }

    private void seedRoles() {
        List<String> defaultRoles = List.of(
                "ROLE_SUPER_ADMIN", // Quản trị viên hệ thống
                "ROLE_EDITOR", // Biên tập viên
                "ROLE_EXPERT", // Bác sĩ/ chuyên gia
                "ROLE_OPERATOR" // Nhân viên vận hành
        );

        for (String roleName : defaultRoles) {
            if (roleJpaRepository.findByName(roleName).isEmpty()) {
                roleJpaRepository.save(RoleEntity.builder().name(roleName).build());
            }
        }
    }

    private void seedSuperAdmin() {
        if (adminJpaRepository.findByUsername("superadmin").isEmpty()) {
            Optional<RoleEntity> superAdminRole = roleJpaRepository.findByName("ROLE_SUPER_ADMIN");

            if (superAdminRole.isPresent()) {
                AdminEntity superAdmin = AdminEntity.builder()
                        .username("admin")
                        .password(passwordEncoder.encode("admin123")) // Default password
                        .name("System Super Admin")
                        .active(true)
                        .roles(new HashSet<>(List.of(superAdminRole.get())))
                        .build();

                adminJpaRepository.save(superAdmin);
            }
        }
    }
}
