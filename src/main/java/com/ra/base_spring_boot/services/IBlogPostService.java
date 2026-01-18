package com.ra.base_spring_boot.services;

import com.ra.base_spring_boot.dto.req.BlogPostRequest;
import com.ra.base_spring_boot.dto.resp.BlogPostResponse;
import com.ra.base_spring_boot.exception.HttpBadRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IBlogPostService {
    List<BlogPostResponse> findAll();
    BlogPostResponse findById(Long id);
    BlogPostResponse save(BlogPostRequest request, MultipartFile image);
    BlogPostResponse update(Long id, BlogPostRequest request, MultipartFile image);
    void delete(Long id);
}
