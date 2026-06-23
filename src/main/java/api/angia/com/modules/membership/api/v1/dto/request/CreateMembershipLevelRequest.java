package api.angia.com.modules.membership.api.v1.dto.request;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class CreateMembershipLevelRequest {
    private String id;
    private String level;
    private BigDecimal minSpent;
    private Double discount;
    private Double pointMultiplier;
    private boolean freeShipping;
    private boolean prioritySupport;
    private Boolean exclusiveOffers;
}
