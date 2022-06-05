package io.rachidassouani.ebankbackend.mapper;

import io.rachidassouani.ebankbackend.dto.CustomerDTO;
import io.rachidassouani.ebankbackend.dto.SavingAccountDTO;
import io.rachidassouani.ebankbackend.model.Customer;
import io.rachidassouani.ebankbackend.model.SavingAccount;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class SavingAccountMapperImpl {

    private final CustomerMapperImpl customerMapper;

    public SavingAccountMapperImpl(CustomerMapperImpl customerMapper) {
        this.customerMapper = customerMapper;
    }

    public SavingAccountDTO fromSavingAccount(SavingAccount savingAccount) {
        SavingAccountDTO savingAccountDTO = new SavingAccountDTO();
        BeanUtils.copyProperties(savingAccount, savingAccountDTO);

        // casting customer to customerDTO
        CustomerDTO customerDTO = customerMapper.fromCustomer(savingAccount.getCustomer());
        savingAccountDTO.setCustomerDTO(customerDTO);
        savingAccountDTO.setType(savingAccount.getClass().getSimpleName());

        return savingAccountDTO;
    }

    public SavingAccount fromSavingAccountDTO(SavingAccountDTO savingAccountDTO) {
        SavingAccount savingAccount = new SavingAccount();
        BeanUtils.copyProperties(savingAccountDTO, savingAccount);

        // casting customerDTO to customer
        Customer customer = customerMapper.fromCustomerDTO(savingAccountDTO.getCustomerDTO());
        savingAccount.setCustomer(customer);
        return savingAccount;
    }
}
