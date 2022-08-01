package com.crown.africa.customerservice.web.controllers;

import com.crown.africa.customerservice.data.models.User;
import com.crown.africa.customerservice.exception.AuthException;
import com.crown.africa.customerservice.exception.UserException;
import com.crown.africa.customerservice.services.UserService;
import com.crown.africa.customerservice.web.payload.request.UserRequest;
import com.crown.africa.customerservice.web.payload.response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/save")
    public ResponseEntity<?> createUser(@RequestBody UserRequest userRequest){
        try{
            ApiResponse response = ApiResponse
                    .builder()
                    .isSuccessful(true)
                    .message(""+ userService.createUser(userRequest))
                    .build();

            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (AuthException ex){
            ApiResponse response = ApiResponse
                    .builder()
                    .isSuccessful(false)
                    .message(ex.getMessage())
                    .build();

            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getUser/{id}")
    public ResponseEntity<?> getUser(@PathVariable String id){
        try{

            ApiResponse response = ApiResponse
                    .builder()
                    .isSuccessful(true)
                    .message("" + userService.getUser(id))
                    .build();

            return  new ResponseEntity<>(response, HttpStatus.OK);
        }catch(UserException ex){
            ApiResponse response = ApiResponse
                    .builder()
                    .isSuccessful(false)
                    .message(ex.getMessage())
                    .build();
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getAllUsers")
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }
}
