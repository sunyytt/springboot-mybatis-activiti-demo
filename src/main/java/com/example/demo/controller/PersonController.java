package com.example.demo.controller;

import com.example.demo.entity.Person;
import com.example.demo.service.person.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/person")
public class PersonController {
    @Autowired
    PersonService personService;

    @Autowired
    CacheManager cacheManager;

    @PutMapping("/put")
    public long put(Person person) {
        Person p = personService.save(person);
        return p.getId();
    }

    @RequestMapping("/get")
    public Person cacheable(Person person) {
        String a = "a";
        String[] b = {"1", "2"};
        List<Long> c = new ArrayList<>();
        c.add(3L);
        c.add(4L);
        return personService.findOne(person, a, b, c);
    }

    @RequestMapping("/get1")
    public Person cacheable1(Person person) {

        return personService.findOne1();
    }

    @RequestMapping("/get2")
    public Person cacheable2(Person person) {

        return personService.findOne2(person);
    }

    @DeleteMapping("/delete/{id}")
    public String evit(@PathVariable long id) {
        personService.remove(id);
        return "ok";
    }
}
