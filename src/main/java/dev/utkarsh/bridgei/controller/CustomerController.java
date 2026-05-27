package dev.utkarsh.bridgei.controller;

import dev.utkarsh.bridgei.model.CustomerCloud;
import dev.utkarsh.bridgei.repository.CustomerRepository;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

@RestController // Tells Spring this class serves JSON responses over the web
@RequestMapping("/api/customers") // The base URL for this controller
public class CustomerController {

    private final CustomerRepository customerRepository;

    // Spring automatically injects your PostgreSQL repository here
    public CustomerController(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    // 1. READ: Sends data to the web page
    @GetMapping
    public List<CustomerCloud> getAllCustomers() {
        // Fetches all the cleaned AS/400 data from PostgreSQL
        return customerRepository.findAll();
    }

    // 2. CREATE: Receives data, checks for duplicates, and saves to Postgres
    @PostMapping
    public ResponseEntity<?> addCustomer(@RequestBody CustomerCloud newCustomer) {
        // Check if the ID already exists in the database
        if (customerRepository.existsById(newCustomer.getId())) {
            // Return a 409 Conflict error with a custom message
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body("Error: Customer with ID " + newCustomer.getId() + " already exists. Please enter a different ID.");
        }

        // If it does not exist, save it normally and return a 201 Created status
        CustomerCloud savedCustomer = customerRepository.save(newCustomer);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCustomer);
    }
}