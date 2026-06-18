package api.angia.com.modules.customer.api.v1.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ChangePasswordRequest {

    @NotBlank(message = "Mật khẩu cũ không được rỗng")
    private String oldPassword;

    @NotBlank(message = "Mật khẩu mới không được rỗng")
    @Size(min = 6, message = "Mật khẩu mới phải tối thiểu 6 ký tự")
    private String newPassword;
}
