package com.crown.africa.customerservice.web.payload.request;

import com.crown.africa.customerservice.data.models.BillingDetails;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private BillingDetails billingDetails;
}
