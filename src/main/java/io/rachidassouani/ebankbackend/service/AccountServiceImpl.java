package io.rachidassouani.ebankbackend.service;

import io.rachidassouani.ebankbackend.dao.AccountOperationRepository;
import io.rachidassouani.ebankbackend.dao.AccountRepository;
import io.rachidassouani.ebankbackend.dao.CustomerRepository;
import io.rachidassouani.ebankbackend.dto.AccountDTO;
import io.rachidassouani.ebankbackend.dto.CurrentAccountDTO;
import io.rachidassouani.ebankbackend.dto.CustomerDTO;
import io.rachidassouani.ebankbackend.dto.SavingAccountDTO;
import io.rachidassouani.ebankbackend.enums.AccountStatus;
import io.rachidassouani.ebankbackend.enums.OperationType;
import io.rachidassouani.ebankbackend.exception.AccountNotFoundException;
import io.rachidassouani.ebankbackend.exception.BalanceNotSufficientException;
import io.rachidassouani.ebankbackend.exception.CustomerNotFoundException;
import io.rachidassouani.ebankbackend.mapper.CurrentAccountMapperImpl;
import io.rachidassouani.ebankbackend.mapper.CustomerMapperImpl;
import io.rachidassouani.ebankbackend.mapper.SavingAccountMapperImpl;
import io.rachidassouani.ebankbackend.model.*;
import io.rachidassouani.ebankbackend.util.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class AccountServiceImpl implements AccountService {
    private final static Logger LOGGER = LoggerFactory.getLogger(AccountServiceImpl.class);

    private final AccountRepository accountRepository;
    private final CustomerRepository customerRepository;
    private final AccountOperationRepository accountOperationRepository;
    private final CurrentAccountMapperImpl currentAccountMapper;
    private final SavingAccountMapperImpl savingAccountMapper;
    private final CustomerMapperImpl customerMapper;


    public AccountServiceImpl(AccountRepository accountRepository, CustomerRepository customerRepository, AccountOperationRepository accountOperationRepository, CurrentAccountMapperImpl currentAccountMapper, SavingAccountMapperImpl savingAccountMapper, CustomerMapperImpl customerMapper) {
        this.accountRepository = accountRepository;
        this.customerRepository = customerRepository;
        this.accountOperationRepository = accountOperationRepository;
        this.currentAccountMapper = currentAccountMapper;
        this.savingAccountMapper = savingAccountMapper;
        this.customerMapper = customerMapper;
    }

    @Override
    public Account findAccount(long accountId) throws AccountNotFoundException {
        LOGGER.info("finding account by its ID .."+ accountId);
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new AccountNotFoundException(Constant.ACCOUNT_ID_NOT_EXIST));

        return account;
    }

    @Override
    public AccountDTO findAccountDTO(long accountId) throws AccountNotFoundException {
        LOGGER.info("finding accountDTO by its ID ..");
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new AccountNotFoundException(Constant.ACCOUNT_ID_NOT_EXIST));

        if (account instanceof CurrentAccount) {
            CurrentAccount currentAccount = (CurrentAccount) account;
            CurrentAccountDTO currentAccountDTO = currentAccountMapper.fromCurrentAccount(currentAccount);

            return currentAccountDTO;

        } else {
            SavingAccount savingAccount = (SavingAccount) account;
            SavingAccountDTO savingAccountDTO = savingAccountMapper.fromSavingAccount(savingAccount);

            return savingAccountDTO;
        }
    }

    @Override
    public List<AccountDTO> findAll() {
        List<Account> accounts = accountRepository.findAll();

        List<AccountDTO> accountsDTO = accounts.stream().map(account -> {
            if (account instanceof CurrentAccount) {
                CurrentAccount currentAccount = (CurrentAccount) account;
                return currentAccountMapper.fromCurrentAccount(currentAccount);
            } else {
                SavingAccount savingAccount = (SavingAccount) account;
                return savingAccountMapper.fromSavingAccount(savingAccount);
            }
        }).collect(Collectors.toList());

        return accountsDTO;
    }

    @Override
    public CurrentAccountDTO saveCurrentAccount(long customerId, double initBalance, double overDraft) throws CustomerNotFoundException {
        LOGGER.info("Saving new current account ..");

        // finding the customer by its ID
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException(Constant.CUSTOMER_ID_NOT_EXIST));

        // casting it to CustomerDTO
        CustomerDTO customerDTO = customerMapper.fromCustomer(customer);

        CurrentAccount currentAccount = new CurrentAccount();
        currentAccount.setBalance(initBalance);
        currentAccount.setCustomer(customer);
        currentAccount.setAccountStatus(AccountStatus.CREATED);
        currentAccount.setOverDraft(overDraft);
        currentAccount.setCreatedAt(LocalDate.now());

        CurrentAccount savedAccount = accountRepository.save(currentAccount);
        return currentAccountMapper.fromCurrentAccount(savedAccount);
    }

    @Override
    public SavingAccountDTO saveSavingAccount(long customerId, double initBalance, double interestRate) throws CustomerNotFoundException {
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
        SavingAccountDTO savingAccountDTO = savingAccountMapper.fromSavingAccount(savedAccount);
        return savingAccountDTO;
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
        accountOperation.setAmount(amount);
        accountOperation.setDate(LocalDate.now());
        accountOperationRepository.save(accountOperation);
    }

    @Override
    public void credit(long accountId, double amount, String description) throws AccountNotFoundException {
        Account account = findAccount(accountId);

        // update the balance of the account
        account.setBalance(account.getBalance() + amount);
        accountRepository.save(account);

        // saving the operation
        AccountOperation accountOperation = new AccountOperation();
        accountOperation.setAccount(account);
        accountOperation.setDescription(description);
        accountOperation.setOperationType(OperationType.CREDIT);
        accountOperation.setAmount(amount);
        accountOperation.setDate(LocalDate.now());
        accountOperationRepository.save(accountOperation);
    }

    @Override
    public void transfer(long accountIdSource, long accountIdDestination, double amount, String description) throws AccountNotFoundException, BalanceNotSufficientException {
        debit(accountIdSource, amount, description);
        credit(accountIdDestination, amount, description);
    }
}
