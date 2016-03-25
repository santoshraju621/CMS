package com.thesoftwareguild.capstonecms.controllers;

import com.thesoftwareguild.capstonecms.daos.PostDao;
import com.thesoftwareguild.capstonecms.dtos.Post;
import com.thesoftwareguild.capstonecms.dtos.Tag;
import java.util.List;
import javax.inject.Inject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class HomeController {
    
    PostDao postDao;
    
    @Inject
    public HomeController(PostDao postDao) { 
        
        this.postDao = postDao;
        
    }
    
    
    @RequestMapping(value="/home")
    public String home(Model model) {
        
        Post post = postDao.getNewest();
        List<Tag> tags = post.getTags();

        model.addAttribute("post", post);
        model.addAttribute("tags", tags);

        return "index";
    }
    
    @RequestMapping(value="/admin-login")
    public String adminLogin() {

        return "admin-login";

    }
    
    @RequestMapping(value="/admin")
    public String adminPage() {

        return "admin";

    }
    
        
//         @RequestMapping(value = "/{id}")
//    public String show(@PathVariable("id") Integer postId, Model model) {
//
//        Post post = postDao.get(postId);
//        List<Tag> tags = post.getTags();
//
//        model.addAttribute("post", post);
//        model.addAttribute("tags", tags);
//
//        return "showpost";
//
//    }
        
        
        
        /////////////////////Strip html tags out of content
        
        ////////////////////////Pass info to index
//        model.addAttribute("mostRecentId", blog.id)
//        model.addAttribute("mostRecentContet", )        
        
        
        
    }
