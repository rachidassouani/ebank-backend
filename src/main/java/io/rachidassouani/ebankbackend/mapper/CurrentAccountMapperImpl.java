package io.rachidassouani.ebankbackend.mapper;

import io.rachidassouani.ebankbackend.dto.CurrentAccountDTO;
import io.rachidassouani.ebankbackend.dto.CustomerDTO;
import io.rachidassouani.ebankbackend.model.CurrentAccount;
import io.rachidassouani.ebankbackend.model.Customer;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class CurrentAccountMapperImpl {

    private final CustomerMapperImpl customerMapper;

    public CurrentAccountMapperImpl(CustomerMapperImpl customerMapper) {
        this.customerMapper = customerMapper;
    }

    public CurrentAccountDTO fromCurrentAccount(CurrentAccount currentAccount) {
        CurrentAccountDTO currentAccountDTO = new CurrentAccountDTO();
        BeanUtils.copyProperties(currentAccount, currentAccountDTO);

        // casting customer to customerDTO
        CustomerDTO customerDTO = customerMapper.fromCustomer(currentAccount.getCustomer());
        currentAccountDTO.setCustomerDTO(customerDTO);
        currentAccountDTO.setType(currentAccount.getClass().getSimpleName());

        return currentAccountDTO;
    }

    public CurrentAccount fromCurrentAccountDTO(CurrentAccountDTO currentAccountDTO) {
        CurrentAccount currentAccount = new CurrentAccount();
        BeanUtils.copyProperties(currentAccountDTO, currentAccount);

        // casting customerDTO to customer
        Customer customer = customerMapper.fromCustomerDTO(currentAccountDTO.getCustomerDTO());
        currentAccount.setCustomer(customer);

        return currentAccount;
    }
}
