package com.thesoftwareguild.capstonecms.daos;

import com.thesoftwareguild.capstonecms.dtos.Category;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public class CategoryDaoDbImpl implements CategoryDao {
    
private JdbcTemplate template;
    
    private static final String SQL_GET_CAT = "Select * FROM holiday.categories where id = ?";
    private static final String SQL_INSERT_CAT = "INSERT INTO `holiday`.`categories` (`name`) VALUES (?)";
    private static final String SQL_REMOVE_CAT = "DELETE FROM `holiday`.`categories` WHERE `id`= ?";
    private static final String SQL_UPDATE_CAT = "UPDATE `holiday`.`categories` SET `name`= ? WHERE `id`= ?";
    private static final String SQL_LIST = "Select * FROM holiday.categories";
    private static final String SQL_SEARCH = "";
    
   @Inject
    public CategoryDaoDbImpl(JdbcTemplate template)    {
        
        this.template = template; 
        
    }
    
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Category add(Category category)  {
        template.update(SQL_INSERT_CAT, category.getName());
        Integer categoryId = template.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        category.setId(categoryId);
        return category;
    }
    
    
    @Override
    public Category get(Integer id) {
        return template.queryForObject(SQL_GET_CAT, new CatMapper(), id);
    }
    
    @Override
    public void remove(Integer id)  {
        template.update(SQL_REMOVE_CAT, id);
    }
    
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Category update(Category category)   {
        template.update(SQL_UPDATE_CAT, category.getName(), category.getId());
        return category;
    }
    
    @Override
    public List<Category> list()    {
        
        List<Category> categories = template.query(SQL_LIST, new CatMapper());
        
        return categories;       
    }
    
    @Override
    public List<Category> search(String search) {
        
        List<Category> categories = new ArrayList(); //shitcan for actual method
        
        return categories;
        
    }

    private static class CatMapper implements RowMapper<Category> {

        public CatMapper() {
        }

        @Override
        public Category mapRow(ResultSet rs, int i) throws SQLException {
            Category cat = new Category();
            
            cat.setId(rs.getInt("id"));
            cat.setName(rs.getString("name"));
            
            return cat;
        
        }
    }

   

}
