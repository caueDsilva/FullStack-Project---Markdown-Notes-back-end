package com.example.Markdown_Notes.user;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;



@RestController 
@RequestMapping("/user")
public class UserController {
    @GetMapping("/user")
    public ResponseEntity<String> getUser() {
        return ResponseEntity.ok("User endpoint");
    }
    
}
