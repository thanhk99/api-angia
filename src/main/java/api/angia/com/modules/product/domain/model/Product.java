package api.angia.com.modules.product.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * Aggregate Root — Sản phẩm.
 * Quản lý toàn bộ thông tin chi tiết sản phẩm, giá cả, chứng nhận,
 * truy xuất nguồn gốc và hành trình sản xuất (timeline).
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    /** ID duy nhất định danh sản phẩm (8 chữ số) */
    private String id;

    /** Tên sản phẩm đa ngôn ngữ */
    private Map<String, String> name;

    /** Đường dẫn URL phục vụ SEO */
    private String slug;

    /** FK — liên kết với danh mục sản phẩm (Category.id) */
    private String categoryId;

    /** Giá trị hiện tại của sản phẩm */
    private BigDecimal price;

    /** Giá bán gốc khi chưa giảm giá */
    private BigDecimal originalPrice;

    /** Tỷ lệ giảm giá % trực quan cho khách hàng */
    private BigDecimal discount;

    /** Đường dẫn ảnh đại diện/thumbnail sản phẩm */
    private String image;

    /** Bộ sưu tập hình ảnh chi tiết khác của sản phẩm */
    private List<String> images;

    /** Mô tả chi tiết đa ngôn ngữ (thông tin, công dụng, nguồn gốc bằng Markdown) */
    private Map<String, String> description;

    /** Mô tả ngắn đa ngôn ngữ phục vụ hiển thị nhanh ở trang danh sách */
    private Map<String, String> shortDescription;

    /** Danh sách các lợi ích nổi bật cốt lõi của sản phẩm (đa ngôn ngữ) */
    private Map<String, List<String>> benefits;

    /** Hướng dẫn sử dụng chi tiết đa ngôn ngữ */
    private Map<String, String> usage;

    /** Cặp thuộc tính động (ví dụ: dung tích: 500ml, đóng gói: hộp) */
    private Map<String, String> attributes;

    /** Xuất xứ địa lý (ví dụ: Quảng Nam, Việt Nam) */
    private String origin;

    /** Danh sách các chứng nhận đạt được (ví dụ: OCOP 4 sao, VietGAP) */
    private List<String> certifications;

    /** Điểm đánh giá trung bình của sản phẩm (thang điểm 5) */
    private BigDecimal rating;

    /** Số lượng sản phẩm đã bán ra thực tế */
    private Integer soldCount;

    /** Trạng thái tồn kho (true: còn hàng, false: hết hàng) */
    private Boolean inStock;

    /**
     * Hành trình sản xuất qua các cột mốc thời gian.
     * Mỗi phần tử gồm date (YYYY-MM-DD) và event (đa ngôn ngữ vi/en).
     */
    private List<ProductTimeline> timeline;

    /** Mã QR truy xuất thông tin chuỗi cung ứng */
    private String qrCode;

    /** Mã số lô sản xuất kiểm soát chất lượng */
    private String batch;

    /** Ngày sản xuất (định dạng YYYY-MM-DD) */
    private String productionDate;

    /** Hạn sử dụng (định dạng YYYY-MM-DD) */
    private String expiryDate;

    /** Vùng nguyên liệu nuôi trồng/sản xuất */
    private String region;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    /**
     * Factory method — tạo sản phẩm mới với giá trị mặc định.
     */
    public static Product createNew(
            Map<String, String> name,
            String slug,
            String categoryId,
            BigDecimal price,
            BigDecimal originalPrice,
            BigDecimal discount,
            String image,
            List<String> images,
            Map<String, String> description,
            Map<String, String> shortDescription,
            Map<String, List<String>> benefits,
            Map<String, String> usage,
            Map<String, String> attributes,
            String origin,
            List<String> certifications,
            Boolean inStock,
            List<ProductTimeline> timeline,
            String qrCode,
            String batch,
            String productionDate,
            String expiryDate,
            String region
    ) {
        return Product.builder()
                .name(name)
                .slug(slug)
                .categoryId(categoryId)
                .price(price)
                .originalPrice(originalPrice)
                .discount(discount)
                .image(image)
                .images(images)
                .description(description)
                .shortDescription(shortDescription)
                .benefits(benefits)
                .usage(usage)
                .attributes(attributes)
                .origin(origin)
                .certifications(certifications)
                .rating(BigDecimal.ZERO)
                .soldCount(0)
                .inStock(inStock != null ? inStock : true)
                .timeline(timeline)
                .qrCode(qrCode)
                .batch(batch)
                .productionDate(productionDate)
                .expiryDate(expiryDate)
                .region(region)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }
}
