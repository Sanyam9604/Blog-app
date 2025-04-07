package com.javasb.blog.blog_app_apis.services.impl;

import com.javasb.blog.blog_app_apis.entities.Category;
import com.javasb.blog.blog_app_apis.entities.Post;
import com.javasb.blog.blog_app_apis.entities.User;
import com.javasb.blog.blog_app_apis.exceptions.ResourceNotFoundException;
import com.javasb.blog.blog_app_apis.payloads.PostDto;
import com.javasb.blog.blog_app_apis.payloads.PostResponse;
import com.javasb.blog.blog_app_apis.repositories.CategoryRepository;
import com.javasb.blog.blog_app_apis.repositories.PostRepository;
import com.javasb.blog.blog_app_apis.repositories.UserRepository;
import com.javasb.blog.blog_app_apis.services.PostService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public PostDto createPost(PostDto postDto, Integer userId, Integer categoryId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "user id", userId));

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "category id", categoryId));

        Post post = modelMapper.map(postDto, Post.class);
        post.setId(null);
        if(postDto.getImageUrl() == null) {
            post.setImageUrl("default.png");
        }
        if(postDto.getAddedDate() == null) {
            post.setAddedDate(new Date());
        }

        post.setUser(user);
        post.setCategory(category);

        Post savedPost = this.postRepository.save(post);

        return this.modelMapper.map(savedPost, PostDto.class);
    }

    @Override
    public PostDto updatePost(PostDto postDto, Integer postId) {

        Post post = this.postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", " Id ", postId));

        if(postDto.getTitle() != null) {
            post.setTitle(postDto.getTitle());
        }
        if(postDto.getContent() != null) {
            post.setContent(postDto.getContent());
        }
        if(postDto.getImageUrl() != null) {
            post.setImageUrl(postDto.getImageUrl());
        }
        if(postDto.getCategory() != null) {
            post.setCategory(modelMapper.map(postDto.getCategory(), Category.class));
        }

        return modelMapper.map(this.postRepository.save(post), PostDto.class);
    }

    @Override
    public void deletePost(Integer postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", " Id ", postId));
        this.postRepository.deleteById(postId);
    }

    @Override
    public PostResponse getAllPosts(Integer pageSize, Integer pageNumber, String sortBy, String direction) {

        Sort sort = direction.equalsIgnoreCase("ASC") ?
                Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        Pageable p = PageRequest.of(pageNumber, pageSize, sort);
        Page<Post> posts = postRepository.findAll(p);

        List<Post> postList = posts.getContent();

        List<PostDto> postDtoList = postList.stream()
                .map(post -> modelMapper.map(post, PostDto.class))
                .collect(Collectors.toList());

        PostResponse postResponse = new PostResponse();

        postResponse.setContent(postDtoList);
        postResponse.setPageSize(pageSize);
        postResponse.setPageNumber(pageNumber);
        postResponse.setLastPage(posts.isLast());
        postResponse.setTotalElements(posts.getTotalElements());
        postResponse.setTotalPages(posts.getTotalPages());

        return postResponse;
    }

    @Override
    public PostDto getPostById(Integer postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "Id", postId));

        return modelMapper.map(post, PostDto.class);
    }

    @Override
    public List<PostDto> getAllPostsByCategory(Integer categoryId) {

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "category id", categoryId));

        List<Post> posts = postRepository.findByCategory(category);

        if(posts.isEmpty()) {
            throw new ResourceNotFoundException("Post", "categoryId", categoryId);
        }
        System.out.println("Posts: " + posts);

        return posts.stream()
                .map(post -> modelMapper.map(post, PostDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<PostDto> getAllPostsByUser(Integer userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "user id", userId));

        List<Post> posts = postRepository.findByUser(user);

        return posts.stream()
                .map(post -> modelMapper.map(post, PostDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<PostDto> searchPostsByContent(String keyword) {
        List<Post> posts = postRepository.findByContentContaining(keyword);
        return posts.stream()
                .map(post -> modelMapper.map(post, PostDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<PostDto> searchPosts(String title) {
        List<Post> posts = postRepository.findByTitleContaining(title);
        return posts.stream()
                .map(post -> modelMapper.map(post, PostDto.class))
                .collect(Collectors.toList());
    }
}
