package com.shashwat.ledger.service;

import com.shashwat.ledger.dto.AccountCreateRequest;
import com.shashwat.ledger.dto.AccountResponse;
import com.shashwat.ledger.exception.ResourceNotFoundException;
import com.shashwat.ledger.model.Account;
import com.shashwat.ledger.model.Party;
import com.shashwat.ledger.repository.AccountRepository;
import com.shashwat.ledger.repository.PartyRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final PartyRepository partyRepository;

    public AccountService(AccountRepository accountRepository,
                          PartyRepository partyRepository) {
        this.accountRepository = accountRepository;
        this.partyRepository = partyRepository;
    }

    /**
     * Create a bill (Account) for an existing customer (Party)
     */
    public Account createAccount(AccountCreateRequest request) {

        // 1️⃣ Fetch customer (Party)
        Party party = partyRepository.findById(request.getPartyId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Customer not found with id: "
                                + request.getPartyId()));

        // 2️⃣ Create Account (Bill)
        Account account = Account.builder()
                .party(party)
                .totalAmount(request.getTotalAmount())
                .totalCredit(0.0)
                .totalDebit(0.0)
                .pendingAmount(request.getTotalAmount())
                .status("OPEN")
                .description(request.getDescription())
                .createdDate(LocalDateTime.now())
                .build();

        // 3️⃣ Save & return
        return accountRepository.save(account);
    }

    public List<AccountResponse> getAccountsForParty(Long partyId) {

        // Optional: validate party exists (recommended)

        if (!partyRepository.existsById(partyId)) {
            throw new ResourceNotFoundException(
                    "Customer not found with id: " + partyId
            );
        }

        List<Account> list= accountRepository.findByPartyId(partyId);
        List<AccountResponse> list1=new ArrayList<>();
        for(Account a:list){
            list1.add(mapToAccountResponse(a));
        }
        return list1;
    }

    public AccountResponse mapToAccountResponse(Account account) {
        return AccountResponse.builder()
                .id(account.getId())
                .totalAmount(account.getTotalAmount())
                .totalCredit(account.getTotalCredit())
                .totalDebit(account.getTotalDebit())
                .pendingAmount(account.getPendingAmount())
                .status(account.getStatus())
                .description(account.getDescription())
                .createdDate(account.getCreatedDate())
                .build();
    }


}
