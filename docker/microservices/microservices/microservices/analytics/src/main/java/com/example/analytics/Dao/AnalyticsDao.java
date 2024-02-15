package com.example.analytics.Dao;

import com.example.analytics.model.DataStatistics;
import com.example.analytics.model.UserData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class AnalyticsDao {



    @Autowired
    private JdbcTemplate template;

    private static final class UserDataMapper implements RowMapper<UserData> {
        @Override
        public UserData mapRow(ResultSet rs, int rowNum) throws SQLException {
            UserData user = new UserData();
            user.setData(rs.getDouble("data"));
            user.setUserName(rs.getString("user_name"));
            return user;
        }
    }




    public List<UserData> readGrades(){
        return template.query("SELECT * FROM datas" ,new UserDataMapper() );
    }

    public void writeStatistics(DataStatistics dataStatistics){

        template.update("INSERT INTO statistics values(?,?,?,?)" , 1 , dataStatistics.getMin() , dataStatistics.getMax(),dataStatistics.getAvg());
    }

}