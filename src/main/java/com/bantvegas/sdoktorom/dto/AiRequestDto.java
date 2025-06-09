package com.bantvegas.sdoktorom.dto;

public class AiRequestDto {
    private String symptoms; // textový popis príznakov

    public AiRequestDto() {}

    public AiRequestDto(String symptoms) {
        this.symptoms = symptoms;
    }

    public String getSymptoms() {
        return symptoms;
    }

    public void setSymptoms(String symptoms) {
        this.symptoms = symptoms;
    }
}
