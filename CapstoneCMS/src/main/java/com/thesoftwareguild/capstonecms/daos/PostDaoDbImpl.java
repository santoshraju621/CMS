package com.thesoftwareguild.capstonecms.daos;

import com.thesoftwareguild.capstonecms.dtos.Category;
import com.thesoftwareguild.capstonecms.dtos.Post;
import com.thesoftwareguild.capstonecms.dtos.Tag;
import com.thesoftwareguild.capstonecms.dtos.User;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public class PostDaoDbImpl implements PostDao {

    private JdbcTemplate template;
    private CategoryDao categoryDao;
    private TagDao tagDao;

    private static final String SQL_GET_POST = "select p.id, p.title, p.content, p.published, p.expiration, p.is_approved, p.created, c.id as category_id, c.name as category_name, u.id as user_id, u.username as username FROM holiday.posts p join holiday.categories c on p.category_id = c.id join holiday.users u on p.user_id = u.id where p.id = ?;";
    private static final String SQL_INSERT_POST = "INSERT INTO `holiday`.`posts` (`title`, `content`, `category_id`, `published`, `expiration`, `is_approved`, `user_id`) VALUES (?, ?, ?, ?, ?, ?, ?);";
    private static final String SQL_CHECKTAGS_EXIST = "SELECT exists (select tags.id from tags where tags.name = ?)";
    private static final String SQL_INSERT_TAG = "INSERT INTO `holiday`.`tags` (`name`) VALUES (?)";
    private static final String SQL_GET_TAGID = "Select tags.id from tags where tags.name = ?";
    private static final String SQL_INSERT_TAG_POST_LINKING = "INSERT INTO `holiday`.`posts_tags` (`post_id`, `tag_id`) VALUES (?, ?)";
    private static final String SQL_REMOVE_POST_TAGS = "delete from holiday.posts_tags where posts_tags.post_id = ?;";
    private static final String SQL_REMOVE_POST = "DELETE FROM holiday.posts WHERE id = ?";
    private static final String SQL_UPDATE_POST = "UPDATE `holiday`.`posts` SET `title`=?, `content`=?,`category_id`=?, `is_approved`=?, `user_id`=?, `published`=?,  `expiration`=? WHERE `id`=?";
    private static final String SQL_LIST = "select p.id, p.title, p.content, p.published, p.expiration, p.is_approved, p.created, c.id as category_id, c.name as category_name, u.id as user_id, u.username as username FROM holiday.posts p join holiday.categories c on p.category_id = c.id join holiday.users u on p.user_id = u.id order by published DESC;";
    private static final String SQL_GET_TAGS = "select t.id, t.name from holiday.posts p left join holiday.posts_tags pt on p.id = pt.post_id left join holiday.tags t on pt.tag_id = t.id where p.id = ?;";
    private static final String SQL_GET_NEWEST_POST_ID = "SELECT id FROM holiday.posts order by created DESC limit 1";
    
    public PostDaoDbImpl(JdbcTemplate template, TagDao tagDao, CategoryDao categoryDao) {
        this.template = template;
        this.tagDao = tagDao;
        this.categoryDao = categoryDao;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Post add(Post post) {

        template.update(SQL_INSERT_POST, post.getTitle(),
                post.getContent(),
                post.getCategory().getId(),
                post.getPublished().toString(),
                post.getExpiration().toString(),
                post.isIsApproved(),
                post.getUser().getId()
        );

        Integer postId = template.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        String sqlGetCreated = ("SELECT posts.created FROM holiday.posts where posts.id = " + postId);

        if (post.getTags() != null) {
            for (Tag t : post.getTags()) {
                Boolean isExist = template.queryForObject(SQL_CHECKTAGS_EXIST, Boolean.class, t.getName());

                if (!isExist) {
                    template.update(SQL_INSERT_TAG, t.getName());
                    Integer tagId = template.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
                    template.update(SQL_INSERT_TAG_POST_LINKING, postId, tagId);
                } else {
                    Integer tagId = template.queryForObject(SQL_GET_TAGID, Integer.class, t.getName());
                    template.update(SQL_INSERT_TAG_POST_LINKING, postId, tagId);
                }

            }
        }

        post.setId(postId);
        
        return post;
    }

    @Override
    public Post get(Integer id) {
        Post post = template.queryForObject(SQL_GET_POST, new PostMapper(), id);

        List<Tag> tags = template.query(SQL_GET_TAGS, new TagMapper(), id);

        post.setTags(tags);

        return post;
    }
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Post getNewest() {
        Integer id = template.queryForObject(SQL_GET_NEWEST_POST_ID, Integer.class);
        Post post = template.queryForObject(SQL_GET_POST, new PostMapper(), id);

        List<Tag> tags = template.query(SQL_GET_TAGS, new TagMapper(), id);

        post.setTags(tags);

        return post;
    }

    @Override
    public void remove(Integer id) {
        template.update(SQL_REMOVE_POST_TAGS, id);
        template.update(SQL_REMOVE_POST, id);
    }
//"UPDATE `holiday`.`posts` SET `title`=?, `content`=?,`category_id`=?, 
//    `is_approved`=?, `user_id`=?, `published`=?,  `expiration`=? WHERE `id`=?";

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Post update(Post post) {

        Integer userId = 6;
        Boolean isApproved = true;
        template.update(SQL_UPDATE_POST, post.getTitle(),
                post.getContent(),
                post.getCategory().getId(),
                isApproved,
                userId,
                post.getPublished().toString(),
                post.getExpiration().toString(),
                post.getId()
                );
        Integer postId = post.getId();
        
        if (post.getTags() != null) {
            template.update(SQL_REMOVE_POST_TAGS, postId);
            for (Tag t : post.getTags()) {
                Boolean isExist = template.queryForObject(SQL_CHECKTAGS_EXIST, Boolean.class, t.getName());

                if (!isExist) {
                    template.update(SQL_INSERT_TAG, t.getName());
                    Integer tagId = template.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
                    template.update(SQL_INSERT_TAG_POST_LINKING, postId, tagId);
                } else {
                    Integer tagId = template.queryForObject(SQL_GET_TAGID, Integer.class, t.getName());
                    template.update(SQL_INSERT_TAG_POST_LINKING, postId, tagId);
                }
            }
        }

        return post;
    }

    @Override
    public List<Post> list() {

        List<Post> posts = template.query(SQL_LIST, new PostMapper());
        
        for (Post p : posts) {
            List<Tag> tags = template.query(SQL_GET_TAGS, new TagMapper(), p.getId());
            p.setTags(tags);
        }
        
        
        return posts;

    }

    @Override
    public List<Post> search(String search) {

        List<Post> posts = new ArrayList(); //shitcan for actual method

        return posts;

    }

    private static class PostMapper implements RowMapper<Post> {

        @Override
        public Post mapRow(ResultSet rs, int i) throws SQLException {
            Post post = new Post();

            post.setId(rs.getInt("id"));
            post.setTitle(rs.getString("title"));
            post.setContent(rs.getString("content"));
            post.setCreated(rs.getDate("created").toLocalDate());
            post.setPublished(rs.getDate("published").toLocalDate());
            post.setExpiration(rs.getDate("expiration").toLocalDate());
            post.setIsApproved(rs.getBoolean("is_approved"));

            Category category = new Category();
            category.setId(rs.getInt("category_id"));
            category.setName(rs.getString("category_name"));
            post.setCategory(category);

            User user = new User();
            user.setId(rs.getInt("user_id"));
            user.setUsername(rs.getString("username"));
            post.setUser(user);

            return post;
        }
    }

    private static class TagMapper implements RowMapper<Tag> {

        @Override
        public Tag mapRow(ResultSet rs, int i) throws SQLException {
            Tag tag = new Tag();

            tag.setId(rs.getInt("id"));
            tag.setName(rs.getString("name"));

            return tag;

        }
    }

}
