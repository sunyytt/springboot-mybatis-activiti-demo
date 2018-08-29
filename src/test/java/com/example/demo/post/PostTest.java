package com.example.demo.post;

import com.example.demo.common.Criteria;
import com.example.demo.common.Restrictions;
import com.example.demo.entity.Post;
import com.example.demo.repository.PostRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
@RunWith(SpringRunner.class)
@SpringBootTest
public class PostTest {
    /**
     * 待优化更好的
     */
    @Autowired
    private PostRepository postRepository;

    @Test
    public void test() {
        Criteria<Post> criteria = new Criteria<>();
//        criteria.add(Restrictions.like("title", "1", true));
        criteria.add(Restrictions.like("content", "ee", true));
        List<Long> value = new ArrayList<>();
        value.add(16L);
        value.add(18L);
        criteria.add(Restrictions.in("id",value,true));
        List<Post> postList = postRepository.findAll(criteria);
        for (Post post : postList) {
            System.out.println(post.getId());
        }
    }


}
