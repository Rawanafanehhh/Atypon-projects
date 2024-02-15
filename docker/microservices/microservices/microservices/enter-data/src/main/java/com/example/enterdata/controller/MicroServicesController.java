package com.example.enterdata.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Controller
public class MicroServicesController {

    @Autowired
    private JdbcTemplate template;

    @PostMapping("/microservice")
    public String useOtherServices(Model model) throws URISyntaxException, IOException, InterruptedException {


        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request =HttpRequest.newBuilder()
                .uri(new URI("http://analytics:8080/analytics"))
                .POST(HttpRequest.BodyPublishers.ofString("doAnalytics"))
                .build();
        HttpResponse<String> response=client.send(request , HttpResponse.BodyHandlers.ofString());


        if (response.body().equals("incomplete")){
            model.addAttribute("message" , "incomplete");
            return "result";
        }

        HttpClient client1 = HttpClient.newHttpClient();
        HttpRequest request1 =HttpRequest.newBuilder()
                .uri(new URI("http://showresult:8080/showresult"))
                .POST(HttpRequest.BodyPublishers.ofString(String.valueOf(1)))
                .build();
        HttpResponse<String> response1=client.send(request1 , HttpResponse.BodyHandlers.ofString());
        model.addAttribute("message" , response1.body());
        return "result";

    }
}