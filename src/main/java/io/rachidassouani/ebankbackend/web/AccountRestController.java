package io.rachidassouani.ebankbackend.web;

import io.rachidassouani.ebankbackend.dto.AccountDTO;
import io.rachidassouani.ebankbackend.dto.AccountOperationDTO;
import io.rachidassouani.ebankbackend.exception.AccountNotFoundException;
import io.rachidassouani.ebankbackend.service.AccountOperationService;
import io.rachidassouani.ebankbackend.service.AccountService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/accounts")
public class AccountRestController {

    private final AccountService accountService;
    private final AccountOperationService accountOperationService;

    public AccountRestController(AccountService accountService, AccountOperationService accountOperationService) {
        this.accountService = accountService;
        this.accountOperationService = accountOperationService;
    }

    @GetMapping("{accountId}")
    public AccountDTO findAccount(@PathVariable("accountId") long accountId) throws AccountNotFoundException {
        return accountService.findAccountDTO(accountId);
    }

    @GetMapping
    public List<AccountDTO> findAll() {
        return accountService.findAll();
    }

    @GetMapping("{accountId}/operations")
    public List<AccountOperationDTO> findAllAccountOperationByAccountId(long accountId) {
        return accountOperationService.findAllByAccountId(accountId);
    }
}
