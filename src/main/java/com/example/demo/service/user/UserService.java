package com.example.demo.service.user;




import com.github.pagehelper.PageInfo;
import com.example.demo.model.User;

public interface UserService {

    int addUser(User user);

    User getUserById(int id);

    /**
     * 利用分页工具
     * @param pageNum
     * @param pageSize
     * @return
     */
    PageInfo<User> findAllUser(int pageNum, int pageSize);
}
