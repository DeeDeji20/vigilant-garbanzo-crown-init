package com.crown.africa.customerservice.data.repository;

import com.crown.africa.customerservice.data.models.BillingDetails;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BillingDetailsRepository extends MongoRepository<BillingDetails, String> {
}
