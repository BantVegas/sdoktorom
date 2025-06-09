package com.bantvegas.sdoktorom.dto;

public class UserRegisterDto {
    private String email;
    private String password;
    private String role; // "PACIENT" alebo "DOKTOR"

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
}
