package com.bantvegas.sdoktorom.service;

import com.bantvegas.sdoktorom.model.User;
import com.bantvegas.sdoktorom.model.Role;
import com.bantvegas.sdoktorom.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User registerUser(String email, String password, String role) {
        User user = new User(email, password, Role.valueOf(role));
        return userRepository.save(user);
    }
}
