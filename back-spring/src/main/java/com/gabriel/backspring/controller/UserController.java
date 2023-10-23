package com.gabriel.backspring.controller;

import java.util.List;
import java.util.Optional;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gabriel.backspring.jwt.JwtController;
import com.gabriel.backspring.models.LoginModel;
import com.gabriel.backspring.models.dto.TokenRequestDTO;
import com.gabriel.backspring.repository.UserRepository;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/usuario")
@CrossOrigin("*")
public class UserController {

  @Autowired
  private UserRepository userRepository;

  @GetMapping("")
  public ResponseEntity<?> getAllUser(HttpServletRequest request) {
    String token = JwtController.pegaTokenHeader(request);

    LoginModel user = userRepository.findByToken(token);

    if (user == null) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Acesso negado");
    }
    return ResponseEntity.ok(userRepository.findAll());
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> getUserById(HttpServletRequest request, @PathVariable Long id) {
    String token = JwtController.pegaTokenHeader(request);

    LoginModel user = userRepository.findByToken(token);

    if (user == null) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Acesso negado");
    }
    return ResponseEntity.ok(userRepository.findById(id));
  }

  @PostMapping("/user")
  public ResponseEntity<?> getUserByToken(HttpServletRequest requestz, @RequestBody TokenRequestDTO request) {
    String token = JwtController.pegaTokenHeader(requestz);

    LoginModel user = userRepository.findByToken(token);

    if (user == null) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Acesso negado");
    }

    String tokenz = request.getToken();

    return ResponseEntity.ok(userRepository.findByToken(tokenz));
  }
}
