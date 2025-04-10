package com.javasb.blog.blog_app_apis;

import com.javasb.blog.blog_app_apis.entities.Role;
import com.javasb.blog.blog_app_apis.repositories.RoleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

import static com.javasb.blog.blog_app_apis.config.AppConstants.*;

@SpringBootApplication
public class BlogAppApisApplication implements CommandLineRunner {

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private RoleRepository roleRepository;

	public static void main(String[] args) {
		SpringApplication.run(BlogAppApisApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	@Override
	public void run(String... args) throws Exception {
//		System.out.println("Encrypted password:"+ passwordEncoder.encode("test@123"));

//		try {
//
//			Role role = new Role();
//			role.setId(ADMIN_USER);
//			role.setName("ROLE_ADMIN");
//
//			Role role1 = new Role();
//			role1.setId(NORMAL_USER);
//			role1.setName("ROLE_NORMAL");
//
//			List<Role> roles = List.of(role, role1);
//			List<Role> result = this.roleRepository.saveAll(roles);
//
//			result.forEach(r-> System.out.println(r));
//
//		} catch (Exception e) {
//
//		}
	}
}
