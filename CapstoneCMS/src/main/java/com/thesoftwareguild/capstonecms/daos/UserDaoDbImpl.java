package com.thesoftwareguild.capstonecms.daos;

import com.thesoftwareguild.capstonecms.dtos.User;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


public class UserDaoDbImpl implements UserDao {

    private static final String SQL_INSERT_USER = "INSERT into users (username, password, enabled) values (?, ?, ?);";
    private static final String SQL_INSERT_AUTHORITIES = "INSERT into authorities (user_id, authority) values (?, ?)";
    private static final String SQL_GET_USER = "SELECT * FROM `users` WHERE id = ?";
    private static final String SQL_GET_USERNAME = "SELECT * FROM `users` WHERE username = ?";
    private static final String SQL_GET_AUTHORITIES = "SELECT authority from authorities WHERE user_id = ?";
    private static final String SQL_LIST_USERS = "SELECT * FROM users;";
    private static final String SQL_REMOVE_USER = "DELETE FROM users WHERE id = ?";
    private static final String SQL_REMOVE_AUTHORITY = "DELETE from authorities where user_id = ?;";
    private static final String SQL_UPDATE_USER = "UPDATE users set username = ?, password = ?, enabled = ? where id = ? ";
    
    
    private JdbcTemplate template;

    public UserDaoDbImpl(JdbcTemplate template) {
        this.template = template;
    }
    
    
    /*
    ############## Basic CRUD operation, add, get, list, update, remove with two Row Mapper inner classes below
    ############## All of these methods have been tested, see JUnit UserTest.java
    ############## The only tricky part of this Dao was the 'authorities' list, which required its own mapper even though the return 
    ############## was just strings (still have to use a Mapper if you're expecting more than one SQL RESULT).
    ############## One thing to note with the List<String> authorities, I assumed the List would be built before reaching this DAO
    ############## for example, in the controller. You'll see the comments below.
    */

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public User add(User user) {
        
        template.update(SQL_INSERT_USER, user.getUsername(), user.getPassword(), user.isEnabled());

        Integer newId = template.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        user.setId(newId);

        for (String authority : user.getAuthorities()) { //<---I assumed this list existed and was not null
            template.update(SQL_INSERT_AUTHORITIES, user.getId(), authority);
        }
        return user;
    }

    @Override
    public User get(Integer id) {

        User user = template.queryForObject(SQL_GET_USER, new UserMapper(), id);
        List<String> authorities = template.query(SQL_GET_AUTHORITIES, new AuthoritiesMapper(), user.getId());

        user.setAuthorities(authorities);

        return user;
    }
    
    public User getByName(String name) {

        User user = template.queryForObject(SQL_GET_USERNAME, new UserMapper(), name);
        List<String> authorities = template.query(SQL_GET_AUTHORITIES, new AuthoritiesMapper(), user.getId());

        user.setAuthorities(authorities);

        return user;
    }
    
    @Override
    public List<User> list()    {
        
        List<User> list = template.query(SQL_LIST_USERS, new UserMapper());
        
        for (User user : list)   {
            List<String> authorities = template.query(SQL_GET_AUTHORITIES, new AuthoritiesMapper(), user.getId());
            user.setAuthorities(authorities);
            
        }
        
        return list;
    }
    
    @Override
    public void update(User user)   {
        
        template.update(SQL_UPDATE_USER, user.getUsername(), user.getPassword(), user.isEnabled(), user.getId());
        
        template.update(SQL_REMOVE_AUTHORITY, user.getId());//<---deletes all "authorities" in SQL DB and resets the entries below
        
        for (String authority : user.getAuthorities())  {
            template.update(SQL_INSERT_AUTHORITIES, user.getId(), authority);//<----adds all authorities to SQL DB from user, authorities list must be complete here
        }
    }
    
    @Override
    public void remove(Integer id)  {
        template.update(SQL_REMOVE_USER, id);
    }
    
    
    ///////////Row Mapper inner classes//////////////

    private static class UserMapper implements RowMapper<User> {

        public UserMapper() {
        }

        @Override
        public User mapRow(ResultSet rs, int i) throws SQLException {

            User user = new User();

            user.setId(rs.getInt("id"));
            user.setEnabled(rs.getBoolean("enabled"));
            user.setPassword(rs.getString("password"));
            user.setUsername(rs.getString("username"));

            return user;

        }
    }

    private static class AuthoritiesMapper implements RowMapper<String> {

        public AuthoritiesMapper() {
        }

        @Override
        public String mapRow(ResultSet rs, int i) throws SQLException {

            return rs.getString("authority");

        }
    }

}
