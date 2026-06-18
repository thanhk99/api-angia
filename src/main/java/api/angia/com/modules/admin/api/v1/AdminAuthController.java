package api.angia.com.modules.admin.api.v1;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/auth")
public class AdminAuthController {

    @PostMapping("/login")
    public ResponseEntity<String> login() {
        // TODO: Implement login logic (check account, generate token for admin)
        return ResponseEntity.ok("Admin token will be generated here");
    }
}
