package com.example.Company.Cotroller;

import com.example.Company.Entity.OperationLog;
import com.example.Company.Repository.OperationLogRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/logs")
public class LogController {

    private final OperationLogRepository operationLogRepository;

    public LogController(OperationLogRepository operationLogRepository) {
        this.operationLogRepository = operationLogRepository;
    }

    // Loglarni olish (pagination bilan)
    @GetMapping
    public ResponseEntity<Page<OperationLog>> getAllLogs(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("timestamp").descending());
        Page<OperationLog> logs = operationLogRepository.findAll(pageable);
        return ResponseEntity.ok(logs);
    }

    @GetMapping("/user/{username}")
    public ResponseEntity<List<OperationLog>> getLogsByUser(@PathVariable String username) {
        List<OperationLog> logs = operationLogRepository.findByUsername(username);
        return ResponseEntity.ok(logs);
    }

}

