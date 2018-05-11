package com.example.demo.generator.SpringBootTest;

import com.example.demo.Junit.JunitTest;
import com.example.demo.common.result.ResultPager;
import com.example.demo.model.User;
import com.example.demo.service.user.UserService;
import com.example.demo.utils.ResponseResult;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BootTest {

    /**
     *
     *SpringBoot 自带单元测试
     *
     */
    @Autowired
    private UserService userService;
    private static Logger logger = LoggerFactory.getLogger(JunitTest.class);

    @Test
    public void test(){
        ResultPager<User> pager = userService.findAllUser(1,10);
        ResponseResult.SuccessResult(pager);
        logger.info("pager:{}",pager.getList().size());
    }
}
