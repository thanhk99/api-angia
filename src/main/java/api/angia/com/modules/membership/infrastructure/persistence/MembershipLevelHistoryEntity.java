package api.angia.com.modules.membership.infrastructure.persistence;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "membership_level_histories")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MembershipLevelHistoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String levelId;

    @Column(nullable = false, length = 20)
    private String action;

    @Column(length = 255)
    private String actionBy;

    // Snapshot fields
    @Column(nullable = false)
    private String level;

    @Column(nullable = false, precision = 36, scale = 8)
    private BigDecimal minSpent;

    @Column(nullable = false)
    private Double discount;

    @Column(nullable = false)
    private Double pointMultiplier;

    @Column(nullable = false)
    private boolean freeShipping;

    @Column(nullable = false)
    private boolean prioritySupport;

    @Column(nullable = true)
    private Boolean exclusiveOffers;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;
}
