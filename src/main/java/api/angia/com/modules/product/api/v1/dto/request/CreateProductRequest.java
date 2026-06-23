package api.angia.com.modules.product.api.v1.dto.request;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Data
public class CreateProductRequest {

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
    private Boolean inStock;

    /**
     * Hành trình sản xuất qua các cột mốc thời gian.
     * Mỗi phần tử: { date: "YYYY-MM-DD", event: { "vi": "...", "en": "..." } }
     */
    private List<TimelineItemRequest> timeline;

    private String qrCode;
    private String batch;
    private String productionDate;
    private String expiryDate;
    private String region;
}
