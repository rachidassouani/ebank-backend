package io.rachidassouani.ebankbackend.service;

import io.rachidassouani.ebankbackend.dto.CustomerDTO;
import io.rachidassouani.ebankbackend.exception.CustomerNotFoundException;

import java.util.List;

public interface CustomerService {

    CustomerDTO save(CustomerDTO customerDTO);
    List<CustomerDTO> findAll();
    CustomerDTO findCustomerDTO(long customerId) throws CustomerNotFoundException;
    void delete(long customerId) throws CustomerNotFoundException;
}
