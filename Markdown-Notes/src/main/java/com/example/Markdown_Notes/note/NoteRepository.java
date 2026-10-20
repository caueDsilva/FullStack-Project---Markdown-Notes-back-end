package com.example.Markdown_Notes.note;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.Markdown_Notes.DTO.NoteUpdateRequest;

public interface NoteRepository extends JpaRepository<Note, Long> {
    
    Optional<Note> findByIdAndUserId(Long id, Long userId);

    Optional<Note> updateByIdAndUserId(Long id, Long userId, NoteUpdateRequest noteUpdateRequest);

    Optional<Note> deleteByIdAndUserId(Long id, Long userId);


    
}
