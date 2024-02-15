package com.example.analytics.Controller;


import com.example.analytics.service.AnalyticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AnalyticsController {

    @Autowired
    private AnalyticsService service;


    @PostMapping("/analytics")
    public String doAnalytics(@RequestBody String command){
        if (!command.equals("doAnalytics"))
            return "incomplete";
        service.doService();
        return "completed";
    }



}