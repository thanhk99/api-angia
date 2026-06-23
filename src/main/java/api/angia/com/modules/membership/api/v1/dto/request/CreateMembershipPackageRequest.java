package api.angia.com.modules.membership.api.v1.dto.request;

import lombok.Data;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Data
public class CreateMembershipPackageRequest {
    private Map<String, String> name;
    private BigDecimal price;
    private Map<String, String> description;
    private List<Map<String, String>> benefit;
    private String image;
    private Boolean featured;
}
