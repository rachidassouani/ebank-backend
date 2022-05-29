package io.rachidassouani.ebankbackend.dao;

import io.rachidassouani.ebankbackend.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
}
