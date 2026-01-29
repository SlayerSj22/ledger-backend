package com.shashwat.ledger.repository;

import com.shashwat.ledger.model.Party;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PartyRepository extends JpaRepository<Party,Long> {
    Optional<Party> findByNameAndFatherNameAndVillage(
            String name,
            String fatherName,
            String village
    );

}
