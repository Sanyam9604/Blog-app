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
public class PostResponse {

    private List<PostDto> content;

    private Integer pageNumber;

    private Integer pageSize;

    private long totalPages;

    private long totalElements;

    private boolean isLastPage;
}
