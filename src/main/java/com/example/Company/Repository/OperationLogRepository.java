package com.example.Company.Repository;

import com.example.Company.Entity.OperationLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OperationLogRepository extends JpaRepository<OperationLog, Long> {
    @Query("SELECT l FROM OperationLog l WHERE l.username = :username")
    List<OperationLog> findByUsername(@Param("username") String username);
}
