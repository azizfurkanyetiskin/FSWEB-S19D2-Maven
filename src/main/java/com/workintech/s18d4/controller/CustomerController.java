package com.workintech.s18d4.controller;

import com.workintech.s18d4.dto.CustomerResponse;
import com.workintech.s18d4.entity.Customer;
import com.workintech.s18d4.service.CustomerService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping({"/customer", "/customers"})
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public List<CustomerResponse> findAll() {
        return customerService.findAll()
                .stream()
                .map(CustomerResponse::from)
                .toList();
    }

    @GetMapping("/{id}")
    public CustomerResponse find(@PathVariable Long id) {
        return CustomerResponse.from(
                customerService.find(id)
        );
    }

    @PostMapping
    public CustomerResponse save(
            @RequestBody Customer customer
    ) {
        return CustomerResponse.from(
                customerService.save(customer)
        );
    }

    @PutMapping("/{id}")
    public CustomerResponse update(
            @PathVariable Long id,
            @RequestBody Customer customer
    ) {
        customer.setId(id);

        return CustomerResponse.from(
                customerService.save(customer)
        );
    }

    @DeleteMapping("/{id}")
    public CustomerResponse delete(
            @PathVariable Long id
    ) {
        return CustomerResponse.from(
                customerService.delete(id)
        );
    }
}