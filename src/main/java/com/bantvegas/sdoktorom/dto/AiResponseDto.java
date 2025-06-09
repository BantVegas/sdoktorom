// AiResponseDto.java
package com.bantvegas.sdoktorom.dto;

public class AiResponseDto {
    private String result;

    public AiResponseDto() {}
    public AiResponseDto(String result) {
        this.result = result;
    }
    public String getResult() {
        return result;
    }
    public void setResult(String result) {
        this.result = result;
    }
}
