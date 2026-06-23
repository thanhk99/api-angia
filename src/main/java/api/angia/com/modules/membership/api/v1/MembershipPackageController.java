package api.angia.com.modules.membership.api.v1;

import api.angia.com.modules.membership.api.v1.dto.request.CreateMembershipPackageRequest;
import api.angia.com.modules.membership.api.v1.dto.response.MembershipPackageResponse;
import api.angia.com.modules.membership.application.service.MembershipPackageApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/membership-packages")
@RequiredArgsConstructor
public class MembershipPackageController {

    private final MembershipPackageApplicationService applicationService;

    @GetMapping
    @PreAuthorize("permitAll()")
    public ResponseEntity<List<MembershipPackageResponse>> getAllPackages() {
        return ResponseEntity.ok(applicationService.getAllPackages());
    }

    @GetMapping("/{id}")
    @PreAuthorize("permitAll()")
    public ResponseEntity<MembershipPackageResponse> getPackageById(@PathVariable String id) {
        return ResponseEntity.ok(applicationService.getPackageById(id));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN')")
    public ResponseEntity<MembershipPackageResponse> createPackage(
            @RequestBody CreateMembershipPackageRequest request) {
        return ResponseEntity.ok(applicationService.createPackage(request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<Void> deletePackage(@PathVariable String id) {
        applicationService.deletePackage(id);
        return ResponseEntity.noContent().build();
    }
}
