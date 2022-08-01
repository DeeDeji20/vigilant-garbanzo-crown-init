package com.crown.africa.customerservice;

import com.crown.africa.customerservice.data.models.BillingDetails;
import com.crown.africa.customerservice.data.models.User;
import com.crown.africa.customerservice.data.repository.UserRepository;
import com.crown.africa.customerservice.exception.AuthException;
import com.crown.africa.customerservice.exception.UserException;
import com.crown.africa.customerservice.services.UserService;
import com.crown.africa.customerservice.web.payload.request.UserRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import java.math.BigDecimal;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;



@DataMongoTest
public class UserServiceTest {
    @Autowired
    private UserService userService;

    @Autowired
    UserRepository userRepository;

    UserRequest request;
    @BeforeEach
    void setUp(){

        request = UserRequest
                .builder()
                .id("1")
                .firstName("testFirstname")
                .lastName("testLastname")
                .email("test@gmail.com")
                .billingDetails(new BillingDetails("1234567890-01", BigDecimal.ONE))
                .build();
    }

    @Test
    void testThatUserCanBeCreated(){
        User user = userService.createUser(request);
        assertThat(userService.getAllUsers().size(), is(1));
        assertThat(user.getBillingDetails().getAccountNumber(), is("1234567890-01"));
        assertThat(user.getBillingDetails().getTarrif(), is(BigDecimal.ONE));
    }

    @Test
    @DisplayName("If account number doesnt end with -01 throws exception")
    void testThat_ThrowsException_WhenAccountNumberIsInvalid(){

    }

    @Test
    void testThatUserCannotCreateAccountWithEmailThatAlreadyExist_throwException() {
        userService.createUser(request);
        //given
        UserRequest request2 = UserRequest
                .builder()
                .id("2")
                .firstName("test2")
                .lastName("test2")
                .email("test@gmail.com")
                .billingDetails(new BillingDetails("1234567890-01", BigDecimal.ONE))
                .build();
        //assert
        assertThrows(AuthException.class, () -> userService.createUser(request2));
    }

    @Test
    void testThatUserCanFindUserByEmail() {
        //when
        userService.createUser(request);

        assertThat(userService.getAllUsers().size(), is(1));

        User response = userService.getUser(request.getId());
        assertThat(response.getEmail(), is("test@gmail.com"));
        assertThat(response.getFirstName(),is("testFirstname"));
        assertThat(response.getLastName(),is("testLastname"));

    }

    @Test
    void testThatUserCannotFindUserByEmail_throwException() {
        //when
        userService.createUser(request);
        assertThat(userService.getAllUsers().size(), is(1));
        assertThrows(UserException.class,()-> userService.getUser("2"));
    }

    @AfterEach
    void tearDown(){
        userRepository.deleteAll();
    }

}
