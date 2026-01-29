package com.shashwat.ledger.controller;

import com.shashwat.ledger.dto.ApiResponse;
import com.shashwat.ledger.dto.PartyRegistrationRequest;
import com.shashwat.ledger.model.Party;
import com.shashwat.ledger.service.PartyService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/party")
public class PartyController {

    private final PartyService partyService;

    public PartyController(PartyService partyService) {
        this.partyService = partyService;
    }

    @PostMapping("/register")
    public ApiResponse<Party> registerUser(
            @RequestBody PartyRegistrationRequest request) {

        Party savedParty = partyService.createParty(request);

        return ApiResponse.<Party>builder()
                .data(savedParty)
                .message("Customer registered successfully")
                .status(201)
                .build();
    }
}

