package api.angia.com.modules.membership.application.service;

import api.angia.com.modules.membership.api.v1.dto.request.CreateMembershipPackageRequest;
import api.angia.com.modules.membership.api.v1.dto.response.MembershipPackageResponse;
import api.angia.com.modules.membership.application.mapper.MembershipPackageMapper;
import api.angia.com.modules.membership.domain.exception.MembershipException;
import api.angia.com.modules.membership.domain.model.MembershipPackage;
import api.angia.com.modules.membership.domain.model.MembershipPackageHistory;
import api.angia.com.modules.membership.domain.repository.MembershipPackageHistoryRepository;
import api.angia.com.modules.membership.domain.repository.MembershipPackageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MembershipPackageApplicationService {

    private final MembershipPackageRepository membershipPackageRepository;
    private final MembershipPackageHistoryRepository historyRepository;
    private final MembershipPackageMapper mapper;

    public List<MembershipPackageResponse> getAllPackages() {
        return membershipPackageRepository.findAll()
                .stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    public MembershipPackageResponse getPackageById(String id) {
        MembershipPackage pkg = membershipPackageRepository.findById(id)
                .orElseThrow(() -> MembershipException.notFound(id));
        return mapper.toResponse(pkg);
    }

    public MembershipPackageResponse createPackage(CreateMembershipPackageRequest request) {
        MembershipPackage pkg = mapper.toDomain(request);
        boolean isNew = membershipPackageRepository.findById(pkg.getId()).isEmpty();
        MembershipPackage saved = membershipPackageRepository.save(pkg);
        
        // Save history
        String action = isNew ? "CREATE" : "UPDATE";
        historyRepository.save(MembershipPackageHistory.createSnapshot(saved, action, "SYSTEM")); // Replace SYSTEM with actual user if auth is added

        return mapper.toResponse(saved);
    }

    public void deletePackage(String id) {
        MembershipPackage pkg = membershipPackageRepository.findById(id)
                .orElseThrow(() -> MembershipException.notFound(id));
                
        // Save history before deleting
        historyRepository.save(MembershipPackageHistory.createSnapshot(pkg, "DELETE", "SYSTEM"));
        
        membershipPackageRepository.deleteById(id);
    }
}
