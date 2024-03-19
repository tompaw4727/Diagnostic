package com.example.diagnostic.controllers;

import com.example.diagnostic.entities.Test;
import com.example.diagnostic.services.TestService;
import com.example.diagnostic.entities.Visit;
import com.example.diagnostic.services.VisitService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(path = "/employees")
public class EmployeesController {
    private VisitService visitService;
    private TestService testService;

    @Autowired
    public EmployeesController(VisitService visitService, TestService testService) {
        this.visitService = visitService;
        this.testService = testService;
    }

    @GetMapping()
    public String getTests() {
        return "for_employees";
    }

    @GetMapping("/technican/dashboard")
    public String getTechnicianDashboard() {
        return "technican_dashboard";
    }

    @GetMapping("/receptionist/dashboard")
    public String getReceptionistDashboard() {
        return "receptionist_dashboard";
    }

    @GetMapping("/update/appointment/form")
    public String getAppointmentUpdateForm(@RequestParam("visitId") String visitId,
                                           Model model) {
        try {
            int visitIdInt = Integer.parseInt(visitId);
            Visit visit = visitService.getVisitById(visitIdInt);
            Test test  =  testService.getTestById(visit.getTestId());

            model.addAttribute("visit", visit);
            model.addAttribute("test", test);

            return "appointment_update_form";

        }catch (NumberFormatException | EntityNotFoundException  e) {

            return "wrong_update_appointment";
        }
    }

    @GetMapping("/receptionist/form")
    public String getAppointmentForm() {
        return "appointment_form_re";
    }

}
