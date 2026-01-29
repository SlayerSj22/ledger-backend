package com.shashwat.ledger.service;

import com.shashwat.ledger.dto.AddPaymentRequest;
import com.shashwat.ledger.dto.LedgerEntryResponse;
import com.shashwat.ledger.exception.ResourceNotFoundException;
import com.shashwat.ledger.model.Account;
import com.shashwat.ledger.model.LedgerEntry;
import com.shashwat.ledger.repository.AccountRepository;
import com.shashwat.ledger.repository.LedgerEntryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class LedgerService {

    private final LedgerEntryRepository ledgerEntryRepository;
    private final AccountRepository accountRepository;

    public LedgerService(LedgerEntryRepository ledgerEntryRepository,
                         AccountRepository accountRepository) {
        this.ledgerEntryRepository = ledgerEntryRepository;
        this.accountRepository = accountRepository;
    }

    /**
     * Add a ledger entry (payment / adjustment) to an account
     */
    @Transactional
    public LedgerEntry addPayment(AddPaymentRequest request) {

        // 1️⃣ Fetch account
        Account account = accountRepository.findById(request.getAccountId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Account not found with id: " + request.getAccountId()
                        ));

        // 2️⃣ Create ledger entry
        LedgerEntry entry = LedgerEntry.builder()
                .account(account)
                .amount(request.getAmount())
                .type(request.getType())
                .description(request.getDescription())
                .createdDate(LocalDateTime.now())
                .build();

        ledgerEntryRepository.save(entry);

        // 3️⃣ Update account totals
        if ("CREDIT".equalsIgnoreCase(request.getType())) {
            account.setTotalCredit(
                    account.getTotalCredit() + request.getAmount()
            );
        } else if ("DEBIT".equalsIgnoreCase(request.getType())) {
            account.setTotalDebit(
                    account.getTotalDebit() + request.getAmount()
            );
        } else {
            throw new IllegalArgumentException("Invalid ledger entry type");
        }

        // 4️⃣ Recalculate pending amount
        double netPaid = account.getTotalCredit() - account.getTotalDebit();
        double pending = account.getTotalAmount() - netPaid;

        account.setPendingAmount(pending);

        // 5️⃣ Update status
        if (pending <= 0) {
            account.setStatus("CLOSED");
        } else {
            account.setStatus("OPEN");
        }

        // 6️⃣ Save updated account
        accountRepository.save(account);

        return entry;
    }

    public List<LedgerEntry> getLedgerForAccount(Long accountId) {

        // Validate account exists
        if (!accountRepository.existsById(accountId)) {
            throw new ResourceNotFoundException(
                    "Account not found with id: " + accountId
            );
        }

        return ledgerEntryRepository
                .findByAccountIdOrderByCreatedDateAsc(accountId);
    }

    public LedgerEntryResponse mapToLedgerResponse(LedgerEntry entry) {
        return LedgerEntryResponse.builder()
                .id(entry.getId())
                .accountId(entry.getAccount().getId())
                .amount(entry.getAmount())
                .type(entry.getType())
                .description(entry.getDescription())
                .createdDate(entry.getCreatedDate())
                .build();
    }


}
