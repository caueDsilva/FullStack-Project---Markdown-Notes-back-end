package com.example.Markdown_Notes.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record FolderUpdateRequest(@NotBlank 
    @NotNull String name
) {}
