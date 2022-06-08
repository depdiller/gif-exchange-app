package com.alfa.exchangerate.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/app")
public class PageController {
    private List<String> currencyCodes;

    @RequestMapping("/home")
    public String home(Model model) {
        model.addAttribute("currencyCodes", currencyCodes);
        return "home";
    }
}
