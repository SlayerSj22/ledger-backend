package com.shashwat.ledger.controller;

import com.shashwat.ledger.dto.AccountCreateRequest;
import com.shashwat.ledger.dto.AccountResponse;
import com.shashwat.ledger.dto.ApiResponse;
import com.shashwat.ledger.model.Account;
import com.shashwat.ledger.service.AccountService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    /**
     * Create a new bill (Account) for a customer
     */
    @PostMapping("/create")
    public ApiResponse<Account> createAccount(
            @RequestBody AccountCreateRequest request) {

        Account account = accountService.createAccount(request);

        return ApiResponse.<Account>builder()
                .data(account)
                .message("Bill created successfully")
                .status(201)
                .build();
    }

    /**
     * Fetch all bills of a customer
     */
    @GetMapping("/party/{partyId}")
    public ApiResponse<List<AccountResponse>> getAccountsByParty(
            @PathVariable Long partyId) {

        List<AccountResponse> responses =
                accountService.getAccountsForParty(partyId);

        return ApiResponse.<List<AccountResponse>>builder()
                .data(responses)
                .message("Bills fetched successfully")
                .status(200)
                .build();
    }


}
