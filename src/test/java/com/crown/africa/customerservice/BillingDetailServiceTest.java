package com.crown.africa.customerservice;

import com.crown.africa.customerservice.data.models.BillingDetails;
import com.crown.africa.customerservice.data.repository.BillingDetailsRepository;
import com.crown.africa.customerservice.services.BillingDetailsService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;


import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataMongoTest
public class BillingDetailServiceTest {

    @Autowired
    BillingDetailsRepository billingDetailsRepository;

    @Autowired
    BillingDetailsService billingDetailsService;

    private BillingDetails billingDetails;
    @BeforeEach
    void setUp(){
        billingDetails = billingDetailsService.createAccount();
    }

    @Test
    void testThatAllBillingsCanBBeRetrieved(){
        List<BillingDetails> allBilling = billingDetailsService.getAllBillings();
        assertThat(allBilling.size(), is(1));
    }

    @Test
    void testThatAccountCanBeCreated(){
        assertThat(billingDetailsService.getAllBillings().size(), is(1));
    }

    @Test
    void testAccountNumbercannotBeLessThan10Digit(){
        assertThat(billingDetails.getAccountNumber().split("-01")[0].length(), is(10));
    }

    @AfterEach
    void tearDown(){
        billingDetailsRepository.deleteAll();
    }

}
