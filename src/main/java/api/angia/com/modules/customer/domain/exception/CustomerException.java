package api.angia.com.modules.customer.domain.exception;

import org.springframework.http.HttpStatus;

public class CustomerException extends RuntimeException {

    private HttpStatus status;

    public CustomerException(String message) {
        super(message);
    }

    public CustomerException(HttpStatus status, String message) {
        super(message);
        this.status = status;
    }

    public static CustomerException notFoundById(String id) {
        return new CustomerException(HttpStatus.NOT_FOUND, "Không tìm thấy khách hàng với id: " + id);
    }

    public static CustomerException notFound(String username) {
        return new CustomerException(HttpStatus.NOT_FOUND, "Không tìm thấy khách hàng với username: " + username);
    }

    public static CustomerException usernameAlreadyExists(String username) {
        return new CustomerException(HttpStatus.BAD_REQUEST, "Username đã tồn tại: " + username);
    }

    public static CustomerException emailAlreadyExists(String email) {
        return new CustomerException(HttpStatus.BAD_REQUEST, "Email đã tồn tại: " + email);
    }

    public static CustomerException invalidCredentials() {
        return new CustomerException(HttpStatus.UNAUTHORIZED, "Tên đăng nhập hoặc mật khẩu không đúng");
    }

    public static CustomerException accountDisabled() {
        return new CustomerException(HttpStatus.FORBIDDEN, "Tài khoản đã bị khoá");
    }

    public static CustomerException unauthorized() {
        return new CustomerException(HttpStatus.UNAUTHORIZED, "Bạn không có quyền thực hiện thao tác này");
    }
}
