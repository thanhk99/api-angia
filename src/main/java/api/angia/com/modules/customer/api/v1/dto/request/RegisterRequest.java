package api.angia.com.modules.customer.api.v1.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterRequest {

    @NotBlank(message = "Username không được rỗng")
    @Size(min = 4, max = 50, message = "Username phải từ 4-50 ký tự")
    private String username;

    @NotBlank(message = "Password không được rỗng")
    @Size(min = 6, message = "Password phải tối thiểu 6 ký tự")
    private String password;

    @Email(message = "Email không hợp lệ")
    private String email;

    private String fullName;
}
