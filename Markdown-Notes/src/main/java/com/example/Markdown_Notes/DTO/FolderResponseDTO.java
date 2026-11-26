package com.example.Markdown_Notes.DTO;

import java.time.LocalDateTime;

import com.example.Markdown_Notes.folder.Folder;

public record FolderResponseDTO(
    Long id,
    Long userId,
    String name,
    LocalDateTime createAt,
    LocalDateTime updatedAt
) {

    public static FolderResponseDTO fromEntity(Folder folder){
        return new FolderResponseDTO(
            folder.getId(),
            folder.getUser() != null ? folder.getUser().getUserId() : null,
            folder.getName(),
            folder.getCreatedAt(),
            folder.getUpdatedAt()
        );
    }

    
    
}
