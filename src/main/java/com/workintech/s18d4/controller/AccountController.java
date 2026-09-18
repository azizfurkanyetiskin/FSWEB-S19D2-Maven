package com.workintech.s18d4.controller;

import com.workintech.s18d4.dto.AccountResponse;
import com.workintech.s18d4.entity.Account;
import com.workintech.s18d4.entity.Customer;
import com.workintech.s18d4.service.AccountService;
import com.workintech.s18d4.service.CustomerService;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping({"/account", "/accounts"})
public class AccountController {

    private final AccountService accountService;
    private final CustomerService customerService;

    public AccountController(
            AccountService accountService,
            CustomerService customerService
    ) {
        this.accountService = accountService;
        this.customerService = customerService;
    }

    @GetMapping
    public List<AccountResponse> findAll() {
        return accountService.findAll()
                .stream()
                .map(AccountResponse::from)
                .toList();
    }

    @GetMapping("/{id}")
    public AccountResponse find(@PathVariable long id) {
        return AccountResponse.from(
                accountService.find(id)
        );
    }

    @PostMapping("/{customerId}")
    public AccountResponse save(
            @PathVariable long customerId,
            @RequestBody Account account
    ) {
        Customer customer = customerService.find(customerId);

        if (customer != null) {
            account.setCustomer(customer);

            List<Account> accounts =
                    customer.getAccounts() == null
                            ? new ArrayList<>()
                            : new ArrayList<>(customer.getAccounts());

            boolean alreadyExists = accounts.stream()
                    .anyMatch(existing ->
                            existing == account ||
                            (
                                    existing.getId() > 0 &&
                                    account.getId() > 0 &&
                                    existing.getId() == account.getId()
                            )
                    );

            if (!alreadyExists) {
                accounts.add(account);
            }

            customer.setAccounts(accounts);
        }

        return AccountResponse.from(
                accountService.save(account)
        );
    }

    @PutMapping("/{customerId}")
    public AccountResponse update(
            @PathVariable long customerId,
            @RequestBody Account account
    ) {
        Customer customer = customerService.find(customerId);

        if (account.getId() > 0) {
            accountService.find(account.getId());
        }

        if (customer != null) {
            account.setCustomer(customer);
        }

        return AccountResponse.from(
                accountService.save(account)
        );
    }

    @DeleteMapping("/{id}")
    public AccountResponse delete(@PathVariable long id) {
        accountService.find(id);

        return AccountResponse.from(
                accountService.delete(id)
        );
    }
}