package com.example.diagnostic.repositories;


import com.example.diagnostic.entities.Visit;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

//public interface VisitRepository extends JpaRepository<Visit, Long> {
//    @Autowired
//     private EntityManager entityManager; /// To do tego uzywania zpaytan sql
//
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
//
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
//    public default List<Visit> getVisitsByDay(Date visitsDate) {
//        var queryString = "SELECT v FROM Visit v WHERE v.visitDate = :visitDate";
//        TypedQuery<Visit> query = entityManager.createQuery(queryString, Visit.class);
//        query.setParameter("visitDate", visitsDate);
//        List<Visit> result = (List<Visit>) query.getResultList();
//
//        return result;
//    }
//
//    public default List<Visit.Tour> getAvailableToursInDay(Date visitsDate, int labId) {
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
//
//}

@Repository
public interface VisitRepository extends JpaRepository<Visit, Long> {

    @Query("SELECT CASE WHEN COUNT(v) > 0 THEN true ELSE false END FROM Visit v WHERE v.visitDate = :visitDate AND v.tour = :tour AND v.labId = :labId")
    boolean isTourOccupied(@Param("visitDate") Date visitDate, @Param("tour") Visit.Tour tour, @Param("labId") Integer labId);

    @Query("SELECT CASE WHEN COUNT(v) > 0 THEN true ELSE false END FROM Visit v WHERE v.patientEmail = :patientEmail AND v.visitDate = :visitDate AND v.testId = :testId")
    boolean isSamePersonTestInDay(@Param("patientEmail") String patientEmail, @Param("visitDate") Date visitDate, @Param("testId") Long testId);

    @Query("SELECT v FROM Visit v WHERE v.visitDate = :visitDate")
    List<Visit> getVisitsByDay(@Param("visitDate") Date visitDate);

    @Query("SELECT v.tour FROM Visit v WHERE v.visitDate = :visitDate AND v.labId = :labId")
    List<Visit.Tour> getAvailableToursInDay(@Param("visitDate") Date visitDate, @Param("labId") int labId);
}
