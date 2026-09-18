package com.workintech.s18d4.dto;

import com.workintech.s18d4.entity.Customer;

public record CustomerResponse(
        long id,
        String email,
        Double salary
) {

    public static CustomerResponse from(Customer customer) {
        if (customer == null) {
            return null;
        }

        return new CustomerResponse(
                customer.getId(),
                customer.getEmail(),
                customer.getSalary()
        );
    }
}