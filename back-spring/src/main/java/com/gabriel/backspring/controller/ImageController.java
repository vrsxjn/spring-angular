package com.gabriel.backspring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gabriel.backspring.jwt.JwtController;
import com.gabriel.backspring.models.CommentModel;
import com.gabriel.backspring.models.ImageModel;
import com.gabriel.backspring.models.LoginModel;
import com.gabriel.backspring.repository.ImageRepository;
import com.gabriel.backspring.repository.UserRepository;

import jakarta.servlet.http.HttpServletRequest;

import java.util.Base64;
import java.util.List;
import java.util.Optional;
@RestController
@RequestMapping("/images")
@CrossOrigin("*")
public class ImageController {
    
  @Autowired
  private ImageRepository imageRepository;

  @Autowired
  private UserRepository userRepository;

  @GetMapping()
  public ResponseEntity<?> postGet() {
    List<ImageModel> imageList = imageRepository.findAll();
    return ResponseEntity.ok(imageList);
  }
  
  @GetMapping("/{imageName}")
  public ResponseEntity<?> pegarImage(@PathVariable("imageName") String imageName) {
    try {

      ImageModel image = imageRepository.findByFileName(imageName);

      if (image == null) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body("Imagem não encontrada");
      }

      byte[] imageBytes = Base64.getDecoder().decode(image.getBase64());
      return ResponseEntity.ok()
          .contentType(MediaType.parseMediaType(image.getMimeType()))
          .body(imageBytes);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
          .body("Erro ao buscar a imagem");
    }
  }

  @PostMapping("/upload")
  public ResponseEntity<?> uploadImage(HttpServletRequest request, @RequestBody ImageModel image) {

    String token = JwtController.pegaTokenHeader(request);

    LoginModel user = userRepository.findByToken(token);

    if (user == null) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Acesso negado");
    }

    ImageModel savedImage = imageRepository.save(image);

    String imageUrl = "http://localhost:8080/images/" + image.getFileName();
    savedImage.setUrlImage(imageUrl);
    imageRepository.save(savedImage);
    image.setUrlImage(savedImage.getUrlImage());
    return ResponseEntity.ok()
        .body(image);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> delete(HttpServletRequest request, @PathVariable Long id) {
    String token = JwtController.pegaTokenHeader(request);

    LoginModel user = userRepository.findByToken(token);

    if (user == null) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Acesso negado");
    }

    try {
      Optional<ImageModel> image = imageRepository.findById(id);
      if (image.isPresent()) {
          imageRepository.deleteById(id);
          return ResponseEntity.ok().build(); 
      } else {
          return ResponseEntity.notFound().build(); 
      }
      } catch (Exception e) {
          return ResponseEntity.badRequest().build();
      }
  }
  

  
}