package api.angia.com.modules.membership.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MembershipLevelHistory {
    private Long id;
    private String levelId;
    private String action; // CREATE, UPDATE, DELETE
    private String actionBy;

    // Snapshot of the level
    private String level;
    private BigDecimal minSpent;
    private Double discount;
    private Double pointMultiplier;
    private boolean freeShipping;
    private boolean prioritySupport;
    private Boolean exclusiveOffers;

    private LocalDateTime createdAt;

    public static MembershipLevelHistory createSnapshot(MembershipLevel levelModel, String action, String actionBy) {
        return MembershipLevelHistory.builder()
                .levelId(levelModel.getId())
                .action(action)
                .actionBy(actionBy)
                .level(levelModel.getLevel())
                .minSpent(levelModel.getMinSpent())
                .discount(levelModel.getDiscount())
                .pointMultiplier(levelModel.getPointMultiplier())
                .freeShipping(levelModel.isFreeShipping())
                .prioritySupport(levelModel.isPrioritySupport())
                .exclusiveOffers(levelModel.getExclusiveOffers())
                .createdAt(LocalDateTime.now())
                .build();
    }
}
