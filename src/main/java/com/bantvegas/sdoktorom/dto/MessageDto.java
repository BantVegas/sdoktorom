package com.bantvegas.sdoktorom.dto;

import java.time.LocalDateTime;

public class MessageDto {

    private Long id;
    private Long doctorId;
    private Long patientId;
    private String symptoms;
    private String recommendation;
    private LocalDateTime createdAt;
    private Boolean viewedByDoctor;
    private Boolean sentToDoctor;

    // Gettery a settery

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
    }

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public String getSymptoms() {
        return symptoms;
    }

    public void setSymptoms(String symptoms) {
        this.symptoms = symptoms;
    }

    public String getRecommendation() {
        return recommendation;
    }

    public void setRecommendation(String recommendation) {
        this.recommendation = recommendation;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Boolean getViewedByDoctor() {
        return viewedByDoctor;
    }

    public void setViewedByDoctor(Boolean viewedByDoctor) {
        this.viewedByDoctor = viewedByDoctor;
    }

    public Boolean getSentToDoctor() {
        return sentToDoctor;
    }

    public void setSentToDoctor(Boolean sentToDoctor) {
        this.sentToDoctor = sentToDoctor;
    }
}
