package api.angia.com.modules.product.api.v1.dto.response;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Data
@Builder
public class ProductResponse {

    private String id;
    private Map<String, String> name;
    private String slug;
    private String categoryId;
    private BigDecimal price;
    private BigDecimal originalPrice;
    private BigDecimal discount;
    private String image;
    private List<String> images;
    private Map<String, String> description;
    private Map<String, String> shortDescription;
    private Map<String, List<String>> benefits;
    private Map<String, String> usage;
    private Map<String, String> attributes;
    private String origin;
    private List<String> certifications;
    private BigDecimal rating;
    private Integer soldCount;
    private Boolean inStock;

    /**
     * Hành trình sản xuất qua các cột mốc thời gian.
     * Mỗi phần tử: { date: "YYYY-MM-DD", event: { "vi": "...", "en": "..." } }
     */
    private List<TimelineItemResponse> timeline;

    private String qrCode;
    private String batch;
    private String productionDate;
    private String expiryDate;
    private String region;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
