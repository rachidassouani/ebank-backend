package io.rachidassouani.ebankbackend.service;

import io.rachidassouani.ebankbackend.dto.AccountOperationDTO;

import java.util.List;

public interface AccountOperationService {

    List<AccountOperationDTO> findAllByAccountId(long accountId);
}
