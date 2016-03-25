package com.thesoftwareguild.capstonecms.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Ollie
 */
@Controller
@RequestMapping(value = "/contact")
public class EmailController {

    @RequestMapping
    public String showPage() {

        return "emailForm";

    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public String sendMessage(@RequestParam("name") String name,
            @RequestParam("email") String email,
            @RequestParam("subject") String subject,
            @RequestParam("message") String message, Model model) {

        model.addAttribute("name", name);

        return "emailSuccess";

    }
}
