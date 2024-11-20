package com.example.Company.Service;

import com.example.Company.Entity.User;
import com.example.Company.Repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import java.util.Set;

@Service
public class DataLoader {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataLoader(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    public void loadInitialData() {
        // 1. Director User
        if (!userRepository.existsByUsername("director3")) {
            User directorUser = new User();
            directorUser.setUsername("director3");
            directorUser.setPassword(passwordEncoder.encode("director123"));
            directorUser.setRoles(Set.of(User.Role.DIRECTOR));
            userRepository.save(directorUser);
        }

        // 2. Department Head User
        if (!userRepository.existsByUsername("depthead3")) {
            User deptHeadUser = new User();
            deptHeadUser.setUsername("depthead3");
            deptHeadUser.setPassword(passwordEncoder.encode("depthead123"));
            deptHeadUser.setRoles(Set.of(User.Role.DEPARTMENT_HEAD));
            userRepository.save(deptHeadUser);
        }

        // 3. Regular Employee User
        if (!userRepository.existsByUsername("employee3")) {
            User employeeUser = new User();
            employeeUser.setUsername("employee3");
            employeeUser.setPassword(passwordEncoder.encode("employee123"));
            employeeUser.setRoles(Set.of(User.Role.EMPLOYEE));
            userRepository.save(employeeUser);
        }
    }
}
