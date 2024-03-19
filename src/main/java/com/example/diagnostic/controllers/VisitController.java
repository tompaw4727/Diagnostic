package com.example.diagnostic.controllers;

import com.example.diagnostic.services.VisitService;
import com.example.diagnostic.entities.Visit;
import com.example.diagnostic.services.EmailSenderService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;

@Controller
@RequestMapping(path = "/visits")
public class VisitController {
    private final VisitService visitService;
    private final EmailSenderService emailSenderService;

    @Autowired
    public VisitController(VisitService visitService, EmailSenderService emailSenderService) {
        this.visitService = visitService;
        this.emailSenderService = emailSenderService;
    }

    @GetMapping
    public String getVisits(Model model) {
        List<Visit> visits = visitService.getVisits();
        model.addAttribute("visits", visits);
        return "visits";
    }

    @DeleteMapping(path = "/delete/{visitId}")
    public String deleteVisit(@RequestParam("visitId") String visitId ) {
        try {
            int visitIdInt = Integer.parseInt(visitId);
            visitService.deleteVisit(visitIdInt);

            return "success_cancel_appointment";
        }catch (NumberFormatException | IllegalStateException e) {

            return "wrong_cancel_appointment";
        }

    }

    @PostMapping("/add/new/visit/status")
    public String addVisit(@RequestParam("patientEmail") String patientEmail,
                           @RequestParam("visitDate") String visitDate,
                           @RequestParam("testId") Integer testId,
                           @RequestParam("labId") Integer labId,
                           @RequestParam("tour") String tour,
                           Model model) {
        try {
            Visit visit = new Visit();
            visit.setPatientEmail(patientEmail);
            visit.setVisitDate(Date.valueOf(visitDate));
            visit.setTestId(testId);
            visit.setLabId(labId);
            visit.setTour(Visit.Tour.valueOf(tour));
            visitService.addNewVisit(visit);

            emailSenderService.sendTestPreparationEmail(visit.getId());

            return "success_appointment_book";
        }catch(IllegalStateException e) {
            model.addAttribute("errorMessage", e.getMessage());

            return "wrong_appointment_book";
        }catch (IllegalArgumentException  e) {
            String errorMessage = "There are issues with the information entered in the form. Please remember that all fields are mandatory.";
            model.addAttribute("errorMessage", errorMessage);

            return "wrong_appointment_book";
        }
    }

    @PutMapping(path = "/edit/{visitId}")
    @Transactional
    public String updateVisit(@PathVariable("visitId") int id,
                            @RequestParam(required = false) Integer testId,
                            @RequestParam(required = false) Integer labId,
                            @RequestParam(required = false) String patientEmail,
                            @RequestParam(required = false) String visitDate,
                            @RequestParam(required = false) Visit.Tour tour) {
        try {
            Date visitDateVal  = Date.valueOf(visitDate);

            visitService.updateVisit(id, testId, labId, patientEmail, visitDateVal, tour);
            return "success_update_appointment";
        }catch(IllegalArgumentException | IllegalStateException e) {

            return "wrong_update_appointment";
        }
    }

    @GetMapping("/{visitDate}")
    public String getVisitsByDay(@RequestParam("visitDate") String visitDate,
                                 Model model) {
        try {
            Date visitDateVal  = Date.valueOf(visitDate);

            List<Visit> visits = visitService.getVisitsByDay(visitDateVal);
            model.addAttribute("visits", visits);
            return "visits";

        }catch(IllegalArgumentException e) {
            return "tests";
        }
    }

    @GetMapping("/available/tours/{visitsDate}")
    public String getAvailableToursInDay(@RequestParam("visitsDate") String visitsDate,
                                         @RequestParam("labId") int labId,
                                         Model model) {
        try {

            List<Visit.Tour> available_tours = visitService.getAvailableToursInDay(Date.valueOf(visitsDate), labId);

            model.addAttribute("available_tours", available_tours);

            return "available_tours";
        }catch (IllegalArgumentException e) {

            return "available_tours_wrong";
        }
    }


    @PutMapping(path = "/technic/edit/visit_status/{visitId}")
    @Transactional
    public String updateVisitStatus(@RequestParam("visitId") String visitId,
                                    Model model) {
        try {
            int visitIdValid = Integer.valueOf(visitId);
            visitService.updateVisitStatus(visitIdValid);

            Visit.VisitStatus actualVisitStatus = (Visit.VisitStatus) visitService.getVisitById(visitIdValid).getVisitStatus();

            model.addAttribute("success_visit_status_change", actualVisitStatus);

            return "success_visit_status_change";

        }catch(NumberFormatException | IllegalStateException e) {
            return "wrong_visit_status_change";
        }

    }

    @PutMapping(path = "technic/edit/visit_result_status/{visitId}")
    @Transactional
    public String updateVisitResultStatus(@RequestParam("visitId") String visitId,
                                        Model model) {
        try {
            int visitIdValid = Integer.valueOf(visitId);
            visitService.updateVisitResultStatus(visitIdValid);

            Visit.ResultStatus actualResultsStatus =  visitService.getVisitById(visitIdValid).getResultsStatus();

            model.addAttribute("success_results_status_change", actualResultsStatus);

            return "success_results_status_change";
        }catch(NumberFormatException | IllegalStateException e) {
            return "wrong_results_status_change";
        }

    }




}
