package com.crown.africa.customerservice.data.models;

import com.crown.africa.customerservice.exception.InvalidAccountNumberException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
@AllArgsConstructor
@NoArgsConstructor
@Data
@Document
public class BillingDetails {
    @Id
    private String id;
    private String accountNumber;
    private BigDecimal tarrif;

    public void setAccountNumber(String accountNumber) {
        String[] splitString = accountNumber.split("-");
        if (!splitString[1].equals("01")) throw new InvalidAccountNumberException("Account number does not end with -01");
        this.accountNumber = accountNumber;
    }
}
