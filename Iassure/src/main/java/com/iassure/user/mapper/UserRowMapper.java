package com.iassure.user.mapper;

import com.iassure.user.dto.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Naveen Kumar Chintala
 */
public class UserRowMapper implements RowMapper<User> {

    //PasswordEncoder passwordEncoder=new BCryptPasswordEncoder();

    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        User user = new User();
        user.setId(rs.getInt("pk_user_details_id"));
        user.setName(rs.getString("full_name") != null ? rs.getString("full_name") : "");
        //System.out.println("User password::" + rs.getString("password"));
        //System.out.println("User password(Encoded)::" + passwordEncoder.encode(rs.getString("password")));
        user.setPassword(rs.getString("password"));
        user.setEmail(rs.getString("email"));
        user.setMobile(rs.getString("contact_no"));
        return user;
    }

}
