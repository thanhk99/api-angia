package api.angia.com.modules.customer.api.v1.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomerResponse {
    private String id;
    private String username;
    private String fullName;
    private String email;
    private String provider;
    private boolean active;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
