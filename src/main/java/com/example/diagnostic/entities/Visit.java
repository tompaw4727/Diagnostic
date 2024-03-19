package com.example.diagnostic.entities;

import jakarta.persistence.*;

import java.sql.Date;

@Entity
public class Visit {
    public enum Referral {
        YES,
        NO
    }
    public enum Tour {
        I,
        II,
        III,
        IV,
        V
    }
    public enum VisitStatus {
        completed,
        not_completed
    }
    public enum ResultStatus {
        available,
        unavailable
    }


    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "test_id")
    private Integer testId;
    @Basic
    @Column(name = "lab_id")
    private Integer labId;
    @Basic
    @Column(name = "patient_email")
    private String patientEmail;

    @Basic
    @Column(name = "visit_date")
    private Date visitDate;
    @Basic
    @Enumerated(EnumType.STRING)
    @Column(name = "tour")
    private Tour tour;
    @Basic
    @Enumerated(EnumType.STRING)
    @Column(name = "visit_status")
    private VisitStatus visitStatus;
    @Basic
    @Enumerated(EnumType.STRING)
    @Column(name = "results_status")
    private ResultStatus resultsStatus;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getTestId() {
        return testId;
    }

    public void setTestId(Integer testId) {
        this.testId = testId;
    }

    public Integer getLabId() {
        return labId;
    }

    public void setLabId(Integer labId) {
        this.labId = labId;
    }

    public String getPatientEmail() {
        return patientEmail;
    }

    public void setPatientEmail(String patientEmail) {
        this.patientEmail = patientEmail;
    }

//    public Referral getReferral() {
//        return referral;
//    }

//    public void setReferral(Referral referral) {
//        this.referral = referral;
//    }

    public Date getVisitDate() {
        return visitDate;
    }

    public void setVisitDate(Date visitDate) {
        this.visitDate = visitDate;
    }

    public Tour getTour() {
        return tour;
    }

    public void setTour(Tour tour) {
        this.tour = tour;
    }

    public VisitStatus getVisitStatus() {
        return visitStatus;
    }

    public void setVisitStatus(VisitStatus visitStatus) {
        this.visitStatus = visitStatus;
    }

    public ResultStatus getResultsStatus() {
        return resultsStatus;
    }

    public void setResultsStatus(ResultStatus resultsStatus) {
        this.resultsStatus = resultsStatus;
    }

    @Override
    public String toString() {
        return "Visit{" +
                "id=" + id +
                ", testId=" + testId +
                ", labId=" + labId +
                ", patientEmail='" + patientEmail + '\'' +
//                ", referral=" + referral +
                ", visitDate=" + visitDate +
                ", tour=" + tour +
                ", visitStatus=" + visitStatus +
                ", resultsStatus=" + resultsStatus +
                '}';
    }

}
