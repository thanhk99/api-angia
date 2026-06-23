package api.angia.com.modules.customer.infrastructure.persistence;

import api.angia.com.modules.customer.application.mapper.CustomerMapper;
import api.angia.com.modules.customer.domain.model.Customer;
import api.angia.com.modules.customer.domain.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CustomerRepositoryImpl implements CustomerRepository {

    private final CustomerJpaRepository jpaRepository;

    @Override
    public Optional<Customer> findByUsername(String username) {
        return jpaRepository.findByUsername(username).map(this::toModel);
    }

    @Override
    public Optional<Customer> findById(String id) {
        return jpaRepository.findById(id).map(this::toModel);
    }

    @Override
    public Optional<Customer> findByEmail(String email) {
        return jpaRepository.findByEmail(email).map(this::toModel);
    }

    @Override
    public boolean existsByUsername(String username) {
        return jpaRepository.existsByUsername(username);
    }

    @Override
    public boolean existsByEmail(String email) {
        return jpaRepository.existsByEmail(email);
    }

    @Override
    public Customer save(Customer customer) {
        CustomerEntity entity = toEntity(customer);
        return toModel(jpaRepository.save(entity));
    }

    private Customer toModel(CustomerEntity entity) {
        return Customer.builder()
                .id(entity.getId())
                .username(entity.getUsername())
                .password(entity.getPassword())
                .provider(entity.getProvider())
                .providerId(entity.getProviderId())
                .fullName(entity.getFullName())
                .email(entity.getEmail())
                .active(entity.isActive())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .createdBy(entity.getCreatedBy())
                .updatedBy(entity.getUpdatedBy())
                .build();
    }

    private CustomerEntity toEntity(Customer model) {
        return CustomerEntity.builder()
                .id(model.getId())
                .username(model.getUsername())
                .password(model.getPassword())
                .provider(model.getProvider())
                .providerId(model.getProviderId())
                .fullName(model.getFullName())
                .email(model.getEmail())
                .active(model.isActive())
                .createdAt(model.getCreatedAt())
                .updatedAt(model.getUpdatedAt())
                .createdBy(model.getCreatedBy())
                .updatedBy(model.getUpdatedBy())
                .build();
    }
}
