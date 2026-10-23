package com.example.Markdown_Notes.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record NoteCreateRequest(
    @NotBlank @Size(max = 255) String title,
    @NotNull String content,
    Long folderId) {
    
}
