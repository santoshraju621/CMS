/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.thesoftwareguild.capstonecms.daos;

import com.thesoftwareguild.capstonecms.dtos.User;
import java.util.List;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Ollie
 */
public interface UserDao {

    @Transactional(propagation = Propagation.REQUIRED)
    User add(User user);

    User get(Integer id);
    
    User getByName(String name);

    List<User> list();

    void remove(Integer id);

    void update(User user);
    
}
