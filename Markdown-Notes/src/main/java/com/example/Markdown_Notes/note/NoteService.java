package com.example.Markdown_Notes.note;

import java.util.List;
import org.springframework.stereotype.Service;

import com.example.Markdown_Notes.DTO.NoteCreateRequest;
import com.example.Markdown_Notes.DTO.NoteUpdateRequest;
import com.example.Markdown_Notes.exception.ResourceNotFoundException;
//import com.example.Markdown_Notes.folder.Folder;
//import com.example.Markdown_Notes.folder.FolderRepository;
import com.example.Markdown_Notes.user.User;

import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NoteService {

    private final NoteRepository noteRepository;
    //private final FolderRepository folderRepository;

    @Transactional 
    public List<Note> findByUserId(Long userId) {
        return noteRepository.findByUserId(userId);
    }

    @Transactional(readOnly = true)
    public Note findByIdAndUserId(Long id,Long userId){
        return noteRepository.findByIdAndUserId(id, userId)
        .orElseThrow(() -> new ResourceNotFoundException("Nota Não encontrada"));
    }

    @Transactional
    public Note updateByIdAndUserId(Long id, Long userId, NoteUpdateRequest noteUpdateRequest) {
        Note note = findByIdAndUserId(id, userId);
        
        note.setTitle(noteUpdateRequest.title());
        note.setContent(noteUpdateRequest.content());
        note.setIsFavorite(noteUpdateRequest.favorite());
        note.setIsArchived(noteUpdateRequest.archived());

        /*if(noteUpdateRequest.folderId() != null){
            Folder folder = folderRepository.findByIdAndUserId(noteUpdateRequest.folderId(), userId);
            note.setFolder(folder)
        } else {
            note.setFolder(null);
        }*/

        return noteRepository.save(note);
    }

    @Transactional 
    public Note createNote(Long userId, NoteCreateRequest request){
        Note note = new Note();
        note.setUserId(userId);
        note.setTitle(request.title());
        note.setContent(request.content());

        /*if (request.folderId() != null){
            Folder folder = folderRepository.findByIdAndUserId(request.folderId(), user.getId());
            note.setFolder(folder);
        }*/

        return noteRepository.save(note);
    }

    @Transactional
    public void deleteByIdAndUserId(Long id, Long userId) {
        Note note = findByIdAndUserId(id, userId);
        // note.setDeletedAt(LocalDateTime.now());
        noteRepository.delete(note);
    }

}