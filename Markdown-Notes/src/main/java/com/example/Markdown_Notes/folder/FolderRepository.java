package com.example.Markdown_Notes.folder;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FolderRepository extends JpaRepository<Folder, Long> {

    // Navega o relacionamento folder.user.userId
    List<Folder> findByUserUserId(Long userId);

    Optional<Folder> findByIdAndUserUserId(Long id, Long userId);

}
