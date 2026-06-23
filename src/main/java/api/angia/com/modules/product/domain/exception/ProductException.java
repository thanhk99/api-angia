package api.angia.com.modules.product.domain.exception;

import org.springframework.http.HttpStatus;

public class ProductException extends RuntimeException {

    private final HttpStatus status;

    public ProductException(HttpStatus status, String message) {
        super(message);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public static ProductException notFound(String id) {
        return new ProductException(HttpStatus.NOT_FOUND,
                "Sản phẩm không tồn tại với id: " + id);
    }

    public static ProductException slugAlreadyExists(String slug) {
        return new ProductException(HttpStatus.CONFLICT,
                "Slug đã tồn tại: " + slug);
    }

    public static ProductException invalidState(String message) {
        return new ProductException(HttpStatus.CONFLICT, message);
    }

    public static ProductException unauthorized(String action) {
        return new ProductException(HttpStatus.FORBIDDEN,
                "Bạn không có quyền: " + action);
    }
}
