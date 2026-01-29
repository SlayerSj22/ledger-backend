package com.shashwat.ledger.repository;
import com.shashwat.ledger.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountRepository extends JpaRepository<Account, Long> {
    List<Account> findByPartyId(Long partyId);
}

