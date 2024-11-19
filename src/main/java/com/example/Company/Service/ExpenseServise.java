package com.example.Company.Service;

import com.example.Company.Entity.Employee;
import com.example.Company.Entity.Expense;
import com.example.Company.Repository.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class ExpenseServise {

    @Autowired
    private ExpenseRepository expenseRepository;



    public Expense saveExpense(Expense expense, Employee employee) {
        expense.setStartedAt(LocalDateTime.now());

        expense.setCreatedBy(employee);
        return expenseRepository.save(expense);
    }

    public Page<Expense> getExpenses(Pageable pageable) {
        return expenseRepository.findAll(pageable);
    }

    public Expense updateExpense(Long id, Expense updatedExpense) {
        if (updatedExpense.getStartedAt() == null) {
            updatedExpense.setStartedAt(LocalDateTime.now());  // Agar startedAt bo‘sh bo‘lsa, hozirgi vaqtni o‘rnatamiz
        }
        return expenseRepository.findById(id)
                .map(existingExpense -> {
                    // Faqat ma’lum maydonlarni yangilashga ruxsat
                    existingExpense.setAdType(updatedExpense.getAdType());
                    existingExpense.setCost(updatedExpense.getCost());
                    existingExpense.setDuration(updatedExpense.getDuration());
                    existingExpense.setStartedAt(updatedExpense.getStartedAt());
                    return expenseRepository.save(existingExpense);
                }).orElseThrow(() -> new RuntimeException("Expense not found"));
    }

    public Expense getExpenseById(Long id) {
        return expenseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Expense not found"));
    }


    public void deleteExpense(Long id) {
        expenseRepository.deleteById(id);
    }





}
