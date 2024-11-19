package com.example.Company.Repository;

import com.example.Company.Entity.Client;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface ClientRepository extends JpaRepository<Client, Long> {
    List<Client> findByCreatedByIdAndArchivedFalse(Long employeeId);
    List<Client> findByArchivedTrue();

    @Query("SELECT c.createdAt, COUNT(c) FROM Client c WHERE c.archived = false GROUP BY c.createdAt ORDER BY c.createdAt ASC")
    List<Object[]> findDailyRegisteredClients();

    @Query("SELECT c.createdBy.id, c.createdBy.firstName, c.createdBy.lastName, COUNT(c) AS clientCount " +
            "FROM Client c WHERE c.archived = false GROUP BY c.createdBy.id, c.createdBy.firstName, c.createdBy.lastName " +
            "ORDER BY clientCount DESC")
    List<Object[]> findTopRegistrarEmployee();

    @Query("SELECT c.createdBy.id, c.createdBy.firstName, c.createdBy.lastName, COUNT(c) AS clientCount " +
            "FROM Client c WHERE c.archived = false GROUP BY c.createdBy.id, c.createdBy.firstName, c.createdBy.lastName " +
            "ORDER BY clientCount DESC")
    List<Object[]> findTop3RegistrarEmployees(Pageable pageable);

    @Query("SELECT COUNT(c) FROM Client c WHERE c.archived = false AND c.createdAt >= :startDate AND c.createdAt <= :endDate")
    Long countClientsRegisteredLastMonth(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    @Query("SELECT c.createdAt, COUNT(c) AS clientCount FROM Client c " +
            "WHERE c.archived = false AND c.createdAt >= :startDate AND c.createdAt <= :endDate " +
            "GROUP BY c.createdAt ORDER BY clientCount DESC")
    List<Object[]> findMostActiveDayLastMonth(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);


}
