package api.angia.com.modules.membership.infrastructure.persistence;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "membership_levels")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MembershipLevelEntity {

    @Id
    @Column(length = 50)
    private String id;

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
