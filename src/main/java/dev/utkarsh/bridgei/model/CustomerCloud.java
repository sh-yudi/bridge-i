package dev.utkarsh.bridgei.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity // Tells Spring Data JPA to create a table for this
@Table(name = "cloud_customers")
public class CustomerCloud {

    @Id
    private Integer id;

    private String fullName;
    private String accountStatus;
    private LocalDate joinedDate; // Upgraded from Integer to a proper LocalDate
}