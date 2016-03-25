package com.thesoftwareguild.capstonecms.controllers;

import com.thesoftwareguild.capstonecms.daos.CategoryDao;
import com.thesoftwareguild.capstonecms.daos.PostDao;
import com.thesoftwareguild.capstonecms.daos.TagDao;
import com.thesoftwareguild.capstonecms.daos.UserDao;
import com.thesoftwareguild.capstonecms.dtos.Category;
import com.thesoftwareguild.capstonecms.dtos.CommandPost;
import com.thesoftwareguild.capstonecms.dtos.Post;
import com.thesoftwareguild.capstonecms.dtos.Tag;
import com.thesoftwareguild.capstonecms.dtos.User;
import com.thesoftwareguild.capstonecms.views.TagCatSelect;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/post")
public class PostController {

    private PostDao postDao;
    private CategoryDao catDao;
    private TagDao tagDao;
    private UserDao userDao;

    @Inject
    public PostController(PostDao postDao, CategoryDao catDao, TagDao tagDao, UserDao userDao) {
        this.postDao = postDao;
        this.catDao = catDao;
        this.tagDao = tagDao;
        this.userDao = userDao;
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseBody
    public Post add(@Valid @RequestBody CommandPost cp) {
        
        Post post = new Post();
        
        post.setTitle(cp.getTitle());
        post.setContent(cp.getContent());
        
        Category category = new Category();
        category.setId(cp.getCategoryId());
        post.setCategory(category);
        
        List<Tag> tags = new ArrayList();
        for(String holder : cp.getTags()){
            Tag tag = new Tag();
            tag.setName(holder);
            tags.add(tag);
        }
        post.setTags(tags);
        
        post.setPublished(LocalDate.parse(cp.getPublished()));
        post.setExpiration(LocalDate.parse(cp.getExpiration()));
        
        post.setIsApproved(true);
        
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        User user = userDao.getByName(authentication.getName());
        
        post.setUser(user);
        
        return postDao.add(post);
    }

    @RequestMapping(value = "/select")
    @ResponseBody
    public TagCatSelect getTagsCats() {

        TagCatSelect tagsCats = new TagCatSelect();

        List<Tag> tags = tagDao.list();
        List<Category> cats = catDao.list();
        tagsCats.setTags(tags);
        tagsCats.setCategory(cats);

        return tagsCats;

    }

    @RequestMapping(value="/showedit/{id}")
    public String showedit(@PathVariable("id") Integer postId, Model model) {
        
        Post post = postDao.get(postId);
        List<Tag> tags = post.getTags();
        
        model.addAttribute("post", post);
        model.addAttribute("tags", tags);
        
        return "editpost";
        
    }
    
    

    @RequestMapping(value = "/{id}")
    @ResponseBody
    public Post show(@PathVariable("id") Integer id) {
        return postDao.get(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public void delete(@PathVariable("id") Integer id) {

        postDao.remove(id);

    }

    
    @RequestMapping(value="/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public Post edit(@Valid @RequestBody CommandPost cp, @PathVariable Integer id){
        
        Post post = new Post();
        
        post.setId(id);
        post.setTitle(cp.getTitle());
        post.setContent(cp.getContent());
        
        Category category = new Category();
        category.setId(cp.getCategoryId());
        post.setCategory(category);
        
        List<Tag> tags = new ArrayList();
        for(String holder : cp.getTags()){
            Tag tag = new Tag();
            tag.setName(holder);
            tags.add(tag);
        }
        post.setTags(tags);
        
        post.setPublished(LocalDate.parse(cp.getPublished()));
        post.setExpiration(LocalDate.parse(cp.getExpiration()));
        
        return postDao.update(post);
    }

    @RequestMapping(value = "/all")
    public String list(Model model){

        List<Post> posts = postDao.list();
        model.addAttribute("allposts", posts);
        
        return "showallpost";

    }
    
    @RequestMapping(value = "/list")
    @ResponseBody
    public List list(){
        return postDao.list();
    }
    
    @RequestMapping(value="/category")
    public String category() {
        return "category"; 
    }
    
    @RequestMapping(value = "/category/{id}")
    @ResponseBody
    public Category showCategory(@PathVariable Integer id) {
        return catDao.get(id);
    }
    
    @RequestMapping(value = "/category/all")
    @ResponseBody
    public List listCategories() {
        return catDao.list();
    }
    
    @RequestMapping(value = "/category", method = RequestMethod.POST)
    @ResponseBody
    public Category addCategory(@RequestBody Category category) {
        return catDao.add(category);
    }
    
    @RequestMapping(value = "/category/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public Category editCategory(@RequestBody Category category, @PathVariable Integer id) {
        category.setId(id);
        return catDao.update(category);
    }
    
    @RequestMapping(value = "/category/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public void deleteCategory(@PathVariable Integer id) {
        catDao.remove(id);
    }

}
