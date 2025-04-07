package com.javasb.blog.blog_app_apis.payloads;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CategoryResponse {

    private List<CategoryDto> categoryDtoList;

    private Integer pageNumber;

    private Integer pageSize;

    private long totalPages;

    private long totalElements;

    private boolean isLastPage;
}
