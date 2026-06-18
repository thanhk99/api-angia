package api.angia.com.modules.customer.domain.exception;

public class CustomerException extends RuntimeException {

    public CustomerException(String message) {
        super(message);
    }

    public static CustomerException notFound(Long id) {
        return new CustomerException("Không tìm thấy khách hàng với ID: " + id);
    }

    public static CustomerException notFound(String username) {
        return new CustomerException("Không tìm thấy khách hàng với username: " + username);
    }

    public static CustomerException usernameAlreadyExists(String username) {
        return new CustomerException("Username đã tồn tại: " + username);
    }

    public static CustomerException emailAlreadyExists(String email) {
        return new CustomerException("Email đã tồn tại: " + email);
    }

    public static CustomerException invalidCredentials() {
        return new CustomerException("Tên đăng nhập hoặc mật khẩu không đúng");
    }

    public static CustomerException accountDisabled() {
        return new CustomerException("Tài khoản đã bị khoá");
    }

    public static CustomerException unauthorized() {
        return new CustomerException("Bạn không có quyền thực hiện thao tác này");
    }
}
