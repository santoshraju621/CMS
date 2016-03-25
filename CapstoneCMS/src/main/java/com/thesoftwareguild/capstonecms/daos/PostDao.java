package com.thesoftwareguild.capstonecms.daos;

import com.thesoftwareguild.capstonecms.dtos.Post;
import java.util.List;


public interface PostDao {

    Post add(Post post);

    Post get(Integer id);

    List<Post> list();

    void remove(Integer id);

    List<Post> search(String search);

    Post update(Post post);
    
    Post getNewest();
    
}
