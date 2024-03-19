package com.example.diagnostic.services;

import com.example.diagnostic.entities.Lab;
import com.example.diagnostic.entities.Visit;
import com.example.diagnostic.repositories.LabRepository;
import jakarta.persistence.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.diagnostic.repositories.VisitRepository;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class VisitService {
    private final VisitRepository visitRepository;

    private final LabRepository labRepository;

    @Autowired
    public VisitService(VisitRepository visitRepository, LabRepository labRepository) {
        this.visitRepository = visitRepository;
        this.labRepository = labRepository;
    }

    public List<Visit> getVisits() {
        return visitRepository.findAll();
    }

    public void deleteVisit(int visitId) {
        boolean exists = visitRepository.existsById(Long.valueOf(visitId));
        if (!exists) {
            throw new IllegalStateException(
                    "visit with id " + visitId + " does not exist");
        }
        visitRepository.deleteById(Long.valueOf(visitId));
    }

    public void addNewVisit( Visit visit) {
        if (visitRepository.isSamePersonTestInDay(visit.getPatientEmail(), visit.getVisitDate(), Long.valueOf(visit.getTestId()))){
            throw new IllegalStateException("One person can have only one the same test in day");
        }
        if (visitRepository.isTourOccupied(visit.getVisitDate(), visit.getTour(), visit.getLabId())) {
            throw new IllegalStateException("This exact tour is taken");
        }
        if (!isLabOperational(visit.getLabId())) {
            throw new IllegalStateException("Status of this lab is noon operational");
        }
        if (visit.getTestId() == null || visit.getLabId() == null || visit.getPatientEmail() == null ||
            visit.getVisitDate() == null || visit.getTour() == null || visit.getPatientEmail().length() == 0 ){
            throw new IllegalStateException("All field are required");
        }

        LocalDate visitDataLocal = visit.getVisitDate().toLocalDate();
        LocalDate currentDate = LocalDate.now();

        if(visitDataLocal.isBefore(currentDate.minusDays(1))) {
            throw new IllegalArgumentException("The provided date is earlier than today.");
        }

        String emailRegex = "^(.+)@(.+)$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(visit.getPatientEmail());

        if (!matcher.matches()) {
            throw new IllegalArgumentException("Invalid email.");
        }


        visitRepository.save(visit);
    }


    public void updateVisit(int id, Integer testId, Integer labId, String patientEmail, Date visitDate, Visit.Tour tour) {
        Visit visit = visitRepository.findById(Long.valueOf(id)).orElseThrow(() -> new IllegalStateException(
                "Visit with id " + id + " does not exist."));

        if (testId != null && !Objects.equals(visit.getTestId(), testId)) {
            visit.setTestId(testId);
        }

        if (labId != null && !Objects.equals(visit.getLabId(), labId)) {
            if (!isLabOperational(labId)) {
                throw new IllegalStateException("Status of this lab is noon operational.");
            }
            visit.setLabId(labId);
        }

        if (patientEmail != null && patientEmail.length() > 0 && !Objects.equals(visit.getPatientEmail(), patientEmail)) {
            visit.setPatientEmail(patientEmail);
        }


        if (visitDate != null && !Objects.equals(visit.getVisitDate(), visitDate)) {
            if (visitRepository.isTourOccupied(visitDate, tour, labId)) {
                throw new IllegalStateException("This exact tour is taken.");
            }
            visit.setVisitDate(visitDate);
        }

        if (tour != null && !Objects.equals(visit.getTour(), tour)) {
            visit.setTour(tour);
            if (visitRepository.isTourOccupied(visitDate, tour, labId)) {
                throw new IllegalStateException("This exact tour is taken.");
            }
            visit.setTour(tour);
        }

        if (visit.getTestId() == null || visit.getLabId() == null || visit.getPatientEmail() == null ||
                visit.getVisitDate() == null || visit.getTour() == null || visit.getPatientEmail().length() == 0 ){
            throw new IllegalStateException("All field are required.");
        }

        LocalDate visitDataLocal = visitDate.toLocalDate();
        LocalDate currentDate = LocalDate.now();

        if(visitDataLocal.isBefore(currentDate.minusDays(1))) {
            throw new IllegalArgumentException("The provided date is earlier than today.");
        }

        String emailRegex = "^(.+)@(.+)$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(patientEmail);

        if (!matcher.matches()) {
            throw new IllegalArgumentException("Invalid email.");
        }

    }

//    private boolean isTourOccupied(Date visitDate, Visit.Tour tour, Integer labId) {
//
//
//        var queryString = "SELECT COUNT(v) from Visit v WHERE (v.visitDate = :visitDate AND v.tour = :tour AND v.labId = :labId)";
//        TypedQuery<Long> query = entityManager.createQuery(queryString, Long.class);
//        query.setParameter("visitDate", visitDate);
//        query.setParameter("tour", tour);
//        query.setParameter("labId", labId);
//
//        Long result = (Long) query.getSingleResult();
//
//        return result > 0;
//    }

//    private boolean isSamePersonTestInDay(Visit visit){
//        var queryString = "SELECT COUNT(v) from Visit v WHERE (v.patientEmail = :patientEmail AND  v.visitDate = :visitDate AND v.testId = :testId)";
//        TypedQuery<Long> query = entityManager.createQuery(queryString, Long.class);
//        query.setParameter("visitDate", visit.getVisitDate());
//        query.setParameter("testId", visit.getTestId());
//        query.setParameter("patientEmail", visit.getPatientEmail());
//        Long result = (Long) query.getSingleResult();
//
//        return result > 0;
//
//    }
//    public List<Visit> getVisitsByDay(Date visitsDate) {
//        var queryString = "SELECT v FROM Visit v WHERE v.visitDate = :visitDate";
//        TypedQuery<Visit> query = entityManager.createQuery(queryString, Visit.class);
//        query.setParameter("visitDate", visitsDate);
//        List<Visit> result = (List<Visit>) query.getResultList();
//
//        return result;
//    }

    public List<Visit> getVisitsByDay(Date visitsDate) {
        return visitRepository.getVisitsByDay(visitsDate);
    }

//    public List<Visit.Tour> getAvailableToursInDay(Date visitsDate, int labId) {
//        List<Visit.Tour> allTours = List.of(Visit.Tour.I, Visit.Tour.II, Visit.Tour.III, Visit.Tour.IV, Visit.Tour.V);
//
//        var queryString = "SELECT v.tour FROM Visit v WHERE v.visitDate = :visitsDate AND v.labId = :labId";
//        TypedQuery<Visit.Tour> query = entityManager.createQuery(queryString, Visit.Tour.class);
//        query.setParameter("visitsDate", visitsDate);
//        query.setParameter("labId", labId);
//        List<Visit.Tour> result = (List<Visit.Tour>) query.getResultList();
//
//        List<Visit.Tour> availableTours = new ArrayList<>(allTours);
//        availableTours.removeAll(result);
//
//        return availableTours;
//    }
    public List<Visit.Tour> getAvailableToursInDay(Date visitsDate, int labId) {
        return visitRepository.getAvailableToursInDay(visitsDate, labId);
    }

    public Visit getVisitById(int id) {
        return visitRepository.getById(Long.valueOf(id));
    }

    private boolean isLabOperational( Integer labId){
        Lab lab = labRepository.findById(Long.valueOf(labId)).orElse(null);
        boolean result =  lab != null && lab.getLabStatus() == Lab.LabStatus.operational;
        System.out.println(result);

        return result;

    }

    public void updateVisitStatus(int id) {
        Visit visit = visitRepository.findById(Long.valueOf(id)).orElseThrow(() -> new IllegalStateException(
                "Visit with id " + id + " does not exist"));
        if(visit.getVisitStatus() == Visit.VisitStatus.completed) {
            visit.setVisitStatus(Visit.VisitStatus.not_completed);
        }else {
            visit.setVisitStatus(Visit.VisitStatus.completed);
        }

    }

    public void updateVisitResultStatus(int id) {
        Visit visit = visitRepository.findById(Long.valueOf(id)).orElseThrow(() -> new IllegalStateException(
                "Visit with id " + id + " does not exist"));
        if(visit.getResultsStatus() == Visit.ResultStatus.available) {
            visit.setResultsStatus(Visit.ResultStatus.unavailable);
        }else {
            visit.setResultsStatus(Visit.ResultStatus.available);
        }
    }
}
