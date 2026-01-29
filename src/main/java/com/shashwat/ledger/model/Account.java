package com.shashwat.ledger.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "account")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Many accounts (bills) belong to one customer (party)
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "party_id", nullable = false)
    private Party party;

    /**
     * Total bill / obligation amount
     */
    @Column(name = "total_amount", nullable = false)
    private Double totalAmount;

    /**
     * Summary fields (ledger-driven later)
     */
    @Column(name = "total_credit", nullable = false)
    private Double totalCredit;

    @Column(name = "total_debit", nullable = false)
    private Double totalDebit;

    @Column(name = "pending_amount", nullable = false)
    private Double pendingAmount;

    /**
     * OPEN / CLOSED
     */
    @Column(nullable = false)
    private String status;

    /**
     * Description of the bill
     * e.g. "Gold necklace purchase"
     */
    private String description;

    @Column(name = "created_date")
    private LocalDateTime createdDate;
}

