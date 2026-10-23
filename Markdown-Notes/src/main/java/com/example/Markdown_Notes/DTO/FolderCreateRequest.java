package com.example.Markdown_Notes.DTO;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record FolderCreateRequest(@NotNull @Size(max = 100) String name) {
}
