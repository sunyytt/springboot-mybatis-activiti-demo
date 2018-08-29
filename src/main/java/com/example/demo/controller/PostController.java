package com.example.demo.controller;

import com.example.demo.entity.Post;
import com.example.demo.service.post.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/post")
public class PostController {

    @Autowired
    private PostService postService;

    @RequestMapping("/query/{id}")
    public Object query(@PathVariable int id) {
        return postService.findById(id);
    }

    @PutMapping("/save")
    public Object save( Post post) {
        return postService.save(post);
    }

    @DeleteMapping("/delete/{id}")
    public Object delete(@PathVariable int id) {
        return postService.delete(id);
    }

    @RequestMapping("/queryPage")
    public Object query(String name, int pageNum, int count) {
        //根据weight倒序分页查询
//        Pageable pageable = new PageRequest(pageNum, count, Sort.Direction.DESC, "weight");
//        return userRepository.findByName(name, pageable);
        return null;
    }

}
