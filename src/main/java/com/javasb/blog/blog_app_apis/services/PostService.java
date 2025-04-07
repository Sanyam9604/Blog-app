package com.javasb.blog.blog_app_apis.services;

import com.javasb.blog.blog_app_apis.entities.Post;
import com.javasb.blog.blog_app_apis.payloads.PostDto;
import com.javasb.blog.blog_app_apis.payloads.PostResponse;

import java.util.List;

public interface PostService {

    // create post
    PostDto createPost(PostDto postDto, Integer userId, Integer categoryId);

    // update post
    PostDto updatePost(PostDto postDto, Integer postId);

    // delete post
    void deletePost(Integer postId);

    // get all post
    PostResponse getAllPosts(Integer pageSize, Integer pageNumber, String sortBy, String direction);

    // get post by id
    PostDto getPostById(Integer postId);

    // get all posts by category
    List<PostDto> getAllPostsByCategory(Integer categoryId);

    // get all posts of an user
    List<PostDto> getAllPostsByUser(Integer userId);

    // get post by content match
    List<PostDto> searchPosts(String title);

    List<PostDto> searchPostsByContent(String keyword);
}
