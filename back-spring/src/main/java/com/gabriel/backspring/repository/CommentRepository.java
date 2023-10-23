package com.gabriel.backspring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.gabriel.backspring.models.CommentModel;

@Repository
public interface CommentRepository extends JpaRepository<CommentModel, Long> {

}
