package api.angia.com.modules.customer.application.mapper;

import api.angia.com.modules.customer.api.v1.dto.response.CustomerResponse;
import api.angia.com.modules.customer.domain.model.Customer;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapper {

    // Domain Model -> Response DTO (không expose password)
    public CustomerResponse toResponse(Customer customer) {
        if (customer == null) return null;
        return CustomerResponse.builder()
                .id(customer.getId())
                .username(customer.getUsername())
                .fullName(customer.getFullName())
                .email(customer.getEmail())
                .provider(customer.getProvider())
                .active(customer.isActive())
                .createdAt(customer.getCreatedAt())
                .updatedAt(customer.getUpdatedAt())
                .build();
    }
}
