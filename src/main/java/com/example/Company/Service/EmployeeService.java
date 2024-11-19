package com.example.Company.Service;

import com.example.Company.Entity.Employee;
import com.example.Company.Entity.User;
import com.example.Company.Repository.EmployeeRepository;
import com.example.Company.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class EmployeeService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    // Xodimlar ro'yxatini olish
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    // Xodimni ID orqali olish
    public Employee getEmployeeById(long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found with ID: " + id));
    }

    // Yangi xodim yaratish

    @Transactional
    public Employee createEmployee(Employee employee) {
        User user = employee.getUser(); // Extract the user from the employee

        if (user.getId() != 0) {
            // Check if the user exists in the database
            User finalUser = user;
            user = userRepository.findById(user.getId())
                    .orElseThrow(() -> new RuntimeException("User not found with ID: " + finalUser.getId()));
        } else {
            // For new users, encode the password
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }

        // Save or update the user
        user = userRepository.save(user);

        // Associate the managed user with the employee
        employee.setUser(user);

        // Save the employee
        return employeeRepository.save(employee);
    }


    // Xodim ma'lumotlarini yangilash
    public Employee updateEmployee(long id, Employee updatedEmployee) {
        // Mavjud xodimni topish
        Employee existingEmployee = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found with ID: " + id));

        // Ma'lumotlarni yangilash
        existingEmployee.setFirstName(updatedEmployee.getFirstName());
        existingEmployee.setLastName(updatedEmployee.getLastName());
        existingEmployee.setMiddleName(updatedEmployee.getMiddleName());
        existingEmployee.setAge(updatedEmployee.getAge());
        existingEmployee.setPassportNumber(updatedEmployee.getPassportNumber());
        existingEmployee.setJshshr(updatedEmployee.getJshshr());
        existingEmployee.setNationality(updatedEmployee.getNationality());
        existingEmployee.setSalary(updatedEmployee.getSalary());
        existingEmployee.setAddress(updatedEmployee.getAddress());
        existingEmployee.setDepartment(updatedEmployee.getDepartment());

        // Ensure the "user" is correctly linked
        if (existingEmployee.getUser() != null) {
            existingEmployee.getUser().setRoles(Set.of(User.Role.EMPLOYEE));
        } else {
            throw new RuntimeException("User not found for Employee with ID: " + id);
        }

        // Ma'lumotlarni saqlash
        return employeeRepository.save(existingEmployee);
    }


    public void deleteEmployee (long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found with ID: " + id));

        employeeRepository.delete(employee);
    }

    public List<Map<String , Object>> getDepartmentStatus(){
        List<Object[]> status = employeeRepository.countEmployeesByDepartment();
        List<Map<String , Object>> result = new ArrayList<>();

        long totalEmployees = status.stream().mapToLong(o -> (long) o[1]).sum();

        for (Object[] o : status){
            String department = (String) o[0];
            long count = (long) o[1];
            double percentage = (double) count / totalEmployees * 100;

            Map<String , Object> map = new HashMap<>();
            map.put("department", department);
            map.put("count", count);
            map.put("percentage", percentage);
            result.add(map);
        }
        return result;
    }

    public List<Employee> filterEmployeesByAge(int minAge, int maxAge){
        return employeeRepository.findEmployeesByAgeRange(minAge, maxAge);
    }



    public Double getTotalSalaries() {
        return employeeRepository.getTotalSalaries();
    }




















//    @Transactional
//    public void deleteEmployee(Long id) {
//        Employee employee = employeeRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("Employee not found with id: " + id));
//
//        // Check if there's an associated User and delete it if necessary
//        if (employee.getUser() != null) {
//            userRepository.deleteById(employee.getUser().getId());
//        }
//
//        // Delete the employee
//        employeeRepository.delete(employee);
//    }





}
