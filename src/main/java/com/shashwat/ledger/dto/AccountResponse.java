package com.shashwat.ledger.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountResponse {

    private Long id;
    private Double totalAmount;
    private Double totalCredit;
    private Double totalDebit;
    private Double pendingAmount;
    private String status;
    private String description;
    private LocalDateTime createdDate;
}
