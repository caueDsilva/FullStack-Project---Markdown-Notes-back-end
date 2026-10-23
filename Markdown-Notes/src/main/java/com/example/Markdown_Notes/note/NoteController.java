package com.example.Markdown_Notes.note;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Markdown_Notes.DTO.NoteCreateRequest;
import com.example.Markdown_Notes.DTO.NoteResponseDTO;
import com.example.Markdown_Notes.DTO.NoteUpdateRequest;
import com.example.Markdown_Notes.user.User;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController 
@RequestMapping ({"api/note", "/note"})
@RequiredArgsConstructor 
public class NoteController {

    private final NoteService noteService;


    @GetMapping()
    public ResponseEntity<List<NoteResponseDTO>> findAll(@AuthenticationPrincipal User user) {
        List<NoteResponseDTO> notes = noteService.findByUserId(user.getId()).stream()
        .map(NoteResponseDTO::fromEntity).toList();

        return ResponseEntity.ok(notes);
    }

    @PostMapping 
    public ResponseEntity<NoteResponseDTO> createNote(@AuthenticationPrincipal User user, @Valid @RequestBody NoteCreateRequest request){
        Note note = noteService.createNote(user.getId(), request);

        return ResponseEntity.status(HttpStatus.CREATED).body(NoteResponseDTO.fromEntity(note));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<NoteResponseDTO> updateNote(@AuthenticationPrincipal User user, @PathVariable long id, @Valid @RequestBody NoteUpdateRequest request){
        Note update = noteService.updateByIdAndUserId(id, user.getId(), request);
        return ResponseEntity.ok(NoteResponseDTO.fromEntity(update));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNote(@AuthenticationPrincipal User user, @PathVariable long id){
        noteService.deleteNote(id,user.getId());

        return ResponseEntity.noContent().build();
    }
}
