package dev.utkarsh.bridgei.repository;

import dev.utkarsh.bridgei.model.CustomerCloud;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerCloud, Integer> {
    // Spring Data JPA handles all the saving/updating logic automatically!
}