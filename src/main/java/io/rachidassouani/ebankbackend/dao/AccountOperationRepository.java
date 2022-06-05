package io.rachidassouani.ebankbackend.dao;

import io.rachidassouani.ebankbackend.model.AccountOperation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountOperationRepository extends JpaRepository<AccountOperation, Long> {

    List<AccountOperation> findAccountOperationByAccountId(long accountId);
}
