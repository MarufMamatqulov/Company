//package com.example.Company.Config;
//
//import com.example.Company.Entity.Employee;
//import com.example.Company.Entity.User;
//import com.example.Company.Repository.EmployeeRepository;
//import com.example.Company.Repository.UserRepository;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.context.annotation.Bean;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Component;
//
//import java.util.Set;
//@Deprecated
//@Component
//public class DataLoader {
//
//    @Bean
//    CommandLineRunner run(UserRepository userRepository, EmployeeRepository employeeRepository, PasswordEncoder passwordEncoder) {
//        return args -> {
//            // Check and create Director
//            if (!userRepository.existsByUsername("director")) {
//                User directorUser = new User();
//                directorUser.setUsername("director");
//                directorUser.setPassword(passwordEncoder.encode("password"));
//                directorUser.setRoles(Set.of(User.Role.DIRECTOR));
//
//                Employee director = new Employee();
//                director.setFirstName("John");
//                director.setLastName("Doe");
//                director.setJshshr("1234567890"); // Example JSHSHR
//                director.setPassportNumber("ABC12345"); // Example Passport Number
//                director.setUser(directorUser);
//
//                userRepository.save(directorUser);
//                employeeRepository.save(director);
//            }
//
//            // Check and create Department Head
//            if (!userRepository.existsByUsername("dept_head")) {
//                User deptHeadUser = new User();
//                deptHeadUser.setUsername("dept_head");
//                deptHeadUser.setPassword(passwordEncoder.encode("password"));
//                deptHeadUser.setRoles(Set.of(User.Role.DEPARTMENT_HEAD));
//
//                Employee deptHead = new Employee();
//                deptHead.setFirstName("Jane");
//                deptHead.setLastName("Smith");
//                deptHead.setJshshr("0987654321"); // Example JSHSHR
//                deptHead.setPassportNumber("DEF67890"); // Example Passport Number
//                deptHead.setUser(deptHeadUser);
//
//                userRepository.save(deptHeadUser);
//                employeeRepository.save(deptHead);
//            }
//        };
//    }
//
//
//
//}
//
