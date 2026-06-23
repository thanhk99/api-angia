package api.angia.com.modules.product.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductJpaRepository extends JpaRepository<ProductEntity, String> {

    Optional<ProductEntity> findBySlug(String slug);

    List<ProductEntity> findByCategoryId(String categoryId);
}
