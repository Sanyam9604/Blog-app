package com.javasb.blog.blog_app_apis.repositories;

import com.javasb.blog.blog_app_apis.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
