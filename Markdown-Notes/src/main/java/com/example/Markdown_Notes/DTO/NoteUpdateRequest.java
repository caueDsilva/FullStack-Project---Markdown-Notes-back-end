package com.example.Markdown_Notes.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


public record NoteUpdateRequest(
    @NotBlank 
    @NotNull String content,
    Long folderId,
    @NotNull String title,
    Boolean favorite,
    Boolean archived
) {
}