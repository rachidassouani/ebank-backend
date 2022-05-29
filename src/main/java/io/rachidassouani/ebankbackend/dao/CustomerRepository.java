package io.rachidassouani.ebankbackend.dao;

import io.rachidassouani.ebankbackend.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
