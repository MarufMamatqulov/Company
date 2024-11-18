package com.example.Company.Repository;

import com.example.Company.Entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClientRepository extends JpaRepository<Client, Long> {
    List<Client> findByCreatedByIdAndArchivedFalse(Long employeeId);
    List<Client> findByArchivedTrue();
}
