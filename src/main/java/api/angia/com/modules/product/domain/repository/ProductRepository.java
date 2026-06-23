package api.angia.com.modules.product.domain.repository;

import api.angia.com.modules.product.domain.model.Product;

import java.util.List;
import java.util.Optional;

/**
 * Domain Repository Interface — thuần Java, không có Spring/JPA annotation.
 * Implementation nằm trong infrastructure/persistence/ProductRepositoryImpl.java
 */
public interface ProductRepository {
    Optional<Product> findById(String id);
    Optional<Product> findBySlug(String slug);
    List<Product> findAll();
    List<Product> findByCategoryId(String categoryId);
    Product save(Product product);
    void deleteById(String id);
}
