package com.example.diagnostic.services;

import com.example.diagnostic.entities.Lab;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.diagnostic.repositories.LabRepository;

@Service
public class LabService {
    private final LabRepository labRepository;
    @PersistenceContext
    EntityManager entityManager;


    @Autowired
    public LabService(LabRepository labRepository) {
        this.labRepository = labRepository;
    }

    public void updateLabStatus(Integer labId) {
        Lab lab = labRepository.findById(Long.valueOf(labId)).orElseThrow(() -> new IllegalStateException(
                "Lab with id " + labId + " does not exist"));
        if(lab.getLabStatus().equals(Lab.LabStatus.operational)){
            lab.setLabStatus(Lab.LabStatus.non_operational);
        }else {
            lab.setLabStatus(Lab.LabStatus.operational);
        }
    }

    public Lab getLabById(int labId) {
        return labRepository.getById(Long.valueOf(labId));
    }

}
