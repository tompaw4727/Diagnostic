package com.example.diagnostic.controllers;

import com.example.diagnostic.services.LabService;
import com.example.diagnostic.entities.Lab;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("labs")
public class LabController {
    private final LabService labService;

    @Autowired
    public LabController(LabService labService) {this.labService = labService;}

    @Transactional
    @PutMapping(path = "/edit/status/{labId}")
    public String updateLabStatus(@RequestParam("labId") String labId,
                                  Model model){
        try {
            int labIdValid = Integer.valueOf(labId);

            labService.updateLabStatus(labIdValid);

            Lab.LabStatus actualLabStatus = (Lab.LabStatus) labService.getLabById(labIdValid).getLabStatus();

            model.addAttribute("success_lab_status_change", actualLabStatus);

            return "success_lab_status_change";

        } catch(NumberFormatException | IllegalStateException e){
            return "wrong_lab_status_change";
        }
    }



}
