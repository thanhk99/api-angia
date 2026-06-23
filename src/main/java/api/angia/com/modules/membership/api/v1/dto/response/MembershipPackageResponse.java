package api.angia.com.modules.membership.api.v1.dto.response;

import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Data
@Builder
public class MembershipPackageResponse {
    private String id;
    private Map<String, String> name;
    private BigDecimal price;
    private Map<String, String> description;
    private List<Map<String, String>> benefit;
    private String image;
    private Boolean featured;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
