package com.example.showresults;

import com.example.showresults.model.DataStatistics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.ResultSet;
import java.sql.SQLException;

@RestController
public class ShowResultsController {


    @Autowired
    private JdbcTemplate template;

    @PostMapping("/showresult")
    public String showResult(@RequestBody String id ){
        DataStatistics statistics = template.queryForObject("SELECT min , max , avg FROM statistics WHERE id=?" , new DataStatisticsMapper() , id);
        return statistics.toString();
    }



    private static final class DataStatisticsMapper implements RowMapper<DataStatistics> {
        @Override
        public DataStatistics mapRow(ResultSet rs, int rowNum) throws SQLException {
            DataStatistics statistics = new DataStatistics();
            statistics.setAvg(rs.getDouble("avg"));
            statistics.setMin(rs.getDouble("min"));
            statistics.setMax(rs.getDouble("max"));
            return statistics;
        }
    }


}