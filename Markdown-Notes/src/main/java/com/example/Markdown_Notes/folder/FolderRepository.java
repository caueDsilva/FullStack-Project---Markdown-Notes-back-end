package com.example.Markdown_Notes.folder;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.Markdown_Notes.DTO.FolderCreateRequest;
import com.example.Markdown_Notes.DTO.FolderUpdateRequest;
import com.example.Markdown_Notes.user.User;

public interface FolderRepository extends JpaRepository <Folder, Long>{
    List<Folder> findByUserId(Long userId);

    Optional<Folder> findByIdAndUserId(Long id, Long userId);

    Folder createFolder(User user, FolderCreateRequest request);

    Folder updateFolder(Long id, Long userId, FolderUpdateRequest request);

    void deleteByIdAndUserId(Long id, Long userId);
}
