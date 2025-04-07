package com.javasb.blog.blog_app_apis.config;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordMatch {

    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        // The stored password from the DB
        String storedPassword = "$2a$10$RlW4zwovQhylhfAxCgVn1e1eg52UsHyajt.CJ1Wi7Ow2ZD3ysceXK";

        // The raw password you are using during login
        String rawPassword = "test@123";

        // Check if they match
        boolean isMatch = encoder.matches(rawPassword, storedPassword);
        System.out.println("Password matches: " + isMatch);
    }
}
