package io.rachidassouani.ebankbackend.service;

import io.rachidassouani.ebankbackend.dao.CustomerRepository;
import io.rachidassouani.ebankbackend.model.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerService.class);

    private CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public Customer save(Customer customer) {
        LOGGER.info("Saving new customer ...");
        Customer savedCustomer = customerRepository.save(customer);
        return savedCustomer;
    }

    @Override
    public List<Customer> findAll() {
        LOGGER.info("finding list of customers ...");
        return customerRepository.findAll();
    }
}
