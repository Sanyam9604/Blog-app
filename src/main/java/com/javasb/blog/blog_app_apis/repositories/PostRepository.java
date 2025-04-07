package com.javasb.blog.blog_app_apis.repositories;

import com.javasb.blog.blog_app_apis.entities.Category;
import com.javasb.blog.blog_app_apis.entities.Post;
import com.javasb.blog.blog_app_apis.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Integer> {

    List<Post> findByUser(User user);

    List<Post> findByCategory(Category category);

    List<Post> findByTitleContaining(String title);

    @Query("SELECT p FROM Post p WHERE p.content LIKE %:keyword%")
    List<Post> findByContentContaining(@Param("keyword") String keyword);
}
