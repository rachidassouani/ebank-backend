package io.rachidassouani.ebankbackend.service;

import io.rachidassouani.ebankbackend.dto.AccountDTO;
import io.rachidassouani.ebankbackend.dto.CurrentAccountDTO;
import io.rachidassouani.ebankbackend.dto.SavingAccountDTO;
import io.rachidassouani.ebankbackend.exception.AccountNotFoundException;
import io.rachidassouani.ebankbackend.exception.BalanceNotSufficientException;
import io.rachidassouani.ebankbackend.exception.CustomerNotFoundException;
import io.rachidassouani.ebankbackend.model.Account;

import java.util.List;

public interface AccountService {

    AccountDTO findAccountDTO(long accountId) throws AccountNotFoundException;
    Account findAccount(long accountId) throws AccountNotFoundException;
    List<AccountDTO> findAll();
    CurrentAccountDTO saveCurrentAccount(long customerId, double initBalance, double overDraft) throws CustomerNotFoundException;
    SavingAccountDTO saveSavingAccount(long customerId, double initBalance, double interestRate) throws CustomerNotFoundException;
    void debit(long accountId, double amount, String description) throws AccountNotFoundException, BalanceNotSufficientException;
    void credit(long accountId, double amount, String description) throws AccountNotFoundException, BalanceNotSufficientException;
    void transfer(long accountIdSource, long accountIdDestination, double amount) throws AccountNotFoundException, BalanceNotSufficientException;
}
