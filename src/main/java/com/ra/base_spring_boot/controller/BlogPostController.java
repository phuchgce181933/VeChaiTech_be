package com.ra.base_spring_boot.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ra.base_spring_boot.dto.ResponseWrapper;
import com.ra.base_spring_boot.dto.req.BlogPostRequest;
import com.ra.base_spring_boot.dto.resp.BlogPostResponse;
import com.ra.base_spring_boot.services.IBlogPostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
@CrossOrigin("*")
public class BlogPostController {

        private final IBlogPostService blogPostService;

        @GetMapping
        public ResponseEntity<ResponseWrapper<List<BlogPostResponse>>> getAllPosts() {
                return ResponseEntity.ok(
                                ResponseWrapper.<List<BlogPostResponse>>builder()
                                                .status(HttpStatus.OK)
                                                .message("OK")
                                                .data(blogPostService.findAll())
                                                .build());
        }

        @GetMapping("/{id}")
        public ResponseEntity<ResponseWrapper<BlogPostResponse>> getPostById(@PathVariable Long id) {
                return ResponseEntity.ok(
                                ResponseWrapper.<BlogPostResponse>builder()
                                                .status(HttpStatus.OK)
                                                .message("OK")
                                                .data(blogPostService.findById(id))
                                                .build());
        }

        @PostMapping(consumes = "multipart/form-data")
        public ResponseEntity<ResponseWrapper<BlogPostResponse>> createPost(
                        @RequestPart("post") BlogPostRequest request,
                        @RequestPart(value = "image", required = false) MultipartFile image) {
                BlogPostResponse createdPost = blogPostService.save(request, image);
                return ResponseEntity.status(HttpStatus.CREATED).body(
                                ResponseWrapper.<BlogPostResponse>builder()
                                                .status(HttpStatus.CREATED)
                                                .message("Post created successfully")
                                                .data(createdPost)
                                                .build());
        }

        @PutMapping(value = "/{id}", consumes = "multipart/form-data")
        public ResponseEntity<ResponseWrapper<BlogPostResponse>> updatePost(
                        @PathVariable Long id,
                        @RequestPart("post") BlogPostRequest request,
                        @RequestPart(value = "image", required = false) MultipartFile image) {
                return ResponseEntity.ok(
                                ResponseWrapper.<BlogPostResponse>builder()
                                                .status(HttpStatus.OK)
                                                .message("Post updated successfully")
                                                .data(blogPostService.update(id, request, image))
                                                .build());
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<ResponseWrapper<Void>> deletePost(@PathVariable Long id) {
                blogPostService.delete(id);
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(
                                ResponseWrapper.<Void>builder()
                                                .status(HttpStatus.NO_CONTENT)
                                                .message("Deleted")
                                                .data(null)
                                                .build());
        }
}
