package api.angia.com.modules.admin.domain.repository;

import api.angia.com.modules.admin.domain.model.Admin;

import java.util.Optional;

public interface AdminRepository {
    Optional<Admin> findById(String id);
    Optional<Admin> findByUsername(String username);
    Admin save(Admin admin);
}
