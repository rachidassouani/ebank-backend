package io.rachidassouani.ebankbackend.web;

import io.rachidassouani.ebankbackend.dto.CustomerDTO;
import io.rachidassouani.ebankbackend.exception.CustomerNotFoundException;
import io.rachidassouani.ebankbackend.service.CustomerService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/customers")
public class CustomerRestController {

    private final CustomerService customerService;

    public CustomerRestController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public List<CustomerDTO> customersList() {
        return customerService.findAll();
    }

    @GetMapping("{customerId}")
    public CustomerDTO customerDTO(@PathVariable("customerId") long customerId) throws CustomerNotFoundException {
        return customerService.findCustomerDTO(customerId);
    }

    @PostMapping
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO) {
        return customerService.save(customerDTO);
    }

    @DeleteMapping("{customerId}")
    public void deleteCustomer(@PathVariable("customerId") long customerId) throws CustomerNotFoundException {
        customerService.delete(customerId);
    }
}
