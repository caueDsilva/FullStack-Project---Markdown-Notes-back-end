package com.example.Markdown_Notes.note;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
public interface NoteRepository extends JpaRepository<Note, Long> {
    // user.userId (campo da entidade User referenciado via relacionamento user)
    List<Note> findByUserUserId(Long userId);
    Optional<Note> findByIdAndUserUserId(Long id, Long userId);
}