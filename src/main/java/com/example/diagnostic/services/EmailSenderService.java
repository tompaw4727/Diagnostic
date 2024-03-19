package com.example.diagnostic.services;

import com.example.diagnostic.entities.Test;
import com.example.diagnostic.entities.Visit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;


@Service
public class EmailSenderService {
    @Autowired
    private JavaMailSender mailSender;

    private VisitService visitServicervice;

    private TestService testService;

    @Autowired
    public EmailSenderService(JavaMailSender mailSender, VisitService visitService, TestService testService) {
        this.mailSender = mailSender;
        this.visitServicervice = visitService;
        this.testService = testService;
    }


    public void sendTestPreparationEmail(int visitId){
        Visit visit = visitServicervice.getVisitById(visitId);
        Integer testId = visit.getTestId();
        Test test = testService.getTestById(testId);

        String testName = test.getTestName();
        String testPreparation = test.getPreparation();;
        String testDescription = test.getTestDescription();;
        BigDecimal price = test.getPrice();

        String emailContent = "Dear Patient,\n\n"
                + "Confirmation of Your Diagnostic Visit:\n\n"
                + "Visit ID: " + visitId + "\n"
                + "Test Name: " + testName + "\n"
                + "Test Description: " + testDescription + "\n"
                + "Test Preparation: " + (testPreparation.isEmpty() ? "No special preparation is required for this test." : testPreparation) + "\n\n"
                + "Test Price: " + price + " USD\n"
                + "Payment Information: You can pay for the test on-site using your credit/debit card.\n\n"
                + "Thank you for choosing our diagnostic services. If you have any further questions, feel free to contact us.\n\n"
                + "Best regards,\n"
                + "Diagnostic Laboratory Team";

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("tomtest4727@gmail.com");
        message.setTo(visit.getPatientEmail());
        message.setText(emailContent);
        message.setSubject("Confirmation of Diagnostic Visit - Visit ID: " + visitId);
        mailSender.send(message);
    }

    public void sendTestResultEmail(int visitId) {
        Visit visit = visitServicervice.getVisitById(visitId);

        String emailContent = "Dear Patient,\n\n"
                + "Your recent diagnostic visit details:\n\n"
                + "Visit ID: " + visitId + "\n\n"
                + "Your test results are now available for viewing.\n\n"
                + "To access your results, please follow these steps:\n"
                + "1. Visit our website: exampleWebsite.com\n"
                + "2. Enter the Visit ID: " + visitId + "\n"
                + "3. Select the 'Diagnostic' facility.\n"
                + "4. View and download your test results.\n\n"
                + "If you have any questions or encounter issues, feel free to contact us.\n\n"
                + "Thank you for choosing our diagnostic services.\n\n"
                + "Best regards,\n"
                + "Diagnostic Laboratory Team";

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("tomtest4727@gmail.com");
        message.setTo(visit.getPatientEmail());
        message.setText(emailContent);
        message.setSubject("Access Your Diagnostic Test Results - Visit ID: " + visitId);
        mailSender.send(message);
    }

    public void sendSupportEmail(String patientEmail, String topic, String details) {
        String emailContent ="Message for Support Team. \n\n" + "Patient email:" + patientEmail + "\n\n" + "Question: " + "\n" + details;

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("tomtest4727@gmail.com");
        message.setTo("tomtest4727@gmail.com");
        message.setText(emailContent);
        message.setSubject(topic);
        mailSender.send(message);
    };
}
