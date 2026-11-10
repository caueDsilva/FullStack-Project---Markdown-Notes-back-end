package com.example.Markdown_Notes.DTO;

import jakarta.validation.constraints.NotBlank;

public record UserCreateResquest(
    @NotBlank String name, 
    @NotBlank String email,
    @NotBlank String password,
    Long userId
) {
    
}
