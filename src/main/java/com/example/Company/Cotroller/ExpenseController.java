package com.example.Company.Cotroller;

import com.example.Company.Entity.Employee;
import com.example.Company.Entity.Expense;
import com.example.Company.Service.EmployeeService;
import com.example.Company.Service.ExpenseServise;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {

    @Autowired
    private ExpenseServise expenseServise;

    @Autowired
    private EmployeeService employeeService;


    @PostMapping
    public Expense createExpense(@RequestBody Expense expense, @RequestParam Long employeeId) {
        Employee employee = employeeService.getEmployeeById(employeeId);
        return expenseServise.saveExpense(expense,employee);
    }

    @PutMapping("/{id}")
    public Expense updateExpense(@PathVariable Long id, @RequestBody Expense expense) {
        return expenseServise.updateExpense(id, expense);
    }

    @GetMapping
    public Page<Expense> getExpenses(@RequestParam(defaultValue = "0") int page,
                                     @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return expenseServise.getExpenses(pageable);
    }

    @GetMapping("/{id}")
    public Expense getExpenseById(@PathVariable Long id) {
        return expenseServise.getExpenseById(id);
    }

    @DeleteMapping("/{id}")
    public String deleteExpense(@PathVariable Long id) {
        expenseServise.deleteExpense(id);
        return "Expense deleted";
    }

}
