package com.example.Company.Service;

import com.example.Company.Entity.Employee;
import com.example.Company.Entity.User;
import com.example.Company.Repository.EmployeeRepository;
import com.example.Company.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

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
    public Employee createEmployee(Employee employee) {
        if (userRepository.existsByUsername(employee.getUser().getUsername())) {
            throw new RuntimeException("Username already exists");
        }

        // Xodimga "EMPLOYEE" rolini berish
        employee.getUser().setRoles(Set.of(User.Role.EMPLOYEE));

        // Parolni shifrlash
        employee.getUser().setPassword(passwordEncoder.encode(employee.getUser().getPassword()));

        // Xodimni saqlash
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

        // Xodimga doimiy "EMPLOYEE" rolini saqlash
        existingEmployee.getUser().setRoles(Set.of(User.Role.EMPLOYEE));

        // Ma'lumotlarni saqlash
        return employeeRepository.save(existingEmployee);
    }

    // Xodimni o'chirish
    public void deleteEmployee(long id) {
        if (!employeeRepository.existsById(id)) {
            throw new RuntimeException("Employee not found with ID: " + id);
        }
        employeeRepository.deleteById(id);
    }


}
