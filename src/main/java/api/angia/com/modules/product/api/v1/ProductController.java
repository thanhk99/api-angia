package api.angia.com.modules.product.api.v1;

import api.angia.com.modules.product.api.v1.dto.request.CreateProductRequest;
import api.angia.com.modules.product.api.v1.dto.request.UpdateProductRequest;
import api.angia.com.modules.product.api.v1.dto.response.ProductResponse;
import api.angia.com.modules.product.application.service.ProductApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductApplicationService applicationService;

    @GetMapping
    @PreAuthorize("permitAll()")
    public ResponseEntity<List<ProductResponse>> getAllProducts() {
        return ResponseEntity.ok(applicationService.getAllProducts());
    }

    @GetMapping("/{id}")
    @PreAuthorize("permitAll()")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable String id) {
        return ResponseEntity.ok(applicationService.getProductById(id));
    }

    @GetMapping("/slug/{slug}")
    @PreAuthorize("permitAll()")
    public ResponseEntity<ProductResponse> getProductBySlug(@PathVariable String slug) {
        return ResponseEntity.ok(applicationService.getProductBySlug(slug));
    }

    @GetMapping("/category/{categoryId}")
    @PreAuthorize("permitAll()")
    public ResponseEntity<List<ProductResponse>> getProductsByCategory(@PathVariable String categoryId) {
        return ResponseEntity.ok(applicationService.getProductsByCategory(categoryId));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN')")
    public ResponseEntity<ProductResponse> createProduct(@RequestBody CreateProductRequest request) {
        return ResponseEntity.ok(applicationService.createProduct(request));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN')")
    public ResponseEntity<ProductResponse> updateProduct(
            @PathVariable String id,
            @RequestBody UpdateProductRequest request) {
        return ResponseEntity.ok(applicationService.updateProduct(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<Void> deleteProduct(@PathVariable String id) {
        applicationService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}
