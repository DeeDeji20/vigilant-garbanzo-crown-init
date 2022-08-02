package com.crown.africa.customerservice.services;

import com.crown.africa.customerservice.data.models.BillingDetails;
import com.crown.africa.customerservice.data.repository.BillingDetailsRepository;
import com.crown.africa.customerservice.web.payload.request.AccountRequest;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class BillingDetailsServiceImpl implements  BillingDetailsService{
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private BillingDetailsRepository billingDetailsRepository;

    @Override
    public BillingDetails createAccount() {
        AccountRequest request = new AccountRequest();
        request.setAccountNumber(generateAccountDetails());
        request.setTarrif(BigDecimal.ONE);
        return billingDetailsRepository.save(modelMapper.map(request, BillingDetails.class));
    }

    @Override
    public List<BillingDetails> getAllBillings() {
        return billingDetailsRepository.findAll();
    }

    private String generateAccountDetails() {
        Long number = (long) Math.floor(Math.random() * 9_000_000_000L) + 1_000_000_000L;
        String stringValue = String.valueOf(number);
        return stringValue.concat("-01");
    }

}
