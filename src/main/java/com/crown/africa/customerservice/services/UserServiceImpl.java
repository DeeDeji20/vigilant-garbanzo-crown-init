package com.crown.africa.customerservice.services;

import com.crown.africa.customerservice.data.models.User;
import com.crown.africa.customerservice.data.repository.UserRepository;
import com.crown.africa.customerservice.exception.AuthException;
import com.crown.africa.customerservice.exception.UserException;
import com.crown.africa.customerservice.web.payload.request.UserRequest;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public User createUser(UserRequest userRequest){
        if(validateEmail(userRequest.getEmail())) throw new AuthException("Email already exists");

        User user = modelMapper.map(userRequest, User.class);
        userRepository.save(user);
        return modelMapper.map(user, User.class);
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
