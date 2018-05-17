package com.example.demo.java8;

import com.example.demo.model.User;
import org.springframework.boot.autoconfigure.security.SecurityProperties;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class java8Stream {
    public static void main(String[] args) {
        List<User> users = init();
        Stream<User> stream = users.stream();
        List<User> result = stream.limit(3).collect(Collectors.toList());
        forEachJava8(result);
    }

    public static List<User> init(){
        List<User> users = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            User user = new User();
            user.setId(i);
            user.setUsername("username"+i);
            user.setPassword("pass"+i);
            users.add(user);
        }
        return users;
    }

    private static void forEachJava8(List<User> names){
        System.out.println("java8 list forEach");
        names.forEach(x-> System.out.println(x));
    }
}


