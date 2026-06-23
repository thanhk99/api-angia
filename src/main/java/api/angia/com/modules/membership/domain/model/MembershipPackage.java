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
public class MembershipPackage {
    private String id;
    private Map<String, String> name;
    private BigDecimal price;
    private Map<String, String> description;
    private List<Map<String, String>> benefit;
    private String image;
    private Boolean featured;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static MembershipPackage createNew(Map<String, String> name, BigDecimal price, Map<String, String> description, List<Map<String, String>> benefit, String image, Boolean featured) {
        return MembershipPackage.builder()
                .name(name)
                .price(price)
                .description(description)
                .benefit(benefit)
                .image(image)
                .featured(featured)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }
}
