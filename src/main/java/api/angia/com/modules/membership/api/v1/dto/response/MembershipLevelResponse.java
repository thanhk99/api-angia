package api.angia.com.modules.membership.api.v1.dto.response;

import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class MembershipLevelResponse {
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
}
