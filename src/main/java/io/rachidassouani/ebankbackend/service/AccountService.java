package io.rachidassouani.ebankbackend.service;

import io.rachidassouani.ebankbackend.exception.AccountNotFoundException;
import io.rachidassouani.ebankbackend.exception.BalanceNotSufficientException;
import io.rachidassouani.ebankbackend.exception.CustomerNotFoundException;
import io.rachidassouani.ebankbackend.model.Account;
import io.rachidassouani.ebankbackend.model.CurrentAccount;
import io.rachidassouani.ebankbackend.model.SavingAccount;

import java.util.List;

public interface AccountService {

    Account findAccount(long accountId) throws AccountNotFoundException;
    List<Account> findAll();
    CurrentAccount saveCurrentAccount(long customerId, double initBalance, double overDraft) throws CustomerNotFoundException;
    SavingAccount saveSavingAccount(long customerId, double initBalance, double interestRate) throws CustomerNotFoundException;
    void debit(long accountId, double amount, String description) throws AccountNotFoundException, BalanceNotSufficientException;
    void credit(long accountId, double amount, String description) throws AccountNotFoundException, BalanceNotSufficientException;
    void transfer(long accountIdSource, long accountIdDestination, double amount) throws AccountNotFoundException, BalanceNotSufficientException;
}
