package api.angia.com.modules.membership.application.mapper;

import api.angia.com.modules.membership.api.v1.dto.request.CreateMembershipPackageRequest;
import api.angia.com.modules.membership.api.v1.dto.response.MembershipPackageResponse;
import api.angia.com.modules.membership.domain.model.MembershipPackage;
import org.springframework.stereotype.Component;

@Component
public class MembershipPackageMapper {

    public MembershipPackage toDomain(CreateMembershipPackageRequest request) {
        return MembershipPackage.createNew(
                request.getName(),
                request.getPrice(),
                request.getDescription(),
                request.getBenefit(),
                request.getImage(),
                request.getFeatured()
        );
    }

    public MembershipPackageResponse toResponse(MembershipPackage domain) {
        return MembershipPackageResponse.builder()
                .id(domain.getId())
                .name(domain.getName())
                .price(domain.getPrice())
                .description(domain.getDescription())
                .benefit(domain.getBenefit())
                .image(domain.getImage())
                .featured(domain.getFeatured())
                .createdAt(domain.getCreatedAt())
                .updatedAt(domain.getUpdatedAt())
                .build();
    }
}
