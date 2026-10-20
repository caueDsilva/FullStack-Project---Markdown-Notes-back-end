package com.example.Markdown_Notes.note;

import java.util.Optional;
import org.springframework.stereotype.Service;

import com.example.Markdown_Notes.DTO.NoteUpdateRequest;
import com.example.Markdown_Notes.exception.ResourceNotFoundException;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NoteService {
    
    private final NoteRepository noteRepository;
    //private final FolderRepository folderRepository;

    public NoteService(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    public Note findByIdAndUserId(Long id, Long userId) {
    return noteRepository.findByIdAndUserId(id, userId)
            .orElseThrow(() -> new ResourceNotFoundException(
                    "Nota não encontrada " + id + " do usuário " + userId));
    }

    @Transactional
    public Optional<Note> updateByIdAndUserId(Long id, Long userId, NoteUpdateRequest noteUpdateRequest) {
        Note note = findByIdAndUserId(id, userId);
        
        note.setTitle(noteUpdateRequest.title());
        note.setContent(noteUpdateRequest.content());
        note.setIsFavorite(noteUpdateRequest.favorite());
        note.setIsArchived(noteUpdateRequest.archived());

        if(noteUpdateRequest.folderId() != null){
            Folder folder = folderRepository.findByIdUserId(noteUpdateRequest.folderId(), userId);
            note.setFolder(folder)
        } else {
            note.setFolder(null);
        }

        return noteRepository.save(note);
    }

    public Optional<Note> deleteByIdAndUserId(Long id, Long userId) {
        return noteRepository.deleteByIdAndUserId(id, userId);
    }

}