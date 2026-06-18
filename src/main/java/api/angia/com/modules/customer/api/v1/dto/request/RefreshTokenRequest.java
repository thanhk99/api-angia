package api.angia.com.modules.customer.api.v1.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RefreshTokenRequest {

    @NotBlank(message = "Refresh token không được rỗng")
    private String refreshToken;
}
