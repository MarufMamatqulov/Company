package com.example.Company.Cotroller;

import com.example.Company.Entity.Employee;
import com.example.Company.Repository.EmployeeRepository;
import com.example.Company.Service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private EmployeeRepository employeeRepository;

    @GetMapping
//    @PreAuthorize("hasRole('DIRECTOR') or hasRole('DEPARTMENT_HEAD')")
    public List<Employee> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    @GetMapping("/{id}")
//    @PreAuthorize("hasRole('DIRECTOR') or hasRole('DEPARTMENT_HEAD')")
    public Employee getEmployeeById(@PathVariable long id) {
        return employeeService.getEmployeeById(id);
    }

    @PostMapping
//    @PreAuthorize("hasRole('DIRECTOR') or hasRole('DEPARTMENT_HEAD')")
    public Employee createEmployee(@RequestBody Employee employee) {
        return employeeService.createEmployee(employee);
    }

    @PutMapping("/{id}")
//    @PreAuthorize("hasRole('DIRECTOR') or hasRole('DEPARTMENT_HEAD') or hasRole('EMPLOYEE')")
    public Employee updateEmployee(@PathVariable long id, @RequestBody Employee employee) {
        return employeeService.updateEmployee(id, employee);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('DIRECTOR')")
    public ResponseEntity<?> deleteEmployee(@PathVariable long id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity.ok("Employee deleted successfully");
    }

    @GetMapping("/department-status")
    public ResponseEntity<List<Map<String, Object>>> getDepartmentStatus() {
        return ResponseEntity.ok(employeeService.getDepartmentStatus());
    }

    @GetMapping("/list")
    public ResponseEntity<Page<Employee>> getAllEmployees(@RequestParam(defaultValue = "0") int page,
                                                          @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(employeeRepository.findAll(pageable));
    }

    @GetMapping("/filter-by-age")
    public ResponseEntity<List<Employee>> filterEmployeeByAge(
            @RequestParam int minAge, @RequestParam int maxAge) {

        List<Employee> employees = employeeService.filterEmployeesByAge(minAge,maxAge);
        return ResponseEntity.ok(employees);
    }

    @GetMapping("/total-salaries")
    public ResponseEntity<Double> getTotalSalaries() {
        Double totalSalaries = employeeService.getTotalSalaries();
        return ResponseEntity.ok(totalSalaries);
    }



}

