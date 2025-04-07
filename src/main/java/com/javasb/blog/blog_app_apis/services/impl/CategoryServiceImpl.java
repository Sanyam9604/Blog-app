package com.javasb.blog.blog_app_apis.services.impl;

import com.javasb.blog.blog_app_apis.entities.Category;
import com.javasb.blog.blog_app_apis.exceptions.ResourceNotFoundException;
import com.javasb.blog.blog_app_apis.payloads.CategoryDto;
import com.javasb.blog.blog_app_apis.payloads.CategoryResponse;
import com.javasb.blog.blog_app_apis.repositories.CategoryRepository;
import com.javasb.blog.blog_app_apis.services.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {

        Category category = this.modelMapper.map(categoryDto, Category.class);
        Category createdCategory = categoryRepository.save(category);

        return this.modelMapper.map(createdCategory, CategoryDto.class);
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId) {

        Category category = this.categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));


        if(categoryDto.getCategoryTitle() != null) {
            category.setCategoryTitle(categoryDto.getCategoryTitle());
        }

        if(categoryDto.getCategoryDescription() != null) {
            category.setCategoryDescription(categoryDto.getCategoryDescription());
        }

        return modelMapper.map(this.categoryRepository.save(category), CategoryDto.class);
    }

    @Override
    public void deleteCategory(Integer categoryId) {

        Category category = this.categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));

        categoryRepository.delete(category);
    }

    @Override
    public CategoryDto getCategory(Integer categoryId) {
        Category category = this.categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));

        return modelMapper.map(category, CategoryDto.class);
    }

    @Override
    public CategoryResponse getCategories(Integer pageSize, Integer pageNumber, String sortBy, String direction) {

        Sort sort = direction.equalsIgnoreCase("ASC") ?
                Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable p = PageRequest.of(pageNumber, pageSize, sort);

        Page<Category> categories = categoryRepository.findAll(p);

        List<Category> categoryList = categories.getContent();

        List<CategoryDto> categoryDtoList = categoryList.stream()
                .map((category) -> modelMapper.map(category, CategoryDto.class)).collect(Collectors.toList());

        CategoryResponse categoryResponse = new CategoryResponse();
        categoryResponse.setCategoryDtoList(categoryDtoList);
        categoryResponse.setPageSize(pageSize);
        categoryResponse.setPageNumber(pageNumber);
        categoryResponse.setLastPage(categories.isLast());
        categoryResponse.setTotalElements(categories.getTotalElements());
        categoryResponse.setTotalPages(categories.getTotalPages());

        return categoryResponse;
    }
}
