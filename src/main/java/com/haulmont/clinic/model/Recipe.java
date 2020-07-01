package com.haulmont.clinic.model;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "recipes")
public class Recipe {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "description")
    private String description;

    @OneToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @OneToOne
    @JoinColumn(name = "doctor_id", nullable = false)
    private Doctor doctor;

    @Column(name = "creation_date", columnDefinition = "TIMESTAMP")
    private LocalDateTime creationDate;

    @Column(name = "validity")
    private Integer validity;

    @Column(name = "priority")
    private String priority;

    public Recipe() {
    }

    public Recipe(Long id, String description, Patient patient, Doctor doctor, LocalDateTime creationDate, Integer validity, String priority) {
        this.id = id;
        this.description = description;
        this.patient = patient;
        this.doctor = doctor;
        this.creationDate = creationDate;
        this.validity = validity;
        this.priority = priority;
    }

    public Recipe(String description, Patient patient, Doctor doctor, LocalDateTime creationDate, Integer validity, String priority) {
        this.description = description;
        this.patient = patient;
        this.doctor = doctor;
        this.creationDate = creationDate;
        this.validity = validity;
        this.priority = priority;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public Integer getValidity() {
        return validity;
    }

    public void setValidity(Integer validity) {
        this.validity = validity;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getDoctorFullName(){
        return doctor.getFullName();
    }

    public String getPatientFullName(){
        return patient.getFullName();
    }

    public String getCreationDateString(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm MM.dd.yyyy");
        return creationDate.format(formatter);
    }
}
