package com.crown.africa.customerservice.data.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BillingDetails {
    private String accountNumber;
    private BigDecimal tarrif;
}
