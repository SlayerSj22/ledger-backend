package com.shashwat.ledger.service;

import com.shashwat.ledger.dto.PartyRegistrationRequest;
import com.shashwat.ledger.exception.DuplicatePartyException;
import com.shashwat.ledger.model.Party;
import com.shashwat.ledger.repository.PartyRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PartyService {
    private final PartyRepository partyRepository;
    public PartyService(PartyRepository partyRepository){
        this.partyRepository=partyRepository;
    }

    public Party createParty(PartyRegistrationRequest request) {

        boolean exists = partyRepository
                .findByNameAndFatherNameAndVillage(
                        request.getName(),
                        request.getFatherName(),
                        request.getVillage()
                )
                .isPresent();

        if (exists) {
            throw new DuplicatePartyException(
                    "Customer already exists with same name, father name and village"
            );
        }

        Party party = Party.builder()
                .name(request.getName())
                .fatherName(request.getFatherName())
                .village(request.getVillage())
                .phone(request.getPhone())
                .createdDate(LocalDateTime.now())
                .build();

        return partyRepository.save(party);
    }


}
