package com.crown.africa.customerservice.data.models;

import com.crown.africa.customerservice.exception.InvalidAccountNumberException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@Data
public class BillingDetails {
    private String accountNumber;
    private BigDecimal tarrif;

    public BillingDetails(String accountNumber, BigDecimal tarrif) {
        String[] splitString = accountNumber.split("-");
        if (!splitString[1].equals("01")) throw new InvalidAccountNumberException("Invalid account number");
        this.accountNumber = accountNumber;
        this.tarrif = tarrif;
    }

    public void setAccountNumber(String accountNumber) {
        String[] splitString = accountNumber.split("-");
        if (!splitString[1].equals("01")) throw new InvalidAccountNumberException("Invalid account number");
        this.accountNumber = accountNumber;
    }
}
