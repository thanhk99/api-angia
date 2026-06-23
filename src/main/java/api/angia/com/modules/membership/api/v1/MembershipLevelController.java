package api.angia.com.modules.membership.api.v1;

import api.angia.com.modules.membership.api.v1.dto.request.CreateMembershipLevelRequest;
import api.angia.com.modules.membership.api.v1.dto.response.MembershipLevelResponse;
import api.angia.com.modules.membership.application.service.MembershipLevelApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/membership-levels")
@RequiredArgsConstructor
public class MembershipLevelController {

    private final MembershipLevelApplicationService applicationService;

    @GetMapping
    @PreAuthorize("permitAll()")
    public ResponseEntity<List<MembershipLevelResponse>> getAllLevels() {
        return ResponseEntity.ok(applicationService.getAllLevels());
    }

    @GetMapping("/{id}")
    @PreAuthorize("permitAll()")
    public ResponseEntity<MembershipLevelResponse> getLevelById(@PathVariable String id) {
        return ResponseEntity.ok(applicationService.getLevelById(id));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN')")
    public ResponseEntity<MembershipLevelResponse> createLevel(@RequestBody CreateMembershipLevelRequest request) {
        return ResponseEntity.ok(applicationService.createLevel(request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<Void> deleteLevel(@PathVariable String id) {
        applicationService.deleteLevel(id);
        return ResponseEntity.noContent().build();
    }
}
