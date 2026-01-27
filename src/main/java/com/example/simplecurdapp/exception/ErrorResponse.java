package com.example.simplecurdapp.exception;

import java.time.LocalDateTime;

public class ErrorResponse {
    private String message;
    private String details;
    private LocalDateTime timestamp;
    private int status;
    private String path;

    public ErrorResponse() {}

    public ErrorResponse(String message, String details, LocalDateTime timestamp, int status, String path) {
        this.message = message;
        this.details = details;
        this.timestamp = timestamp;
        this.status = status;
        this.path = path;
    }

    public ErrorResponse(String message, String details, int status, String path) {
        this.message = message;
        this.details = details;
        this.status = status;
        this.path = path;
        this.timestamp = LocalDateTime.now();
    }

    // Getters and Setters
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public String getDetails() { return details; }
    public void setDetails(String details) { this.details = details; }

    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }

    public int getStatus() { return status; }
    public void setStatus(int status) { this.status = status; }

    public String getPath() { return path; }
    public void setPath(String path) { this.path = path; }
}
