package com.example.Company.Repository;

import com.example.Company.Entity.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    @Query("SELECT e.adType, SUM(e.cost) AS totalCost FROM Expense e GROUP BY e.adType ORDER BY totalCost DESC")
    List<Object[]> findMostExpensiveAdType();

    @Query("SELECT e.createdBy.id, e.createdBy.firstName, SUM(e.cost) AS totalCost FROM Expense e GROUP BY e.createdBy.id, e.createdBy.firstName ORDER BY totalCost DESC")
    List<Object[]> findTopSpenderEmployee();

    @Query("SELECT COUNT(e) FROM Expense e WHERE e.startedAt >= :startDate AND e.startedAt <= :endDate")
    Long countStartedExpenses(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    @Query("SELECT COUNT(e) FROM Expense e WHERE e.startedAt <= :endDate AND e.startedAt >= :startDate AND e.duration < 30")
    Long countStoppedExpenses(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    @Query("SELECT e.adType, COUNT(e) FROM Expense e GROUP BY e.adType")
    List<Object[]> countExpensesPerAdType();


}
