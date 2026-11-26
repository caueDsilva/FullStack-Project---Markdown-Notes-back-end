package com.example.Markdown_Notes.note;

import java.util.List;
import org.springframework.stereotype.Service;

import com.example.Markdown_Notes.DTO.NoteCreateRequest;
import com.example.Markdown_Notes.DTO.NoteUpdateRequest;
import com.example.Markdown_Notes.exception.ResourceNotFoundException;
import com.example.Markdown_Notes.folder.Folder;
import com.example.Markdown_Notes.folder.FolderRepository;
import com.example.Markdown_Notes.user.User;
import com.example.Markdown_Notes.user.UserRepository;

import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NoteService {

    private final NoteRepository noteRepository;
    private final FolderRepository folderRepository;
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public List<Note> findByUserId(Long userId) {
        return noteRepository.findByUserUserId(userId);
    }

    @Transactional(readOnly = true)
    public Note findByIdAndUserId(Long id, Long userId) {
        return noteRepository.findByIdAndUserUserId(id, userId)
            .orElseThrow(() -> new ResourceNotFoundException("Nota não encontrada"));
    }

    @Transactional
    public Note updateByIdAndUserId(Long id, Long userId, NoteUpdateRequest noteUpdateRequest) {
        Note note = findByIdAndUserId(id, userId);

        note.setTitle(noteUpdateRequest.title());
        note.setContent(noteUpdateRequest.content());

        if (noteUpdateRequest.favorite() != null) {
            note.setIsFavorite(noteUpdateRequest.favorite());
        }
        if (noteUpdateRequest.archived() != null) {
            note.setIsArchived(noteUpdateRequest.archived());
        }

        if (noteUpdateRequest.folderId() != null) {
            Folder folder = folderRepository.findByIdAndUserUserId(noteUpdateRequest.folderId(), userId)
                .orElseThrow(() -> new ResourceNotFoundException("Pasta não encontrada"));
            note.setFolder(folder);
        } else {
            note.setFolder(null);
        }

        return noteRepository.save(note);
    }

    @Transactional
    public Note createNote(Long userId, NoteCreateRequest request) {
        User user = userRepository.getReferenceById(userId);

        Note note = new Note();
        note.setUser(user);
        note.setTitle(request.title());
        note.setContent(request.content());

        if (request.folderId() != null) {
            Folder folder = folderRepository.findByIdAndUserUserId(request.folderId(), userId)
                .orElseThrow(() -> new ResourceNotFoundException("Pasta não encontrada"));
            note.setFolder(folder);
        }

        return noteRepository.save(note);
    }

    @Transactional
    public void deleteNote(Long id, Long userId) {
        Note note = findByIdAndUserId(id, userId);
        noteRepository.delete(note);
    }
}
