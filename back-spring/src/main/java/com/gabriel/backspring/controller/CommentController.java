package com.gabriel.backspring.controller;

import java.util.List;
import java.util.Optional;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gabriel.backspring.jwt.JwtController;
import com.gabriel.backspring.models.CommentModel;
import com.gabriel.backspring.models.LoginModel;
import com.gabriel.backspring.repository.CommentRepository;
import com.gabriel.backspring.repository.UserRepository;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/comment")
@CrossOrigin("*")
public class CommentController {
  
  @Autowired
  private CommentRepository commentRepository;
  @Autowired
  private UserRepository userRepository;


  @GetMapping()
  public ResponseEntity<?> commentGet(HttpServletRequest request) {
    String token = JwtController.pegaTokenHeader(request);

    LoginModel user = userRepository.findByToken(token);

    if (user == null) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Acesso negado");
    }

    List<CommentModel> commentList = commentRepository.findAll();
    return ResponseEntity.ok(commentList);
  
  }

  @PostMapping() 
  public ResponseEntity<?> commentReg(HttpServletRequest request, @RequestBody CommentModel modelComp) {
    String token = JwtController.pegaTokenHeader(request);

    LoginModel user = userRepository.findByToken(token);

    if (user == null) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Acesso negado");
    }

    commentRepository.save(modelComp);
    return ResponseEntity.status(200).body(modelComp);    
  }

  @PutMapping
  public ResponseEntity<?> update(HttpServletRequest request, @RequestBody CommentModel commentario) {
    String token = JwtController.pegaTokenHeader(request);

    LoginModel user = userRepository.findByToken(token);

    if (user == null) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Acesso negado");
    }

    Optional<CommentModel> commentUp = commentRepository.findById(commentario.getId());
  
    if (commentUp.isPresent()) {
      CommentModel comment = commentUp.get();
       
      comment.setComment(commentario.getComment());
  
      commentRepository.save(comment);
        return ResponseEntity.ok().build();
      } else {
        return ResponseEntity.badRequest().build();
      }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> delete(HttpServletRequest request, @PathVariable Long id) {
    String token = JwtController.pegaTokenHeader(request);

    LoginModel user = userRepository.findByToken(token);

    if (user == null) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Acesso negado");
    }

    try {
      Optional<CommentModel> comment = commentRepository.findById(id);
      if (comment.isPresent()) {
          commentRepository.deleteById(id);
          return ResponseEntity.ok().build(); 
      } else {
          return ResponseEntity.notFound().build(); 
      }
      } catch (Exception e) {
          return ResponseEntity.badRequest().build();
      }
  }
  

}
