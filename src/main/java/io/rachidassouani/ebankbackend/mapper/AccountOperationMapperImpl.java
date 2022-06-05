package io.rachidassouani.ebankbackend.mapper;

import io.rachidassouani.ebankbackend.dto.AccountOperationDTO;
import io.rachidassouani.ebankbackend.model.AccountOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class AccountOperationMapperImpl {

    public AccountOperationDTO fromAccountOperation(AccountOperation accountOperation) {
        AccountOperationDTO accountOperationDTO = new AccountOperationDTO();
        BeanUtils.copyProperties(accountOperation, accountOperationDTO);

        return accountOperationDTO;
    }
}
