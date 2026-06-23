package api.angia.com.modules.product.application.mapper;

import api.angia.com.modules.product.api.v1.dto.request.CreateProductRequest;
import api.angia.com.modules.product.api.v1.dto.request.TimelineItemRequest;
import api.angia.com.modules.product.api.v1.dto.response.ProductResponse;
import api.angia.com.modules.product.api.v1.dto.response.TimelineItemResponse;
import api.angia.com.modules.product.domain.model.Product;
import api.angia.com.modules.product.domain.model.ProductTimeline;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProductMapper {

    // ── Request → Domain Model ─────────────────────────────────────────────────

    public Product toDomain(CreateProductRequest request) {
        return Product.createNew(
                request.getName(),
                request.getSlug(),
                request.getCategoryId(),
                request.getPrice(),
                request.getOriginalPrice(),
                request.getDiscount(),
                request.getImage(),
                request.getImages(),
                request.getDescription(),
                request.getShortDescription(),
                request.getBenefits(),
                request.getUsage(),
                request.getAttributes(),
                request.getOrigin(),
                request.getCertifications(),
                request.getInStock(),
                toTimelineDomain(request.getTimeline()),
                request.getQrCode(),
                request.getBatch(),
                request.getProductionDate(),
                request.getExpiryDate(),
                request.getRegion()
        );
    }

    // ── Domain Model → Response DTO ────────────────────────────────────────────

    public ProductResponse toResponse(Product domain) {
        return ProductResponse.builder()
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
                .timeline(toTimelineResponse(domain.getTimeline()))
                .qrCode(domain.getQrCode())
                .batch(domain.getBatch())
                .productionDate(domain.getProductionDate())
                .expiryDate(domain.getExpiryDate())
                .region(domain.getRegion())
                .createdAt(domain.getCreatedAt())
                .updatedAt(domain.getUpdatedAt())
                .build();
    }

    // ── Timeline mapping helpers ───────────────────────────────────────────────

    private List<ProductTimeline> toTimelineDomain(List<TimelineItemRequest> requests) {
        if (requests == null) return Collections.emptyList();
        return requests.stream()
                .map(r -> ProductTimeline.builder()
                        .date(r.getDate())
                        .event(r.getEvent())
                        .build())
                .collect(Collectors.toList());
    }

    private List<TimelineItemResponse> toTimelineResponse(List<ProductTimeline> timelines) {
        if (timelines == null) return Collections.emptyList();
        return timelines.stream()
                .map(t -> TimelineItemResponse.builder()
                        .date(t.getDate())
                        .event(t.getEvent())
                        .build())
                .collect(Collectors.toList());
    }
}
