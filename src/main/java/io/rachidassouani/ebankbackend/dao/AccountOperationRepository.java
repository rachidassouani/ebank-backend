package io.rachidassouani.ebankbackend.dao;

import io.rachidassouani.ebankbackend.model.AccountOperation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountOperationRepository extends JpaRepository<AccountOperation, Long> {
}
