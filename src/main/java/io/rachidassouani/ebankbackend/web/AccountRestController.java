package io.rachidassouani.ebankbackend.web;

import io.rachidassouani.ebankbackend.dto.*;
import io.rachidassouani.ebankbackend.exception.AccountNotFoundException;
import io.rachidassouani.ebankbackend.exception.BalanceNotSufficientException;
import io.rachidassouani.ebankbackend.service.AccountOperationService;
import io.rachidassouani.ebankbackend.service.AccountService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/accounts")
@CrossOrigin("*")
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
    public List<AccountOperationDTO> findAllAccountOperationByAccountId(@PathVariable("accountId") long accountId) {
        return accountOperationService.findAllByAccountId(accountId);
    }

    @GetMapping("{accountId}/pageOperations")
    public AccountHistoryDTO findAccountHistoryByAccountId(@PathVariable("accountId") long accountId) throws AccountNotFoundException {
        return accountOperationService.findAccountHistoryByAccountId(accountId);
    }

    @PostMapping("debit")
    public DebitDTO debut(@RequestBody DebitDTO debitDTO) throws AccountNotFoundException, BalanceNotSufficientException {
        accountService.debit(debitDTO.getAccountId(), debitDTO.getAmount(), debitDTO.getDescription());
        return debitDTO;
    }

    @PostMapping("credit")
    public CreditDTO debut(@RequestBody CreditDTO creditDTO) throws AccountNotFoundException, BalanceNotSufficientException {
        accountService.credit(creditDTO.getAccountId(), creditDTO.getAmount(), creditDTO.getDescription());
        return creditDTO;
    }

    @PostMapping("transfer")
    public void debut(@RequestBody TransferDTO transferDTO) throws AccountNotFoundException, BalanceNotSufficientException {
        accountService.transfer(
                transferDTO.getIdAccountSource(),
                transferDTO.getIdAccountDestination(),
                transferDTO.getAmount(),
                transferDTO.getDescription());
    }
}
