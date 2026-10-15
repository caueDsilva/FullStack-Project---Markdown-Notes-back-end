package com.example.Markdown_Notes.DTO;

import java.time.LocalDateTime;

import com.example.Markdown_Notes.user.User;

public record UserResponseDTO(
    Long id,
    String name,
    String email,
    String role,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {
    public static UserResponseDTO fromEntity(User user) {
        return new UserResponseDTO(
            user.getId(),
            user.getName(),
            user.getEmail(),
            user.getRole(),
            user.getCreatedAt(),
            user.getUpdatedAt()
        );
    }
}
