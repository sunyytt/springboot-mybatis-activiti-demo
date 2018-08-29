package com.example.demo.service.post;

import com.example.demo.entity.Post;

public interface PostService {

    Post findById(int id);
    Post save(Post post);
    int delete(int id);
}
