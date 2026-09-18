package com.workintech.s18d4.dto;

import com.workintech.s18d4.entity.Account;

public record AccountResponse(
        long id,
        String accountName,
        Double moneyAmount
) {

    public static AccountResponse from(Account account) {
        if (account == null) {
            return null;
        }

        return new AccountResponse(
                account.getId(),
                account.getAccountName(),
                account.getMoneyAmount()
        );
    }
}