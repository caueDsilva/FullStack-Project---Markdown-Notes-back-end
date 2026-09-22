package com.example.Markdown_Notes.auth;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Markdown_Notes.DTO.LoginRequestDTO;
import com.example.Markdown_Notes.DTO.RegisterRequestDTO;
import com.example.Markdown_Notes.infra.security.TokenService;
import com.example.Markdown_Notes.user.User;
import com.example.Markdown_Notes.user.UserRepository;
import com.example.Markdown_Notes.DTO.ResponseDTO;

import lombok.RequiredArgsConstructor;

@RestController 
@RequestMapping ("/auth")
@RequiredArgsConstructor 
public class AuthController {
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final TokenService tokenService;

    @PostMapping ("/login")
    public ResponseEntity login(@RequestBody LoginRequestDTO body){
        User user = this.userRepository.findByEmail(body.email()).orElseThrow(()-> new RuntimeException("User not found"));

        if(passwordEncoder.matches(user.getPassword(),body.password())){
            String token = this.tokenService.generateToken(user);
            return ResponseEntity.ok(new ResponseDTO(user.getName(), token)); // -> verificar no front o retorno
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody RegisterRequestDTO body) {
        Optional<User> user = this.userRepository.findByEmail(body.email());

        if(user.isEmpty()){
            User newUser = new User();
            newUser.setName(body.name());
            newUser.setEmail(body.email());
            newUser.setPassword(passwordEncoder.encode(body.password()));
            this.userRepository.save(newUser);
        }
        return ResponseEntity.badRequest().build();
    }
    

}
