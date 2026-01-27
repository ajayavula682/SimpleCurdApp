package com.example.simplecurdapp.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class EntryPointOne {

    @GetMapping("/hello/v1")
    public String helloV1() {
        return "Hello from SimpleCrudApp v1! API is running successfully.";
    }

    @GetMapping("/info")
    public Map<String, Object> getApiInfo() {
        Map<String, Object> info = new HashMap<>();
        info.put("application", "SimpleCrudApp");
        info.put("version", "1.0.0");
        info.put("description", "A complete CRUD application with User and Product management");
        info.put("timestamp", LocalDateTime.now());
        info.put("endpoints", Map.of(
            "users", "/api/users",
            "products", "/api/products",
            "h2-console", "/h2-console"
        ));
        return info;
    }

    @GetMapping("/health")
    public Map<String, String> healthCheck() {
        Map<String, String> health = new HashMap<>();
        health.put("status", "UP");
        health.put("timestamp", LocalDateTime.now().toString());
        return health;
    }
}
