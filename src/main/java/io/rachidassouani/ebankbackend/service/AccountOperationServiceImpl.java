package io.rachidassouani.ebankbackend.service;

import io.rachidassouani.ebankbackend.dao.AccountOperationRepository;
import io.rachidassouani.ebankbackend.dto.AccountOperationDTO;
import io.rachidassouani.ebankbackend.mapper.AccountOperationMapperImpl;
import io.rachidassouani.ebankbackend.model.AccountOperation;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class AccountOperationServiceImpl implements AccountOperationService {

    private final AccountOperationRepository accountOperationRepository;
    private final AccountOperationMapperImpl accountOperationMapper;

    public AccountOperationServiceImpl(AccountOperationRepository accountOperationRepository, AccountOperationMapperImpl accountOperationMapper) {
        this.accountOperationRepository = accountOperationRepository;
        this.accountOperationMapper = accountOperationMapper;
    }


    @Override
    public List<AccountOperationDTO> findAllByAccountId(long accountId) {
        List<AccountOperation> listAccountOperation =
                accountOperationRepository.findAccountOperationByAccountId(accountId);

        // Casting list of AccountOperation to AccountOperationDTO
        List<AccountOperationDTO> accountOperationDTOS = listAccountOperation
                .stream()
                .map(accountOperation -> accountOperationMapper.fromAccountOperation(accountOperation))
                .collect(Collectors.toList());

        return accountOperationDTOS;
    }
}
