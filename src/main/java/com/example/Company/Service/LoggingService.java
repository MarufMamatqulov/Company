package com.example.Company.Service;

import com.example.Company.Entity.OperationLog;
import com.example.Company.Repository.OperationLogRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional
public class LoggingService {
    private final OperationLogRepository operationLogRepository;

    public LoggingService(OperationLogRepository operationLogRepository) {
        this.operationLogRepository = operationLogRepository;
    }

    public void logOperation(String username, String tableName, String operation, String method, String endpoint, String data) {
        OperationLog log = new OperationLog();
        log.setUsername(username);
        log.setTableName(tableName);
        log.setOperation(operation);
        log.setMethod(method);
        log.setEndpoint(endpoint);
        log.setTimestamp(LocalDateTime.now());
        log.setData(data);

        operationLogRepository.save(log);
    }
}

