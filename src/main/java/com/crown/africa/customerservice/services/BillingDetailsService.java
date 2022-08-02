package com.crown.africa.customerservice.services;

import com.crown.africa.customerservice.data.models.BillingDetails;

import java.util.List;


public interface BillingDetailsService {
    BillingDetails createAccount();
    List<BillingDetails> getAllBillings();
}
