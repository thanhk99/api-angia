package api.angia.com.modules.product.infrastructure.persistence;

import api.angia.com.modules.product.domain.model.ProductTimeline;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * JPA Entity cho bảng products (PostgreSQL).
 * Các field đa ngôn ngữ và collections dùng @JdbcTypeCode(SqlTypes.JSON)
 * với columnDefinition = "jsonb" — chuẩn PostgreSQL.
 *
 * timeline lưu dưới dạng jsonb: List<ProductTimeline>
 * Mỗi phần tử: { "date": "YYYY-MM-DD", "event": { "vi": "...", "en": "..." } }
 */
@Entity
@Table(name = "products")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductEntity {

    @Id
    @GeneratedValue(generator = "random-8-digit-string")
    @GenericGenerator(name = "random-8-digit-string", strategy = "api.angia.com.shared.utils.Random8DigitIdGenerator")
    @Column(length = 8)
    private String id;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb", nullable = false)
    private Map<String, String> name;

    @Column(nullable = false, unique = true, length = 300)
    private String slug;

    @Column(nullable = false, length = 8)
    private String categoryId;

    @Column(nullable = false, precision = 36, scale = 8)
    private BigDecimal price;

    @Column(precision = 36, scale = 8)
    private BigDecimal originalPrice;

    @Column(precision = 5, scale = 2)
    private BigDecimal discount;

    @Column(nullable = false, length = 500)
    private String image;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private List<String> images;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb", nullable = false)
    private Map<String, String> description;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private Map<String, String> shortDescription;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private Map<String, List<String>> benefits;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private Map<String, String> usage;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private Map<String, String> attributes;

    @Column(length = 200)
    private String origin;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private List<String> certifications;

    @Column(precision = 3, scale = 2)
    private BigDecimal rating;

    @Column(columnDefinition = "integer default 0")
    private Integer soldCount;

    @Column(nullable = false, columnDefinition = "boolean default true")
    private Boolean inStock;

    /**
     * Hành trình sản xuất — lưu dạng jsonb.
     * Mỗi phần tử: { "date": "2024-03-01", "event": { "vi": "...", "en": "..." } }
     */
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private List<ProductTimeline> timeline;

    @Column(length = 500)
    private String qrCode;

    @Column(length = 100)
    private String batch;

    @Column(length = 10)
    private String productionDate;

    @Column(length = 10)
    private String expiryDate;

    @Column(length = 200)
    private String region;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
