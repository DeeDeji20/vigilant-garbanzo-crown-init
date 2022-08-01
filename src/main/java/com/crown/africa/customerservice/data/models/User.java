package com.crown.africa.customerservice.data.models;

import lombok.Data;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class User {
    @Id
    private String id;
    private String firstName;
    private String lastName;
    @NotBlank(message = "Email is mandatory") @Email
    private String email;
    private BillingDetails billingDetails;
}
