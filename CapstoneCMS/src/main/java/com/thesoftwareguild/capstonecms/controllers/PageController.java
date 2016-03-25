package com.thesoftwareguild.capstonecms.controllers;

import com.thesoftwareguild.capstonecms.daos.PageDao;
import com.thesoftwareguild.capstonecms.dtos.Page;
import java.util.List;
import javax.inject.Inject;
import javax.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping(value = "/page")
public class PageController {

    private PageDao pageDao;

    @Inject
    public PageController(PageDao pageDao) {
        this.pageDao = pageDao;
    }

    @RequestMapping(value = "/create")
    public String create() {
        return "addpage";
    }
    
    @RequestMapping(value = "/show/{id}")
    public String show(Model model, @PathVariable Integer id) {
        model.addAttribute("page", pageDao.get(id));
        return "showpage";
    }

    
    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseBody
    public Page add(@Valid @RequestBody Page page) {
        Page addedPage = pageDao.add(page);
        return addedPage;
    }

    @RequestMapping(value = "/{id}")
    @ResponseBody
    public Page show(@PathVariable("id") Integer id) {
        return pageDao.get(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public void delete(@PathVariable Integer id) {

        pageDao.remove(id);

    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public Page edit(@RequestBody Page page, @PathVariable Integer id) {

        page.setId(id);
        
        pageDao.update(page);

        return page;
    }
    
    @RequestMapping(value = "/all")
    @ResponseBody
    public List list(){
        return pageDao.list();
    }

}
