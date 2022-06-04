package io.rachidassouani.ebankbackend.service;

import io.rachidassouani.ebankbackend.dao.AccountOperationRepository;
import io.rachidassouani.ebankbackend.dao.AccountRepository;
import io.rachidassouani.ebankbackend.dao.CustomerRepository;
import io.rachidassouani.ebankbackend.enums.AccountStatus;
import io.rachidassouani.ebankbackend.enums.OperationType;
import io.rachidassouani.ebankbackend.exception.AccountNotFoundException;
import io.rachidassouani.ebankbackend.exception.BalanceNotSufficientException;
import io.rachidassouani.ebankbackend.exception.CustomerNotFoundException;
import io.rachidassouani.ebankbackend.model.*;
import io.rachidassouani.ebankbackend.util.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class AccountServiceImpl implements AccountService {
    private final static Logger LOGGER = LoggerFactory.getLogger(AccountServiceImpl.class);

    private AccountRepository accountRepository;
    private CustomerRepository customerRepository;
    private AccountOperationRepository accountOperationRepository;

    public AccountServiceImpl(AccountRepository accountRepository, CustomerRepository customerRepository, AccountOperationRepository accountOperationRepository) {
        this.accountRepository = accountRepository;
        this.customerRepository = customerRepository;
        this.accountOperationRepository = accountOperationRepository;
    }

    @Override
    public Account findAccount(long accountId) throws AccountNotFoundException {
        LOGGER.info("finding account by its ID ..");
        return accountRepository.findById(accountId)
                .orElseThrow(() -> new AccountNotFoundException(Constant.ACCOUNT_ID_NOT_EXIST));
    }

    @Override
    public List<Account> findAll() {
        return accountRepository.findAll();
    }

    @Override
    public CurrentAccount saveCurrentAccount(long customerId, double initBalance, double overDraft) throws CustomerNotFoundException {
        LOGGER.info("Saving new current account ..");
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException(Constant.CUSTOMER_ID_NOT_EXIST));

        CurrentAccount currentAccount = new CurrentAccount();
        currentAccount.setBalance(initBalance);
        currentAccount.setCustomer(customer);
        currentAccount.setAccountStatus(AccountStatus.CREATED);
        currentAccount.setOverDraft(overDraft);
        currentAccount.setCreatedAt(LocalDate.now());

        CurrentAccount savedAccount = accountRepository.save(currentAccount);
        return savedAccount;
    }

    @Override
    public SavingAccount saveSavingAccount(long customerId, double initBalance, double interestRate) throws CustomerNotFoundException {
        LOGGER.info("Saving new current account ..");
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException(Constant.CUSTOMER_ID_NOT_EXIST));

        SavingAccount savingAccount = new SavingAccount();
        savingAccount.setBalance(initBalance);
        savingAccount.setCustomer(customer);
        savingAccount.setAccountStatus(AccountStatus.CREATED);
        savingAccount.setInterest(interestRate);
        savingAccount.setCreatedAt(LocalDate.now());

        SavingAccount savedAccount = accountRepository.save(savingAccount);
        return savedAccount;
    }

    @Override
    public void debit(long accountId, double amount, String description) throws AccountNotFoundException, BalanceNotSufficientException {
        Account account = findAccount(accountId);

        if (amount > account.getBalance()) {
            throw new BalanceNotSufficientException(Constant.BALANCE_NOT_SUFFICIENT);
        }

        // update the balance of the account
        account.setBalance(account.getBalance() - amount);
        accountRepository.save(account);

        // saving the operation
        AccountOperation accountOperation = new AccountOperation();
        accountOperation.setAccount(account);
        accountOperation.setDescription(description);
        accountOperation.setOperationType(OperationType.DEBIT);
        accountOperation.setDate(LocalDate.now());
        accountOperationRepository.save(accountOperation);
    }

    @Override
    public void credit(long accountId, double amount, String description) throws AccountNotFoundException, BalanceNotSufficientException {
        Account account = findAccount(accountId);

        // update the balance of the account
        account.setBalance(account.getBalance() + amount);
        accountRepository.save(account);

        // saving the operation
        AccountOperation accountOperation = new AccountOperation();
        accountOperation.setAccount(account);
        accountOperation.setDescription(description);
        accountOperation.setOperationType(OperationType.CREDIT);
        accountOperation.setDate(LocalDate.now());
        accountOperationRepository.save(accountOperation);
    }

    @Override
    public void transfer(long accountIdSource, long accountIdDestination, double amount) throws AccountNotFoundException, BalanceNotSufficientException {
        debit(accountIdSource, amount, Constant.TRANSFER);
        credit(accountIdDestination, amount, Constant.TRANSFER);
    }
}
