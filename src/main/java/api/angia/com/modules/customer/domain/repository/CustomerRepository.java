package api.angia.com.modules.customer.domain.repository;

import api.angia.com.modules.customer.domain.model.Customer;

import java.util.Optional;

public interface CustomerRepository {
    Optional<Customer> findByUsername(String username);
    Optional<Customer> findById(Long id);
    Optional<Customer> findByEmail(String email);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    Customer save(Customer customer);
}
