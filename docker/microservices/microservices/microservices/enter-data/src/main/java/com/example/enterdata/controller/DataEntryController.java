package com.example.enterdata.controller;


import com.example.enterdata.Data.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class DataEntryController {
    @Autowired
    private JdbcTemplate template;

    @PostMapping("/show_data_form")
    public String viewDataForm(Model model){
        model.addAttribute("dataObject" , new Data());
        return"enter-data";
    }

    @PostMapping("/submit_datas")
    public String handlingGrades(@ModelAttribute Data dataObject , HttpServletRequest request){
        String data = dataObject.getdata();
        String[] arr = data.split(",");
        Data[] users = new Data[arr.length];
        for (int i =0 ; i<users.length ; i++){
            users[i] = new Data();
            users[i].generateUser(arr[i]);
        }
        for (Data d : users){
            template.update("INSERT INTO Datas values(?,?)",d.getUserName() , d.getData());
        }
        return"forward:/microservice";
    }



}