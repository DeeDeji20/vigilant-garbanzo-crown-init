package com.crown.africa.customerservice.data.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@Document
@JsonIgnoreProperties(ignoreUnknown = true)
public class User {
    @Id
    private String id;
    private String firstName;
    private String lastName;
    @NotBlank(message = "Email is mandatory") @Email
    private String email;
    private BillingDetails billingDetails;

}
