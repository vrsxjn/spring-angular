package com.gabriel.backspring.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "pics")
public class ImageModel {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private Long user_id;
  private String fileName;
  private String mimeType;
  private String base64;
  private String urlImage;

  

  public String getFileName() {
    return fileName;
  }
  public void setFileName(String fileName) {
    this.fileName = fileName;
  }
  public String getMimeType() {
    return mimeType;
  }
  public void setMimeType(String mimeType) {
    this.mimeType = mimeType;
  }
  public String getBase64() {
    return base64;
  }
  public void setBase64(String base64) {
    this.base64 = base64;
  }
  public String getUrlImage() {
    return urlImage;
  }
  public void setUrlImage(String urlImage) {
    this.urlImage = urlImage;
  }
  public Long getUser_id() {
    return user_id;
  }
  public void setUser_id(Long user_id) {
    this.user_id = user_id;
  }

  public Long getId() {
    return id;
  }
  public void setId(Long id) {
    this.id = id;
  }

  
}