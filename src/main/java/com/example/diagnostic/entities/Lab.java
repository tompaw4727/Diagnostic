package com.example.diagnostic.entities;

import jakarta.persistence.*;

@Entity
public class Lab {
    public enum LabStatus {
        operational,
        non_operational
    }

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Integer id;
    @Basic
    @Enumerated(EnumType.STRING)
    private LabStatus labStatus;
    @Column(name = "technic_id")
    private Integer technicId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Object getLabStatus() {
        return labStatus;
    }

    public void setLabStatus(LabStatus labStatus) {
        this.labStatus = labStatus;
    }

    public Integer getTechnicId() {
        return technicId;
    }

    public void setTechnicId(Integer technicId) {
        this.technicId = technicId;
    }

    @Override
    public String toString() {
        return "Lab{" +
                "id=" + id +
                ", labStatus=" + labStatus +
                '}';
    }

}
