package com.example.auth.controller;

import com.example.auth.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {

    @Autowired
    private AuthenticationService service ;


    @PostMapping("/authenticate")
    public String handlingData(@RequestBody String userFields){
        String username = userFields.split(",")[0];
        String password = userFields.split(",")[1];
        if (service.isUser(username,password))
            return "valid";
        else return "invalid";
    }

}