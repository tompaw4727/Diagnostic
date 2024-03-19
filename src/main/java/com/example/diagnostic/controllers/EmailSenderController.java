package com.example.diagnostic.controllers;

import com.example.diagnostic.services.EmailSenderService;
import com.example.diagnostic.services.VisitService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class EmailSenderController {
    private EmailSenderService emailSenderService;
    private VisitService visitService;


    @Autowired
    public EmailSenderController(EmailSenderService emailSenderService, VisitService visitService){
        this.emailSenderService = emailSenderService;
        this.visitService = visitService;
    }

    @PostMapping("/send/preparation/email")
    @ResponseBody
    public void sendTestPreparationEmail(@RequestParam("visitId") int visitId){

        emailSenderService.sendTestPreparationEmail(visitId);
    }

    @PostMapping("/send/test/result/email")
    public String sendTestResultEmail(@RequestParam("visitId") String visitId){
        try {
            int visitIdInt = Integer.parseInt(visitId);
            emailSenderService.sendTestResultEmail(visitIdInt);

            return "email_send_succes";
        }catch(EntityNotFoundException e ) {

            return "email_send_wrong";
        } catch (NumberFormatException e ) {

            return "email_send_wrong";
        }

    }

    @PostMapping("/send/support/email")
    public String sendSupportEmail(@RequestParam("patientEmail") String patientEmail,
                                   @RequestParam("topic") String topic,
                                   @RequestParam("details") String details){
        if (patientEmail.length() == 0 || topic.length() == 0 || details.length() == 0 ){
            return "question_send_wrong";
        }else {
            emailSenderService.sendSupportEmail(patientEmail, topic, details);
            return "question_send_succes";
        }

    }



}
