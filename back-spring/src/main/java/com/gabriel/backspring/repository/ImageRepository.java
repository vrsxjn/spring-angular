package com.gabriel.backspring.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gabriel.backspring.models.ImageModel;

public interface ImageRepository extends JpaRepository<ImageModel, Long> {
    ImageModel findByFileName(String fileName);
}