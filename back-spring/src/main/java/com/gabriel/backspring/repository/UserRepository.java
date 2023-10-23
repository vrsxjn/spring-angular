package com.gabriel.backspring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gabriel.backspring.models.LoginModel;


@Repository
public interface UserRepository extends JpaRepository<LoginModel, Long> {
    LoginModel findByToken(String token);
    LoginModel findByUsername(String username);
}