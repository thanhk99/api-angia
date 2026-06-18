package api.angia.com.modules.customer.api.v1.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequest {

    @NotBlank(message = "Username không được rỗng")
    private String username;

    @NotBlank(message = "Password không được rỗng")
    private String password;
}
