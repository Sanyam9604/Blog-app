package com.javasb.blog.blog_app_apis.repositories;

import com.javasb.blog.blog_app_apis.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
}
