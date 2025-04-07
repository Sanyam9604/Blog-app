# Blog-app
This is a full-featured Blog Application built using Spring Boot, featuring a clean separation of concerns, secure authentication, and a well-structured content publishing system.

🔐 Key Features
User Authentication & Authorization
Secure login and registration using Spring Security with JWT-based authentication.
Supports Role-Based Access Control (RBAC) with roles like:

ADMIN: Full access including user management.

USER: Can post blogs, comment, and view categories.

Blog Posts

Users can create, update, delete, and read blog posts.

Blogs support rich content and are timestamped.

Posts can be filtered by categories or users.

Categories

Blogs are grouped into categories for easy organization and filtering.

Admins can manage category creation and deletion.

Comments

Authenticated users can comment on posts.

Comments can be added, updated, or deleted based on user permissions.

Role-based Access

Admins can perform CRUD on any blog and comment.

Users can manage only their own content.

⚙️ Tech Stack
Spring Boot (Java)

Spring Security (JWT Auth)

JPA & Hibernate

MySQL / PostgreSQL

ModelMapper / Lombok

Maven
