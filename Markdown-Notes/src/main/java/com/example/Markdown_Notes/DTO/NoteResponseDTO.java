package com.example.Markdown_Notes.DTO;
import java.time.LocalDateTime;

import com.example.Markdown_Notes.note.Note;

public record NoteResponseDTO(
    Long id,
    //Long folderId,
    String title,
    String content,
    String slug,
    boolean favorite,
    boolean archived,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {
    public static NoteResponseDTO fromEntity(Note note) {
        return new NoteResponseDTO(
            note.getId(),
            //note.getFolder() != null ? note.getFolder().getId() : null,
            note.getTitle(),
            note.getContent(),
            note.getSlug(),
            note.getIsFavorite(),
            note.getIsArchived(),
            note.getCreatedAt(),
            note.getUpdatedAt()
        );
    }
}