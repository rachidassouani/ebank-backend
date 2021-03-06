package io.rachidassouani.ebankbackend.service;

import io.rachidassouani.ebankbackend.dao.CustomerRepository;
import io.rachidassouani.ebankbackend.dto.CustomerDTO;
import io.rachidassouani.ebankbackend.exception.CustomerNotFoundException;
import io.rachidassouani.ebankbackend.mapper.CustomerMapperImpl;
import io.rachidassouani.ebankbackend.model.Customer;
import io.rachidassouani.ebankbackend.util.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerService.class);

    private final CustomerRepository customerRepository;
    private final CustomerMapperImpl customerMapper;

    public CustomerServiceImpl(CustomerRepository customerRepository, CustomerMapperImpl customerMapper) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
    }

    @Override
    public CustomerDTO save(CustomerDTO customerDTO) {
        LOGGER.info("Saving new customer ...");
        // casting the customerDTO to customer
        Customer customerToSave = customerMapper.fromCustomerDTO(customerDTO);
        // saving customer
        Customer savedCustomer = customerRepository.save(customerToSave);
        // casting saved customer to customerDTO
        return customerMapper.fromCustomer(savedCustomer);
    }

    @Override
    public List<CustomerDTO> findAll() {
        LOGGER.info("finding list of customers ...");
        List<Customer> customerList = customerRepository.findAll();
        List<CustomerDTO> customerDTOS = customerList.stream().map(customer -> customerMapper.fromCustomer(customer)).collect(Collectors.toList());
        return customerDTOS;
    }

    @Override
    public CustomerDTO findCustomerDTO(long customerId) throws CustomerNotFoundException {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(()-> new CustomerNotFoundException(Constant.CUSTOMER_ID_NOT_EXIST));

        return customerMapper.fromCustomer(customer);
    }

    @Override
    public void delete(long customerId) throws CustomerNotFoundException {
        CustomerDTO customerDTO = findCustomerDTO(customerId);
        customerRepository.deleteById(customerDTO.getId());
    }

    @Override
    public List<CustomerDTO> findCustomerDTOsByName(String name) throws CustomerNotFoundException {
        List<Customer> customers = customerRepository.findByNameContains(name);
        List<CustomerDTO> customerDTOS = customers.stream().map(customer -> customerMapper.fromCustomer(customer)).collect(Collectors.toList());
        return customerDTOS;
    }
}
