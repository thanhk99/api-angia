package api.angia.com.modules.product.infrastructure.persistence;

import api.angia.com.modules.product.domain.model.Product;
import api.angia.com.modules.product.domain.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Adapter chuyển đổi giữa JPA Entity và Domain Model.
 * timeline được Hibernate tự động serialize/deserialize
 * từ List<ProductTimeline> ↔ jsonb thông qua @JdbcTypeCode(SqlTypes.JSON).
 */
@Repository
@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepository {

    private final ProductJpaRepository jpaRepository;

    @Override
    public Optional<Product> findById(String id) {
        return jpaRepository.findById(id).map(this::toDomain);
    }

    @Override
    public Optional<Product> findBySlug(String slug) {
        return jpaRepository.findBySlug(slug).map(this::toDomain);
    }

    @Override
    public List<Product> findAll() {
        return jpaRepository.findAll()
                .stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Product> findByCategoryId(String categoryId) {
        return jpaRepository.findByCategoryId(categoryId)
                .stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Product save(Product domain) {
        ProductEntity entity = toEntity(domain);
        ProductEntity saved = jpaRepository.save(entity);
        return toDomain(saved);
    }

    @Override
    public void deleteById(String id) {
        jpaRepository.deleteById(id);
    }

    // ── Chuyển Entity → Domain Model ──────────────────────────────────────────
    private Product toDomain(ProductEntity entity) {
        return Product.builder()
                .id(entity.getId())
                .name(entity.getName())
                .slug(entity.getSlug())
                .categoryId(entity.getCategoryId())
                .price(entity.getPrice())
                .originalPrice(entity.getOriginalPrice())
                .discount(entity.getDiscount())
                .image(entity.getImage())
                .images(entity.getImages())
                .description(entity.getDescription())
                .shortDescription(entity.getShortDescription())
                .benefits(entity.getBenefits())
                .usage(entity.getUsage())
                .attributes(entity.getAttributes())
                .origin(entity.getOrigin())
                .certifications(entity.getCertifications())
                .rating(entity.getRating())
                .soldCount(entity.getSoldCount())
                .inStock(entity.getInStock())
                .timeline(entity.getTimeline())   // Hibernate tự deserialize jsonb → List<ProductTimeline>
                .qrCode(entity.getQrCode())
                .batch(entity.getBatch())
                .productionDate(entity.getProductionDate())
                .expiryDate(entity.getExpiryDate())
                .region(entity.getRegion())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

    // ── Chuyển Domain Model → Entity ──────────────────────────────────────────
    private ProductEntity toEntity(Product domain) {
        return ProductEntity.builder()
                .id(domain.getId())
                .name(domain.getName())
                .slug(domain.getSlug())
                .categoryId(domain.getCategoryId())
                .price(domain.getPrice())
                .originalPrice(domain.getOriginalPrice())
                .discount(domain.getDiscount())
                .image(domain.getImage())
                .images(domain.getImages())
                .description(domain.getDescription())
                .shortDescription(domain.getShortDescription())
                .benefits(domain.getBenefits())
                .usage(domain.getUsage())
                .attributes(domain.getAttributes())
                .origin(domain.getOrigin())
                .certifications(domain.getCertifications())
                .rating(domain.getRating())
                .soldCount(domain.getSoldCount())
                .inStock(domain.getInStock())
                .timeline(domain.getTimeline())   // Hibernate tự serialize List<ProductTimeline> → jsonb
                .qrCode(domain.getQrCode())
                .batch(domain.getBatch())
                .productionDate(domain.getProductionDate())
                .expiryDate(domain.getExpiryDate())
                .region(domain.getRegion())
                .createdAt(domain.getCreatedAt())
                .updatedAt(domain.getUpdatedAt())
                .build();
    }
}
