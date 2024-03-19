package com.example.diagnostic.controllers;

import com.example.diagnostic.services.EmailSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = "/patients")
public class PatientsController {

    private final EmailSenderService emailSenderService;

    @Autowired
    public PatientsController(EmailSenderService emailSenderService) {
        this.emailSenderService = emailSenderService;
    }

    @GetMapping()
    public String getTests() {
        return "for_patients";
    }

    @GetMapping("/appointment/form")
    public String getAppointmentForm() {
        return "appointment_form";
    }

    @GetMapping("/appointment/book/by/phone")
    public String getBookByPhone() {
        return "book_by_phone";
    }

    @GetMapping("/support")
    public String getSupport() {
        return "support";
    }




}
