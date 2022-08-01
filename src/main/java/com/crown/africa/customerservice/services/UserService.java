package com.crown.africa.customerservice.services;

import com.crown.africa.customerservice.data.models.User;
import com.crown.africa.customerservice.exception.AuthException;
import com.crown.africa.customerservice.web.payload.request.UserRequest;

import java.util.List;

public interface UserService {
    User createUser(UserRequest userRequest) throws AuthException;
    User getUser(String id);
    List<User> getAllUsers();
;}
