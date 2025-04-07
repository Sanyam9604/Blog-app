package com.javasb.blog.blog_app_apis.controllers;

import com.javasb.blog.blog_app_apis.config.AppConstants;
import com.javasb.blog.blog_app_apis.entities.Post;
import com.javasb.blog.blog_app_apis.exceptions.ResourceNotFoundException;
import com.javasb.blog.blog_app_apis.payloads.ApiResponse;
import com.javasb.blog.blog_app_apis.payloads.PostDto;
import com.javasb.blog.blog_app_apis.payloads.PostResponse;
import com.javasb.blog.blog_app_apis.services.FileService;
import com.javasb.blog.blog_app_apis.services.PostService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static com.javasb.blog.blog_app_apis.config.AppConstants.*;

@Slf4j
@RestController
@RequestMapping("/api/")
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private FileService fileService;

    @Value("${project.image}")
    private String path;

    // Post - create a post
    @PostMapping("/user/{userId}/category/{categoryId}/posts")
    public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto,
                                                   @PathVariable Integer userId,
                                                   @PathVariable Integer categoryId) {

        PostDto createdPost = postService.createPost(postDto, userId, categoryId);
        return new ResponseEntity<>(createdPost, HttpStatus.CREATED);
    }

    // Post - update a post
    @PutMapping("/posts/{postId}")
    public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto,
                                              @PathVariable Integer postId) {

        PostDto updatedPost = postService.updatePost(postDto, postId);
        return new ResponseEntity<>(updatedPost, HttpStatus.OK);
    }

    // DELETE - delete post
    @DeleteMapping("/posts/{postId}")
    public ResponseEntity<ApiResponse> deletePost(@PathVariable Integer postId) {
        this.postService.deletePost(postId);
        return new ResponseEntity(new ApiResponse("Post Deleted Successfully!", true), HttpStatus.OK);
    }

    // GET - get a post
    @GetMapping("/posts/{postId}")
    public ResponseEntity<PostDto> getPostById(@PathVariable Integer postId) {
        PostDto postDto = postService.getPostById(postId);
        return new ResponseEntity<>(postDto, HttpStatus.OK);
    }

    // GET - get all posts
    @GetMapping("/posts")
    public ResponseEntity<PostResponse> getAllPosts(
            @RequestParam(value = "pageNumber", defaultValue = PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(value = "sortBy", defaultValue = SORT_BY, required = false) String sortBy,
            @RequestParam(value = "direction", defaultValue = DEFAULT_DIRECTION, required = false) String direction
    ) {
        return new ResponseEntity<>(postService.getAllPosts(pageSize, pageNumber, sortBy, direction), HttpStatus.OK);
    }

    // GET - get posts by category
    @GetMapping("/category/{categoryId}/posts")
    public ResponseEntity<List<PostDto>> getPostsByCategory(@PathVariable Integer categoryId) {
        return new ResponseEntity<>(postService.getAllPostsByCategory(categoryId), HttpStatus.OK);
    }

    // GET - get posts by user
    @GetMapping("/user/{userId}/posts")
    public ResponseEntity<List<PostDto>> getPostsByUser(@PathVariable Integer userId) {
        return new ResponseEntity<>(postService.getAllPostsByUser(userId), HttpStatus.OK);
    }

    // GET - get posts by searching title
    @GetMapping("/posts/search/{keywords}")
    public ResponseEntity<List<PostDto>> searchPostsByTitle(@PathVariable String keywords) {
        return new ResponseEntity<>(postService.searchPosts(keywords), HttpStatus.OK);
    }

    // GET - get posts by searching content
    @GetMapping("/posts/search")
    public ResponseEntity<List<PostDto>> searchPostsByContent(@RequestParam String keywords) {
        return new ResponseEntity<>(postService.searchPostsByContent(keywords), HttpStatus.OK);
    }

    // POST - upload image to a post
    @PostMapping("/post/image/upload/{postId}")
    public ResponseEntity<PostDto> uploadPostImage(@PathVariable Integer postId,
            @RequestParam("image") MultipartFile image) throws IOException {

        PostDto postDto = this.postService.getPostById(postId);
        String imagePath = fileService.uploadImage(path, image);
        postDto.setImageUrl(imagePath);

        PostDto updatedPostDto = this.postService.updatePost(postDto, postId);

        return new ResponseEntity<>(updatedPostDto, HttpStatus.OK);
    }

    @GetMapping(value = "/post/image/{imageName}", produces = MediaType.IMAGE_JPEG_VALUE)
    public void getPostImage(@PathVariable String imageName,
                                                      HttpServletResponse response) throws IOException {
        InputStream resource = this.fileService.getResource(path, imageName);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource, response.getOutputStream());
    }
}
