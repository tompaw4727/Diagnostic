package com.example.diagnostic.controllers;

import com.example.diagnostic.services.TestService;
import com.example.diagnostic.entities.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping(path = "/tests")
public class TestController {
    private final TestService testService;

    @Autowired
    public TestController(TestService testService) {
        this.testService = testService;
    }

    @GetMapping
    public String getVisits(Model model) {
        List<Test> tests = testService.getTests();
        model.addAttribute("tests", tests);
        return "tests";
    }
}
