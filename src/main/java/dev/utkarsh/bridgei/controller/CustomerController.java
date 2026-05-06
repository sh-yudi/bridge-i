package dev.utkarsh.bridgei.controller;

import dev.utkarsh.bridgei.model.CustomerCloud;
import dev.utkarsh.bridgei.repository.CustomerRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController // Tells Spring this class serves JSON responses over the web
@RequestMapping("/api/customers") // The base URL for this controller
public class CustomerController {

    private final CustomerRepository customerRepository;

    // Spring automatically injects your PostgreSQL repository here
    public CustomerController(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    // When someone visits /api/customers, this method fires
    @GetMapping
    public List<CustomerCloud> getAllCustomers() {
        // Fetches all the cleaned AS/400 data from PostgreSQL
        return customerRepository.findAll();
    }
}