package com.example.Markdown_Notes.note;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.Markdown_Notes.DTO.NoteCreateRequest;
import com.example.Markdown_Notes.DTO.NoteUpdateRequest;
import com.example.Markdown_Notes.user.User;

import jakarta.transaction.Transactional;

public interface NoteRepository extends JpaRepository<Note, Long> {
    
    List<Note> findByUserId(Long userId);

    Optional<Note> findByIdAndUserId(Long id, Long userId);

    Optional<Note> updateByIdAndUserId(Long id, Long userId, NoteUpdateRequest noteUpdateRequest);

    Note CreateNote(User user, NoteCreateRequest request);

    @Transactional
    void deleteByIdAndUserId(Long id, Long userId);


    
}
