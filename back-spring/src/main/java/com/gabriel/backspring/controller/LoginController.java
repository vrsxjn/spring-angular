package com.gabriel.backspring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gabriel.backspring.jwt.JwtController;
import com.gabriel.backspring.models.LoginModel;
import com.gabriel.backspring.repository.UserRepository;

@RestController
@RequestMapping("/auth")
@CrossOrigin("*")
public class LoginController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody LoginModel register) {
        userRepository.save(register);
        return ResponseEntity.status(200).body(register);    
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginModel loginUser) {
        String username = loginUser.getUsername();
        String senha = loginUser.getPassword();

        if (username == null || senha == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Usuario e senha são obrigatórios");
        }

        LoginModel user = userRepository.findByUsername(username);

        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Usuário não encontrado");
        }

        if (!user.getPassword().equals(senha)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Senha incorreta");
        }

        String token = JwtController.generateToken(user.getId());

        user.setToken(token);
        userRepository.save(user);

        loginUser.setToken(token);

        return ResponseEntity.ok(loginUser);
    }
}