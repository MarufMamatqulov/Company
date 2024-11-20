package com.example.Company.Config;

import com.example.Company.Service.LoggingService;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Arrays;
import java.util.stream.Collectors;

@Aspect
@Component
public class LoggingAspect {

    private final LoggingService loggingService;

    public LoggingAspect(LoggingService loggingService) {
        this.loggingService = loggingService;
    }

    @Around("execution(* com.example.Company.Cotroller..*(..))") // Controller metodlariga ishlov berish
    public Object logControllerMethods(ProceedingJoinPoint joinPoint) throws Throwable {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes == null) return joinPoint.proceed();

        HttpServletRequest request = attributes.getRequest();

        String username = request.getUserPrincipal() != null ? request.getUserPrincipal().getName() : "Anonymous";
        String method = request.getMethod();
        String endpoint = request.getRequestURI();

        String operation = determineCrudOperation(method);
        String tableName = joinPoint.getSignature().getDeclaringTypeName(); // Jadval nomi sifatida sinf nomi
        String data = getRequestBody(joinPoint);

        Object result = joinPoint.proceed(); // Metodni davom ettiring

        loggingService.logOperation(username, tableName, operation, method, endpoint, data);

        return result;
    }

    private String determineCrudOperation(String method) {
        return switch (method) {
            case "POST" -> "CREATE";
            case "GET" -> "READ";
            case "PUT" -> "UPDATE";
            case "DELETE" -> "DELETE";
            default -> "UNKNOWN";
        };
    }

    private String getRequestBody(ProceedingJoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        return Arrays.stream(args)
                .map(arg -> arg != null ? arg.toString() : "null")
                .collect(Collectors.joining(", "));
    }
}