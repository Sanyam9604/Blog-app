package com.javasb.blog.blog_app_apis.repositories;

import com.javasb.blog.blog_app_apis.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {
}
