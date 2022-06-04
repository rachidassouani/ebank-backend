package io.rachidassouani.ebankbackend.service;

import io.rachidassouani.ebankbackend.model.Customer;

import java.util.List;

public interface CustomerService {

    Customer save(Customer customer);
    List<Customer> findAll();

}
