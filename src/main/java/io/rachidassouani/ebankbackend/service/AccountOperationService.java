package io.rachidassouani.ebankbackend.service;

import io.rachidassouani.ebankbackend.dto.AccountHistoryDTO;
import io.rachidassouani.ebankbackend.dto.AccountOperationDTO;
import io.rachidassouani.ebankbackend.exception.AccountNotFoundException;

import java.util.List;

public interface AccountOperationService {

    List<AccountOperationDTO> findAllByAccountId(long accountId);
    AccountHistoryDTO findAccountHistoryByAccountId(long accountId) throws AccountNotFoundException;
}
