package com.ra.base_spring_boot.services.impl;

import com.ra.base_spring_boot.dto.req.BlogPostRequest;
import com.ra.base_spring_boot.dto.resp.BlogPostResponse;
import com.ra.base_spring_boot.exception.HttpBadRequest;
import com.ra.base_spring_boot.exception.HttpNotFound;
import com.ra.base_spring_boot.model.BlogPost;
import com.ra.base_spring_boot.model.User;
import com.ra.base_spring_boot.repository.IBlogPostRepository;
import com.ra.base_spring_boot.repository.IUserRepository;
import com.ra.base_spring_boot.security.principle.MyUserDetails;
import com.ra.base_spring_boot.services.IBlogPostService;
import com.ra.base_spring_boot.services.ICloudinaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BlogPostServiceImpl implements IBlogPostService {
private final IBlogPostRepository blogPostRepository;
    private final ICloudinaryService cloudinaryService;

    @Override
    public BlogPostResponse save(BlogPostRequest request, MultipartFile image) {
        String imageUrl = null;

        if (image != null && !image.isEmpty()) {
            Map uploadResult = cloudinaryService.upload(image);
            imageUrl = (String) uploadResult.get("secure_url");
        }

        BlogPost post = BlogPost.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .imageUrl(imageUrl)
                .build();

        blogPostRepository.save(post);

        return BlogPostResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .imageUrl(post.getImageUrl())
                .build();
    }

    @Override
    public BlogPostResponse update(Long id, BlogPostRequest request, MultipartFile image) {
        BlogPost post = blogPostRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        post.setTitle(request.getTitle());
        post.setContent(request.getContent());

        if (image != null && !image.isEmpty()) {
            Map uploadResult = cloudinaryService.upload(image);
            post.setImageUrl((String) uploadResult.get("secure_url"));
        }

        blogPostRepository.save(post);

        return BlogPostResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .imageUrl(post.getImageUrl())
                .build();
    }

    @Override
    public List<BlogPostResponse> findAll() {
        return blogPostRepository.findAll().stream()
                .map(post -> BlogPostResponse.builder()
                        .id(post.getId())
                        .title(post.getTitle())
                        .content(post.getContent())
                        .imageUrl(post.getImageUrl())
                        .build())
                .toList();
    }

    @Override
    public BlogPostResponse findById(Long id) {
        BlogPost post = blogPostRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        return BlogPostResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .imageUrl(post.getImageUrl())
                .build();
    }

    @Override
    public void delete(Long id) {
        blogPostRepository.deleteById(id);
    }
}