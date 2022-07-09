package io.rachidassouani.ebankbackend.dto;

import java.util.List;

public class AccountHistoryDTO {
    private long accountId;
    private double balance;
    private List<AccountOperationDTO> accountOperationDTOS;


    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public List<AccountOperationDTO> getAccountOperationDTOS() {
        return accountOperationDTOS;
    }

    public void setAccountOperationDTOS(List<AccountOperationDTO> accountOperationDTOS) {
        this.accountOperationDTOS = accountOperationDTOS;
    }
}
