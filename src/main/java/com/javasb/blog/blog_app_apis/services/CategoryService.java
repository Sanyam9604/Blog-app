package com.javasb.blog.blog_app_apis.services;

import com.javasb.blog.blog_app_apis.payloads.CategoryDto;
import com.javasb.blog.blog_app_apis.payloads.CategoryResponse;

import java.util.List;

public interface CategoryService {

    // create
    CategoryDto createCategory(CategoryDto categoryDto);

    // update
    CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId);

    // delete
    void deleteCategory(Integer categoryId);

    // get
    CategoryDto getCategory(Integer categoryId);

    // get all
    CategoryResponse getCategories(Integer pageSize, Integer pageNumber, String sortBy, String direction);

}
