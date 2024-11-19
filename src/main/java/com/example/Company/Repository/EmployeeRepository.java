package com.example.Company.Repository;

import com.example.Company.Entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    @Query("SELECT e.department, COUNT(e) FROM Employee e GROUP BY e.department")
    List<Object[]> countEmployeesByDepartment();

    @Query("SELECT e FROM Employee e WHERE e.age BETWEEN :minAge AND :maxAge")
    List<Employee> findEmployeesByAgeRange(@Param("minAge") int minAge, @Param("maxAge") int maxAge);

    Page<Employee> findAll(Pageable pageable);

    @Query("SELECT SUM(e.salary) FROM Employee e")
    Double getTotalSalaries();



}
