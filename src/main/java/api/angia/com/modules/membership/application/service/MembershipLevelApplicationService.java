package api.angia.com.modules.membership.application.service;

import api.angia.com.modules.membership.api.v1.dto.request.CreateMembershipLevelRequest;
import api.angia.com.modules.membership.api.v1.dto.response.MembershipLevelResponse;
import api.angia.com.modules.membership.application.mapper.MembershipLevelMapper;
import api.angia.com.modules.membership.domain.exception.MembershipException;
import api.angia.com.modules.membership.domain.model.MembershipLevel;
import api.angia.com.modules.membership.domain.model.MembershipLevelHistory;
import api.angia.com.modules.membership.domain.repository.MembershipLevelHistoryRepository;
import api.angia.com.modules.membership.domain.repository.MembershipLevelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MembershipLevelApplicationService {

    private final MembershipLevelRepository membershipLevelRepository;
    private final MembershipLevelHistoryRepository historyRepository;
    private final MembershipLevelMapper mapper;

    public List<MembershipLevelResponse> getAllLevels() {
        return membershipLevelRepository.findAll()
                .stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    public MembershipLevelResponse getLevelById(String id) {
        MembershipLevel level = membershipLevelRepository.findById(id)
                .orElseThrow(() -> MembershipException.notFound(id));
        return mapper.toResponse(level);
    }

    public MembershipLevelResponse createLevel(CreateMembershipLevelRequest request) {
        MembershipLevel level = mapper.toDomain(request);
        boolean isNew = membershipLevelRepository.findById(level.getId()).isEmpty();
        MembershipLevel saved = membershipLevelRepository.save(level);
        
        // Save history
        String action = isNew ? "CREATE" : "UPDATE";
        historyRepository.save(MembershipLevelHistory.createSnapshot(saved, action, "SYSTEM")); // Replace SYSTEM with actual user if auth is added

        return mapper.toResponse(saved);
    }

    public void deleteLevel(String id) {
        MembershipLevel level = membershipLevelRepository.findById(id)
                .orElseThrow(() -> MembershipException.notFound(id));
        
        // Save history before deleting
        historyRepository.save(MembershipLevelHistory.createSnapshot(level, "DELETE", "SYSTEM"));
        
        membershipLevelRepository.deleteById(id);
    }
}
