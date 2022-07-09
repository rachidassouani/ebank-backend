package io.rachidassouani.ebankbackend.service;

import io.rachidassouani.ebankbackend.dao.AccountOperationRepository;
import io.rachidassouani.ebankbackend.dto.AccountHistoryDTO;
import io.rachidassouani.ebankbackend.dto.AccountOperationDTO;
import io.rachidassouani.ebankbackend.exception.AccountNotFoundException;
import io.rachidassouani.ebankbackend.mapper.AccountOperationMapperImpl;
import io.rachidassouani.ebankbackend.model.Account;
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
    private final AccountService accountService;

    public AccountOperationServiceImpl(AccountOperationRepository accountOperationRepository, AccountOperationMapperImpl accountOperationMapper, AccountService accountService) {
        this.accountOperationRepository = accountOperationRepository;
        this.accountOperationMapper = accountOperationMapper;
        this.accountService = accountService;
    }


    @Override
    public List<AccountOperationDTO> findAllByAccountId(long accountId) {
        List<AccountOperation> listAccountOperation =
                accountOperationRepository.findAccountOperationByAccountIdOrderByDateDesc(accountId);

        // Casting list of AccountOperation to AccountOperationDTO
        List<AccountOperationDTO> accountOperationDTOS = listAccountOperation
                .stream()
                .map(accountOperation -> accountOperationMapper.fromAccountOperation(accountOperation))
                .collect(Collectors.toList());

        return accountOperationDTOS;
    }

    @Override
    public AccountHistoryDTO findAccountHistoryByAccountId(long accountId) throws AccountNotFoundException {

        Account account = accountService.findAccount(accountId);
        if (account == null)
            throw new AccountNotFoundException("Account not found");
        List<AccountOperation> listAccountOperation =
                accountOperationRepository.findAccountOperationByAccountIdOrderByDateDesc(accountId);

        // Casting list of AccountOperation to AccountOperationDTO
        List<AccountOperationDTO> accountOperationDTOS = listAccountOperation
                .stream()
                .map(accountOperation -> accountOperationMapper.fromAccountOperation(accountOperation))
                .collect(Collectors.toList());

        AccountHistoryDTO accountHistoryDTO = new AccountHistoryDTO();
        accountHistoryDTO.setAccountOperationDTOS(accountOperationDTOS);
        accountHistoryDTO.setAccountId(account.getId());
        accountHistoryDTO.setBalance(account.getBalance());
        return accountHistoryDTO;
    }

}
