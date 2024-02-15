package com.example.enterdata.controller;


import com.example.enterdata.user.User;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.StreamingHttpOutputMessage;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.*;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.*;

@Controller
public class LoginController {


    @Autowired
    private JdbcTemplate template;

    @GetMapping("/")
    public String showHome(){
        return "home";
    }

    @GetMapping("/login")
    public String showLogin(Model model){
        model.addAttribute("user" , new User());
        return "login";
    }

    @PostMapping("/submit")
    public String handleForm(@ModelAttribute User user , Model model) throws IOException, URISyntaxException, InterruptedException {
        String name = user.getName();
        String password = user.getPassword();
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request =HttpRequest.newBuilder()
                .uri(new URI("http://authentication:8080/authenticate"))
                .POST(HttpRequest.BodyPublishers.ofString(name+","+password))
                .build();

        HttpResponse<String> response=client.send(request ,BodyHandlers.ofString());
        if (!response.body().equals("valid")){
            model.addAttribute("message" , response.body());
            return "login";
        }

        return "forward:/show_data_form";
    }




}
