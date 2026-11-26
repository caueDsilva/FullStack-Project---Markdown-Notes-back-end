package com.example.Markdown_Notes.folder;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.Markdown_Notes.DTO.FolderCreateRequest;
import com.example.Markdown_Notes.DTO.FolderUpdateRequest;
import com.example.Markdown_Notes.exception.ResourceNotFoundException;
import com.example.Markdown_Notes.user.User;
import com.example.Markdown_Notes.user.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FolderService {

    private final FolderRepository folderRepository;
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public List<Folder> findByUserId(Long userId) {
        return folderRepository.findByUserUserId(userId);
    }

    @Transactional(readOnly = true)
    public Folder findByIdAndUserId(Long id, Long userId) {
        return folderRepository.findByIdAndUserUserId(id, userId)
            .orElseThrow(() -> new ResourceNotFoundException("Pasta nao encontrada"));
    }

    @Transactional
    public Folder createFolder(Long userId, FolderCreateRequest request) {
        User user = userRepository.getReferenceById(userId);

        Folder folder = new Folder();
        folder.setUser(user);
        folder.setName(request.name());

        return folderRepository.save(folder);
    }

    @Transactional
    public Folder updateFolder(Long id, Long userId, FolderUpdateRequest request) {
        Folder folder = findByIdAndUserId(id, userId);
        folder.setName(request.name());
        return folderRepository.save(folder);
    }

    @Transactional
    public void deleteFolder(Long id, Long userId) {
        Folder folder = findByIdAndUserId(id, userId);
        folderRepository.delete(folder);
    }
}
