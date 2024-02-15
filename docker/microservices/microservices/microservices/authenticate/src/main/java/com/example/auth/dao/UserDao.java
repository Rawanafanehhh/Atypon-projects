package com.example.auth.dao;

import com.example.auth.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;

import java.sql.SQLException;



@Repository
public class UserDao {
    @Autowired
    private JdbcTemplate template;

    private static final class UserMapper implements RowMapper<User>{

        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            User user = new User();
            user.setUsername(rs.getString("username"));
            user.setPassword(rs.getString("password"));
            return user;
        }
    }
    public User getUserByUsername(String username){
        User user =template.queryForObject("SELECT username,password FROM user WHERE username=?" , new UserMapper() , username);
        return user;
    }

}