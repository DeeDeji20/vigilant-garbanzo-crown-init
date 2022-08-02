package com.crown.africa.customerservice.services;

import com.crown.africa.customerservice.data.models.BillingDetails;
import com.crown.africa.customerservice.data.models.User;
import com.crown.africa.customerservice.data.repository.UserRepository;
import com.crown.africa.customerservice.exception.AuthException;
import com.crown.africa.customerservice.exception.InvalidEmailException;
import com.crown.africa.customerservice.exception.UserException;
import com.crown.africa.customerservice.web.payload.request.UserRequest;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Autowired
    private BillingDetailsService billingDetailsService;

    @Override
    public User createUser(UserRequest userRequest){
        validateUserDetails(userRequest);
        BillingDetails billingDetails = billingDetailsService.createAccount();
        User user = modelMapper.map(userRequest, User.class);
        user.setBillingId(billingDetails.getId());
        return userRepository.save(user);
    }



    private void validateUserDetails(UserRequest userRequest) {
        if (!validateUserEmail(userRequest).matches()) throw new InvalidEmailException("Enter a valid email");
        if  (validateEmail(userRequest.getEmail())) throw new AuthException("Email already exists");
   }

    private Matcher validateUserEmail(UserRequest request) {
        String regex = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(request.getEmail());
    }

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
