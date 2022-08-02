package com.crown.africa.customerservice.services;

import com.crown.africa.customerservice.data.models.BillingDetails;
import com.crown.africa.customerservice.data.models.User;
import com.crown.africa.customerservice.data.repository.UserRepository;
import com.crown.africa.customerservice.exception.AuthException;
import com.crown.africa.customerservice.exception.InvalidAccountNumberException;
import com.crown.africa.customerservice.exception.InvalidEmailException;
import com.crown.africa.customerservice.exception.UserException;
import com.crown.africa.customerservice.web.payload.request.UserRequest;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.security.SecureRandom;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Slf4j
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public User createUser(UserRequest userRequest){
        User user = new User();
        BillingDetails billingDetails = new BillingDetails(generateAccountDetails(), BigDecimal.ONE);
        user.setBillingDetails(billingDetails);
        System.out.println(user.getBillingDetails().getAccountNumber() + "=========>>>>>>");
        validateuserDetails(userRequest);
        user = modelMapper.map(userRequest, User.class);
        userRepository.save(user);
        return modelMapper.map(user, User.class);
    }

    private String generateAccountDetails() {
       double value= Math.floor(100000 + Math.random() * 900000);
       String stringValue = String.valueOf(value);
       return stringValue.concat("-01");
    }

    private void validateuserDetails(UserRequest userRequest) {
        if (!validateUserEmail(userRequest).matches()) throw new InvalidEmailException("Enter a valid email");
        if  (validateEmail(userRequest.getEmail())) throw new AuthException("Email already exists");
//        if (userRequest.getBillingDetails().getAccountNumber().split("-01")[0].length() != 10) throw new InvalidAccountNumberException("Account number must be 10 digits");
//        if (!userRequest.getBillingDetails().getAccountNumber().endsWith("-01")) throw new InvalidAccountNumberException("Account number does not end with -01");
    }

    private Matcher validateUserEmail(UserRequest request) {
        String regex = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(request.getEmail());
    }

//    private boolean isValidAccountnumber(UserRequest userRequest) {
//        return userRequest.getBillingDetails().getAccountNumber().split("-")[1].equals("01");
//    }

    private boolean validateEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        return user.isPresent();
    }

    @Override
    public User getUser(String id) {
        return userRepository.findById(id).orElseThrow(()-> new UserException("User does not exist"));
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
