package api.angia.com.modules.product.application.service;

import api.angia.com.modules.product.api.v1.dto.request.CreateProductRequest;
import api.angia.com.modules.product.api.v1.dto.request.UpdateProductRequest;
import api.angia.com.modules.product.api.v1.dto.response.ProductResponse;
import api.angia.com.modules.product.application.mapper.ProductMapper;
import api.angia.com.modules.product.domain.exception.ProductException;
import api.angia.com.modules.product.domain.model.Product;
import api.angia.com.modules.product.domain.model.ProductTimeline;
import api.angia.com.modules.product.domain.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductApplicationService {

    private final ProductRepository productRepository;
    private final ProductMapper mapper;

    // ── Queries ────────────────────────────────────────────────────────────────

    public List<ProductResponse> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    public ProductResponse getProductById(String id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> ProductException.notFound(id));
        return mapper.toResponse(product);
    }

    public ProductResponse getProductBySlug(String slug) {
        Product product = productRepository.findBySlug(slug)
                .orElseThrow(() -> ProductException.notFound("slug=" + slug));
        return mapper.toResponse(product);
    }

    public List<ProductResponse> getProductsByCategory(String categoryId) {
        return productRepository.findByCategoryId(categoryId)
                .stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    // ── Commands ───────────────────────────────────────────────────────────────

    @Transactional
    public ProductResponse createProduct(CreateProductRequest request) {
        // Kiểm tra slug đã tồn tại chưa
        if (request.getSlug() != null && productRepository.findBySlug(request.getSlug()).isPresent()) {
            throw ProductException.slugAlreadyExists(request.getSlug());
        }

        Product product = mapper.toDomain(request);
        Product saved = productRepository.save(product);
        return mapper.toResponse(saved);
    }

    @Transactional
    public ProductResponse updateProduct(String id, UpdateProductRequest request) {
        Product existing = productRepository.findById(id)
                .orElseThrow(() -> ProductException.notFound(id));

        // Kiểm tra slug mới có bị trùng không (trừ chính nó)
        if (request.getSlug() != null && !request.getSlug().equals(existing.getSlug())) {
            productRepository.findBySlug(request.getSlug()).ifPresent(p -> {
                throw ProductException.slugAlreadyExists(request.getSlug());
            });
        }

        // Cập nhật các fields nếu được cung cấp trong request
        if (request.getName() != null)
            existing.setName(request.getName());
        if (request.getSlug() != null)
            existing.setSlug(request.getSlug());
        if (request.getCategoryId() != null)
            existing.setCategoryId(request.getCategoryId());
        if (request.getPrice() != null)
            existing.setPrice(request.getPrice());
        if (request.getOriginalPrice() != null)
            existing.setOriginalPrice(request.getOriginalPrice());
        if (request.getDiscount() != null)
            existing.setDiscount(request.getDiscount());
        if (request.getImage() != null)
            existing.setImage(request.getImage());
        if (request.getImages() != null)
            existing.setImages(request.getImages());
        if (request.getDescription() != null)
            existing.setDescription(request.getDescription());
        if (request.getShortDescription() != null)
            existing.setShortDescription(request.getShortDescription());
        if (request.getBenefits() != null)
            existing.setBenefits(request.getBenefits());
        if (request.getUsage() != null)
            existing.setUsage(request.getUsage());
        if (request.getAttributes() != null)
            existing.setAttributes(request.getAttributes());
        if (request.getOrigin() != null)
            existing.setOrigin(request.getOrigin());
        if (request.getCertifications() != null)
            existing.setCertifications(request.getCertifications());
        if (request.getInStock() != null)
            existing.setInStock(request.getInStock());
        if (request.getQrCode() != null)
            existing.setQrCode(request.getQrCode());
        if (request.getBatch() != null)
            existing.setBatch(request.getBatch());
        if (request.getProductionDate() != null)
            existing.setProductionDate(request.getProductionDate());
        if (request.getExpiryDate() != null)
            existing.setExpiryDate(request.getExpiryDate());
        if (request.getRegion() != null)
            existing.setRegion(request.getRegion());

        // Cập nhật timeline
        if (request.getTimeline() != null) {
            List<ProductTimeline> updatedTimeline = request.getTimeline().stream()
                    .map(t -> ProductTimeline.builder()
                            .date(t.getDate())
                            .event(t.getEvent())
                            .build())
                    .collect(Collectors.toList());
            existing.setTimeline(updatedTimeline);
        }

        Product saved = productRepository.save(existing);
        return mapper.toResponse(saved);
    }

    @Transactional
    public void deleteProduct(String id) {
        productRepository.findById(id)
                .orElseThrow(() -> ProductException.notFound(id));
        productRepository.deleteById(id);
    }
}
