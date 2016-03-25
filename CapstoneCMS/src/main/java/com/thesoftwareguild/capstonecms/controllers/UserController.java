
package com.thesoftwareguild.capstonecms.controllers;

import com.thesoftwareguild.capstonecms.daos.UserDao;
import com.thesoftwareguild.capstonecms.dtos.Page;
import com.thesoftwareguild.capstonecms.dtos.User;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.validation.Valid;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value="/user")
public class UserController {
    private UserDao userDao;
    private PasswordEncoder encoder;
    
    @Inject
    public UserController(UserDao userDao, PasswordEncoder encoder){
        this.userDao = userDao;
        this.encoder = encoder;
    }
    
    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseBody
    public User add(@RequestBody User user) {
        
        String encodedPassword = encoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        
        user.setEnabled(true);
        
        user.getAuthorities().add("ROLE_ADMIN");
        
        return userDao.add(user);
    }
    
    @RequestMapping(value = "/{id}")
    @ResponseBody
    public User show(@PathVariable("id") Integer id) {
        
        return userDao.get(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public void delete(@PathVariable Integer id) {
        userDao.remove(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public User edit(@RequestBody User user, @PathVariable Integer id) {

        user.setId(id);
        
        String encodedPassword = encoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        
        user.getAuthorities().add("ROLE_ADMIN");
        user.setEnabled(true);
        
        userDao.update(user);

        return user;
    }
    
    @RequestMapping(value = "/all")
    @ResponseBody
    public List list(){
        return userDao.list();
    }
}
