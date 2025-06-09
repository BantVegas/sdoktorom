package com.bantvegas.sdoktorom.controller;

import com.bantvegas.sdoktorom.dto.UserRegisterDto;
import com.bantvegas.sdoktorom.model.User;
import com.bantvegas.sdoktorom.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserRegisterDto dto) {
        User newUser = userService.registerUser(dto.getEmail(), dto.getPassword(), dto.getRole());
        return ResponseEntity.ok(newUser);
    }
}
