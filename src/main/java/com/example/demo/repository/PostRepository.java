package com.example.demo.repository;

import com.example.demo.entity.Post;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

/**
 * 这个里面有个CacheConfig，配置了cacheNames。
 * 我在findById方法时加了个@Cacheable（key= "#p0")，#p0代表第一个参数，也就是id。
 * 这句话加上之后，当你在调用findById时，就会先从redis的post缓存对象里去查询key等于传过来的id的值。如果没有，就去查表。
 *
 * 在save方法上加了个CachePut，代表往缓存里添加值，key为参数post的id属性，这样当我们save一个Post对象时，
 * redis就会新增一个以id为key的Post对象；如果是update操作，那同样，redis会覆盖id相同的Post对象的值，也完成一次更新。
 * 更多标签，请看http://docs.spring.io/spring/docs/current/spring-framework-reference/html/cache.html#cache-spel-context
 *
 * 这样，在对post的新增和修改时都会自动缓存到redis里。
 */

@CacheConfig(cacheNames = "post")
public interface PostRepository extends JpaRepository<Post, Integer>, JpaSpecificationExecutor<Post> {

    @Cacheable(key = "#p0")
    Post findById(int id);

    /**
     * 新增或修改时
     */
    @CachePut(key = "#p0.id")
    @Override
    Post save(Post post);

    @Transactional
    @Modifying
    @CacheEvict(key = "#p0")
    int deleteById(int id);
}
