package com.example.Markdown_Notes.exception;

import java.time.LocalDateTime;
import java.util.Map;

public record ValidationErrorResponseDTO(
    int status,
    String error,
    String message,
    Map<String, String> fields,
    LocalDateTime timestamp
) {
    public ValidationErrorResponseDTO(int status, String error, String message, Map<String, String> fields) {
        this(status, error, message, fields, LocalDateTime.now());
    }
}
