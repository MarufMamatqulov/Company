package com.example.Company.Cotroller;

import com.example.Company.Entity.Employee;
import com.example.Company.Entity.Expense;
import com.example.Company.Service.EmployeeService;
import com.example.Company.Service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {

    @Autowired
    private ExpenseService expenseService;

    @Autowired
    private EmployeeService employeeService;


    @PostMapping
    public Expense createExpense(@RequestBody Expense expense, @RequestParam Long employeeId) {
        Employee employee = employeeService.getEmployeeById(employeeId);
        return expenseService.saveExpense(expense,employee);
    }

    @PutMapping("/{id}")
    public Expense updateExpense(@PathVariable Long id, @RequestBody Expense expense) {
        return expenseService.updateExpense(id, expense);
    }

    @GetMapping
    public Page<Expense> getExpenses(@RequestParam(defaultValue = "0") int page,
                                     @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return expenseService.getExpenses(pageable);
    }

    @GetMapping("/{id}")
    public Expense getExpenseById(@PathVariable Long id) {
        return expenseService.getExpenseById(id);
    }

    @DeleteMapping("/{id}")
    public String deleteExpense(@PathVariable Long id) {
        expenseService.deleteExpense(id);
        return "Expense deleted";
    }

    // 1. Eng ko’p xarajat qilinadigan reklama turi
    @GetMapping("/most-expensive-type")
    public ResponseEntity<Object[]> getMostExpensiveAdType() {
        return ResponseEntity.ok(expenseService.getMostExpensiveAdType());
    }

    // 2. Eng ko’p reklama xarajatlarini kiritgan xodim
    @GetMapping("/top-spender-employee")
    public ResponseEntity<Object[]> getTopSpenderEmployee() {
        return ResponseEntity.ok(expenseService.getTopSpenderEmployee());
    }

    // 3. Oxirgi 1 oy ichida nechta reklama yo’lga qo’yilgan
    @GetMapping("/started-last-month")
    public ResponseEntity<Long> getStartedExpensesLastMonth() {
        return ResponseEntity.ok(expenseService.countStartedExpensesLastMonth());
    }

    // 4. Oxirgi 1 oy ichida nechta reklama to’xtagan
    @GetMapping("/stopped-last-month")
    public ResponseEntity<Long> getStoppedExpensesLastMonth() {
        return ResponseEntity.ok(expenseService.countStoppedExpensesLastMonth());
    }

    // 5. Har bir reklama turiga nechtadan xarajat to’g’ri keladi
    @GetMapping("/expenses-per-type")
    public ResponseEntity<List<Object[]>> getExpensesPerAdType() {
        return ResponseEntity.ok(expenseService.getExpensesPerAdType());
    }

}
