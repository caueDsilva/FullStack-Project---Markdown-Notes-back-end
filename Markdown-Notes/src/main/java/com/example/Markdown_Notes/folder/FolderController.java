package com.example.Markdown_Notes.folder;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Markdown_Notes.DTO.FolderCreateRequest;
import com.example.Markdown_Notes.DTO.FolderResponseDTO;
import com.example.Markdown_Notes.DTO.FolderUpdateRequest;
import com.example.Markdown_Notes.user.User;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController 
@RequiredArgsConstructor 
@RequestMapping ("/api/folder")
public class FolderController {
    private final FolderService folderService;

    @GetMapping()
    public ResponseEntity<List<FolderResponseDTO>> findAll(@AuthenticationPrincipal User user){
        List<FolderResponseDTO> folders = folderService.findByUserId(user.getId()).stream()
        .map(FolderResponseDTO::fromEntity).toList();

        return ResponseEntity.ok(folders);
    }

    @PostMapping
    public ResponseEntity<FolderResponseDTO> createFolder(@AuthenticationPrincipal User user,@Valid @RequestBody FolderCreateRequest request) {
        Folder folder = folderService.createFolder(user.getId(), request);
        return ResponseEntity.status(HttpStatus.CREATED).body(FolderResponseDTO.fromEntity(folder));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<FolderResponseDTO> updateFolder(@AuthenticationPrincipal User user, @PathVariable Long id, @Valid @RequestBody FolderUpdateRequest request){
        Folder folder = folderService.updateFolder(id, user.getId(), request);
        return ResponseEntity.ok(FolderResponseDTO.fromEntity(folder));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFolder(@AuthenticationPrincipal User user, @PathVariable Long id ){
        folderService.deleteFolder(id, user.getId());

        return ResponseEntity.noContent().build();
    }


}
