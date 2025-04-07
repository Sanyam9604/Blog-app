package com.javasb.blog.blog_app_apis.controllers;

import com.javasb.blog.blog_app_apis.payloads.ApiResponse;
import com.javasb.blog.blog_app_apis.payloads.CommentDto;
import com.javasb.blog.blog_app_apis.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping("posts/{postId}/users/{userId}/comment")
    public ResponseEntity<CommentDto> addComment(@PathVariable Integer postId,
                                                 @PathVariable Integer userId,
                                                 @RequestBody CommentDto commentDto) {
        CommentDto createdCommentDto = commentService.addComment(commentDto, postId, userId);
        return new ResponseEntity<>(createdCommentDto, HttpStatus.CREATED);
    }

    @PutMapping("posts/{postId}/users/{userId}/comment")
    public ResponseEntity<CommentDto> modifyComment(@PathVariable Integer postId,
                                                    @PathVariable Integer userId,
                                                    @RequestBody CommentDto commentDto) {
        return new ResponseEntity<>(commentService.updateComment(commentDto, postId, userId), HttpStatus.OK);
    }

    @DeleteMapping("posts/{postId}/users/{userId}/comments/{commentId}")
    public ResponseEntity<ApiResponse> deleteComment(@PathVariable Integer postId,
                                                     @PathVariable Integer userId,
                                                     @PathVariable Integer commentId) {
        commentService.deleteComment(commentId, postId, userId);
        return new ResponseEntity<>(new ApiResponse("Comment is deleted successfully !", true), HttpStatus.OK);
    }

    @GetMapping("posts/{postId}/users/{userId}/comment/{commentId}")
    public ResponseEntity<CommentDto> getCommentById(@PathVariable Integer commentId) {
        return new ResponseEntity<>(commentService.getCommentById(commentId), HttpStatus.OK);
    }

    @GetMapping("posts/{postId}/comments")
    public ResponseEntity<List<CommentDto>> getAllCommentsOfPost(@PathVariable Integer postId) {
        return new ResponseEntity<>(commentService.getAllCommentsOfPost(postId), HttpStatus.OK);
    }

    @GetMapping("users/{userId}/comments")
    public ResponseEntity<List<CommentDto>> getAllCommentsOfUser(@PathVariable Integer userId) {
        return new ResponseEntity<>(commentService.getAllCommentsOfUser(userId), HttpStatus.OK);
    }
}
