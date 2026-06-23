package api.angia.com.modules.membership.application.mapper;

import api.angia.com.modules.membership.api.v1.dto.request.CreateMembershipLevelRequest;
import api.angia.com.modules.membership.api.v1.dto.response.MembershipLevelResponse;
import api.angia.com.modules.membership.domain.model.MembershipLevel;
import org.springframework.stereotype.Component;

@Component
public class MembershipLevelMapper {

    public MembershipLevel toDomain(CreateMembershipLevelRequest request) {
        return MembershipLevel.createNew(
                request.getId(),
                request.getLevel(),
                request.getMinSpent(),
                request.getDiscount(),
                request.getPointMultiplier(),
                request.isFreeShipping(),
                request.isPrioritySupport(),
                request.getExclusiveOffers()
        );
    }

    public MembershipLevelResponse toResponse(MembershipLevel domain) {
        return MembershipLevelResponse.builder()
                .id(domain.getId())
                .level(domain.getLevel())
                .minSpent(domain.getMinSpent())
                .discount(domain.getDiscount())
                .pointMultiplier(domain.getPointMultiplier())
                .freeShipping(domain.isFreeShipping())
                .prioritySupport(domain.isPrioritySupport())
                .exclusiveOffers(domain.getExclusiveOffers())
                .createdAt(domain.getCreatedAt())
                .updatedAt(domain.getUpdatedAt())
                .build();
    }
}
