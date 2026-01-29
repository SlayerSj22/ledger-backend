package com.shashwat.ledger.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "ledger_entry")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LedgerEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Each entry belongs to one Account (Bill)
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    /**
     * Amount moved
     */
    @Column(nullable = false)
    private Double amount;

    /**
     * CREDIT or DEBIT
     */
    @Column(nullable = false)
    private String type;

    /**
     * Optional description
     */
    private String description;

    @Column(name = "created_date")
    private LocalDateTime createdDate;
}

