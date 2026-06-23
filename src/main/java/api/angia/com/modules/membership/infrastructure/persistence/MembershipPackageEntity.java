package api.angia.com.modules.membership.infrastructure.persistence;

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

@Entity
@Table(name = "membership_packages")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MembershipPackageEntity {

    @Id
    @GeneratedValue(generator = "random-6-digit-string")
    @GenericGenerator(name = "random-6-digit-string", strategy = "api.angia.com.shared.utils.Random6DigitStringIdGenerator")
    @Column(length = 6)
    private String id;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb", nullable = false)
    private Map<String, String> name;

    @Column(nullable = false, precision = 36, scale = 8)
    private BigDecimal price;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb", nullable = false)
    private Map<String, String> description;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb", nullable = false)
    private List<Map<String, String>> benefit;

    @Column(nullable = false, length = 500)
    private String image;

    @Column(nullable = true)
    private Boolean featured;

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
