package com.javasb.blog.blog_app_apis.services;

import com.javasb.blog.blog_app_apis.payloads.CommentDto;

import java.util.List;

public interface CommentService {

    CommentDto addComment(CommentDto commentDto, Integer postId, Integer userId);

    CommentDto updateComment(CommentDto commentDto, Integer postId, Integer userId);

    void deleteComment(Integer commentId, Integer postId, Integer userId);

    CommentDto getCommentById(Integer commentId);

    List<CommentDto> getAllCommentsOfPost(Integer postId);

    List<CommentDto> getAllCommentsOfUser(Integer userId);
}
