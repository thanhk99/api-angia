package api.angia.com.modules.admin.application.service;

import api.angia.com.modules.admin.domain.repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminAuthApplicationService {

    private final AdminRepository adminRepository;

    public void login(String username, String password) {
        // TODO: Implement login logic using AdminRepository
    }
}
