/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

//import com.thesoftwareguild.capstonecms.daos.UserDao;
//import com.thesoftwareguild.capstonecms.dtos.User;
//import java.util.ArrayList;
//import java.util.List;
//import org.junit.After;
//import org.junit.Assert;
//import org.junit.Before;
//import org.junit.Test;
//import static org.junit.Assert.*;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.support.ClassPathXmlApplicationContext;
//
///**
// *
// * @author apprentice
// */
//public class UserTest {
//    
//    UserDao userDao;
//    
//    User user;
//    
//    ApplicationContext ctx;
//    
//    public UserTest() {
//        
//        this.ctx = new ClassPathXmlApplicationContext("test-applicationContext_1.xml");
//        this.userDao = ctx.getBean("userDao", UserDao.class);
//    }
//    
//    @Before
//    public void setUp() {
//    }
//    
//    @After
//    public void tearDown() {
//    }
//
//    @Test
//    public void hello() {
//        String hello = "hello"; //Test just to see that setup is not throwing exceptions or the project doesn't need to clean and build.
//
//        Assert.assertEquals("hello", hello);
//    }
//    
//    @Test
//    public void addTest()   {
//        
//        User userAdd = new User();
//        
//        List<String> authorities = new ArrayList();
//        
//        authorities.add("ROLE_USER");
//        authorities.add("ROLE_ADMIN");
//        
//        userAdd.setAuthorities(authorities);
//        
//        userAdd.setUsername("TestOllie");
//        userAdd.setPassword("Password");
//        userAdd.setEnabled(true);
//        userAdd.setId(userDao.add(userAdd).getId());
//        
//        Assert.assertEquals(userAdd, userAdd);
//        
//        
//    }
//    
//    @Test
//    public void getTest()   {
//        
//        User userGet;
//        
//        userGet = userDao.get(8); ///<--- change this to a number known to be in the database 
//        
//        Assert.assertEquals(userGet, userGet);
//        
//    }
//    
//    @Test
//    public void listTest()  {
//        
//        Assert.assertEquals(userDao.list().size(), userDao.list().size());
//        
//    }
//    
//    @Test
//    public void updateTest()    {
//        
//        User userUpdate = userDao.get(8);
//        
//        userUpdate.setUsername("OllieUpdateTest19");
//        
//        userUpdate.getAuthorities().add("ROLL_UPDATE");
//        
//        userDao.update(userUpdate);
//        
//        Assert.assertSame(userUpdate.getUsername(), userUpdate.getUsername());
//        
//    }
//    
//    @Test
//    public void removeTest()    {
//        
//        userDao.remove(8); //Test passed, checked database to see that user was removed and authorities were removeed. --Ollie
//        
//        
//        
//    }
//}