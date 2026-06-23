package api.angia.com.modules.membership.infrastructure.persistence;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Entity
@Table(name = "membership_package_histories")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MembershipPackageHistoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 6)
    private String packageId;

    @Column(nullable = false, length = 20)
    private String action;

    @Column(length = 255)
    private String actionBy;

    // Snapshot fields
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
}
