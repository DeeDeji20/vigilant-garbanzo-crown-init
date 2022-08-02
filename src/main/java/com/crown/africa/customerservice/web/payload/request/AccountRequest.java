package com.crown.africa.customerservice.web.payload.request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class AccountRequest {
    private String accountNumber;
    private BigDecimal tarrif;
}
