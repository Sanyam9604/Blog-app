package com.javasb.blog.blog_app_apis.services.impl;

import com.javasb.blog.blog_app_apis.entities.Comment;
import com.javasb.blog.blog_app_apis.entities.Post;
import com.javasb.blog.blog_app_apis.entities.User;
import com.javasb.blog.blog_app_apis.exceptions.ResourceNotFoundException;
import com.javasb.blog.blog_app_apis.payloads.CommentDto;
import com.javasb.blog.blog_app_apis.repositories.CommentRepository;
import com.javasb.blog.blog_app_apis.repositories.PostRepository;
import com.javasb.blog.blog_app_apis.repositories.UserRepository;
import com.javasb.blog.blog_app_apis.services.CommentService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CommentDto addComment(CommentDto commentDto, Integer postId, Integer userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "user id", userId));

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "post id", postId));

        Comment comment = modelMapper.map(commentDto, Comment.class);
        comment.setId(null);

        comment.setUser(user);
        comment.setPost(post);

        Comment savedComment = this.commentRepository.save(comment);

        return this.modelMapper.map(savedComment, CommentDto.class);
    }

    @Override
    public CommentDto updateComment(CommentDto commentDto, Integer postId, Integer userId) {

        // TODO: write logic to authenticate user is valid owner of the comment!

        Integer commentId = commentDto.getId();
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment", "comment id", commentId));

        comment.setContent(commentDto.getContent());

        Comment updatedComment = commentRepository.save(comment);

        return modelMapper.map(updatedComment, CommentDto.class);
    }

    @Override
    public void deleteComment(Integer commentId, Integer postId, Integer userId) {
        // TODO: write logic to authenticate user is valid owner of the comment!

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment", "comment id", commentId));

        commentRepository.delete(comment);
    }


    @Override
    public CommentDto getCommentById(Integer commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment", "commentId", commentId));
        return modelMapper.map(comment, CommentDto.class);
    }

    @Transactional
    @Override
    public List<CommentDto> getAllCommentsOfPost(Integer postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "postId", postId));

        Set<Comment> comments = post.getComments();

        List<CommentDto> commentDtoList =  comments.stream()
                .map((comment -> modelMapper.map(comment, CommentDto.class)))
                .collect(Collectors.toList());

        return commentDtoList;
    }

    @Transactional
    @Override
    public List<CommentDto> getAllCommentsOfUser(Integer userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "userId", userId));

        Set<Comment> comments = user.getComments();

        List<CommentDto> commentDtoList =  comments.stream()
                .map((comment -> modelMapper.map(comment, CommentDto.class)))
                .collect(Collectors.toList());

        return commentDtoList;
    }
}
