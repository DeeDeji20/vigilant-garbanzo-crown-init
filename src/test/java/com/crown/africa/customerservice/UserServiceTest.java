package com.crown.africa.customerservice;

import com.crown.africa.customerservice.data.models.BillingDetails;
import com.crown.africa.customerservice.data.models.User;
import com.crown.africa.customerservice.data.repository.UserRepository;
import com.crown.africa.customerservice.exception.AuthException;
import com.crown.africa.customerservice.exception.InvalidAccountNumberException;
import com.crown.africa.customerservice.exception.InvalidEmailException;
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
//                .billingDetails(new BillingDetails("1234567890-01", BigDecimal.ONE))
                .build();
    }

    @Test
    void testThatAccountNumberCannotBeLessThan10(){
        UserRequest request2 = UserRequest
                .builder()
                .id("2")
                .firstName("test2")
                .lastName("test2")
                .email("test@gmail.com")
//                .billingDetails(new BillingDetails("12345678-01", BigDecimal.ONE))
                .build();
        //assert
        Throwable exception = assertThrows(InvalidAccountNumberException.class,  ()-> userService.createUser(request2));
        assertThat(exception.getMessage(), is("Account number must be 10 digits"));
    }

    @Test
    void testThatAccountNumberCannotGreaterThan10(){
        UserRequest request2 = UserRequest
                .builder()
                .id("2")
                .firstName("test2")
                .lastName("test2")
                .email("test@gmail.com")
//                .billingDetails(new BillingDetails("1234567891012-01", BigDecimal.ONE))
                .build();
        //assert
        Throwable exception = assertThrows(InvalidAccountNumberException.class,  ()-> userService.createUser(request2));
        assertThat(exception.getMessage(), is("Account number must be 10 digits"));
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
        BillingDetails billingDetails = new BillingDetails();
        UserRequest
                .builder()
                .id("2")
                .firstName("test2")
                .lastName("test2")
                .email("test@gmail.com")
                .build();
        //assert
        Throwable exception = assertThrows(InvalidAccountNumberException.class, () -> billingDetails.setAccountNumber("1234567890-02"));
        assertThat(exception.getMessage(), is("Account number does not end with -01"));
    }


    @Test
    void testThatWhenAccountNumberEndsWithvalueThatIsNot01_ThrowsException1(){
        UserRequest request2 = UserRequest
                .builder()
                .id("2")
                .firstName("test2")
                .lastName("test2")
                .email("test@gmail.com")
//                .billingDetails(new BillingDetails("1234567890-02", BigDecimal.valueOf(1.5)))
                .build();
        Throwable exception = assertThrows(InvalidAccountNumberException.class, () -> userService.createUser(request2));
        assertThat(exception.getMessage(), is("Account number must be 10 digits"));
    }
    @Test
    void testThatWhenAccountNumberDoesNotEndWith01_ThrowsException2(){
        UserRequest request2 = UserRequest
                .builder()
                .id("2")
                .firstName("test2")
                .lastName("test2")
                .email("test@gmail.com")
//                .billingDetails(new BillingDetails("1234567890", BigDecimal.ONE))
                .build();
        Throwable exception = assertThrows(InvalidAccountNumberException.class, () -> userService.createUser(request2));
        assertThat(exception.getMessage(), is("Account number does not end with -01"));
    }

    @Test
    void testThatWhenEmailIsInvalid_ThrowsException(){
        UserRequest request2 = UserRequest
                .builder()
                .id("2")
                .firstName("test2")
                .lastName("test2")
                .email("test")
//                .billingDetails(new BillingDetails("1234567890-01", BigDecimal.ONE))
                .build();
        Throwable exception = assertThrows(InvalidEmailException.class, () -> userService.createUser(request2));
        assertThat(exception.getMessage(), is("Enter a valid email"));
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
//                .billingDetails(new BillingDetails("1234567890-01", BigDecimal.ONE))
                .build();
        //assert
        Throwable exception = assertThrows(AuthException.class, () -> userService.createUser(request2));
        assertThat(exception.getMessage(), is("Email already exists"));
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
    void testThatGEtUserThatDoesNotExist_throwsException() {
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
