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
public class MembershipLevel {
    private String id;
    private String level;
    private BigDecimal minSpent;
    private Double discount;
    private Double pointMultiplier;
    private boolean freeShipping;
    private boolean prioritySupport;
    private Boolean exclusiveOffers;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static MembershipLevel createNew(String id, String level, BigDecimal minSpent, Double discount, Double pointMultiplier, boolean freeShipping, boolean prioritySupport, Boolean exclusiveOffers) {
        return MembershipLevel.builder()
                .id(id)
                .level(level)
                .minSpent(minSpent)
                .discount(discount)
                .pointMultiplier(pointMultiplier)
                .freeShipping(freeShipping)
                .prioritySupport(prioritySupport)
                .exclusiveOffers(exclusiveOffers)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }
}
