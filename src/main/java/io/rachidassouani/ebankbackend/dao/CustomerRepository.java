package io.rachidassouani.ebankbackend.dao;

import io.rachidassouani.ebankbackend.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    List<Customer> findByNameContains(String name);
}
