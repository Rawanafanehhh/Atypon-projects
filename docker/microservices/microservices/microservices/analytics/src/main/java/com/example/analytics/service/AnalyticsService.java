package com.example.analytics.service;

import com.example.analytics.Dao.AnalyticsDao;
import com.example.analytics.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnalyticsService {
    @Autowired
    private AnalyticsDao dao;


    public void doService(){
        List<UserData> data = dao.readGrades();
        DataStatistics statistics = getStatistics(data);
        dao.writeStatistics(statistics);
    }

    private DataStatistics getStatistics(List<UserData> data){
        double min = Double.MAX_VALUE;
        double max = Double.MIN_VALUE;
        double avg = 0;

        for (UserData Data : data){
            if (Data.getData()<min)
                min=Data.getData();
            if (Data.getData()>max)
                max=Data.getData();
            avg+=Data.getData();
        }
        return new DataStatistics(min , max , avg/data.size());
    }


}