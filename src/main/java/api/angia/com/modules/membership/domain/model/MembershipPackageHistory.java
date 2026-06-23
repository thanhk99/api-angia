package api.angia.com.modules.membership.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MembershipPackageHistory {
    private Long id;
    private String packageId;
    private String action; // CREATE, UPDATE, DELETE
    private String actionBy;

    // Snapshot of the package
    private Map<String, String> name;
    private BigDecimal price;
    private Map<String, String> description;
    private List<Map<String, String>> benefit;
    private String image;
    private Boolean featured;

    private LocalDateTime createdAt;

    public static MembershipPackageHistory createSnapshot(MembershipPackage packageModel, String action, String actionBy) {
        return MembershipPackageHistory.builder()
                .packageId(packageModel.getId())
                .action(action)
                .actionBy(actionBy)
                .name(packageModel.getName())
                .price(packageModel.getPrice())
                .description(packageModel.getDescription())
                .benefit(packageModel.getBenefit())
                .image(packageModel.getImage())
                .featured(packageModel.getFeatured())
                .createdAt(LocalDateTime.now())
                .build();
    }
}
