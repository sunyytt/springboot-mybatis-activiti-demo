package com.example.demo.service.user;




import com.example.demo.common.result.ResultPager;
import com.github.pagehelper.PageInfo;
import com.example.demo.model.User;

public interface UserService {

    /**
     *
     * @param current  
     * @param pageSize
     * @return
     */
    ResultPager<User> findAllUser(int current, int pageSize);

    User getUserById(int id);

    User getUserByName(String name);
}
