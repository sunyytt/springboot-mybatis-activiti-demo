package com.example.demo.Junit;

import com.example.demo.DemoApplication;
import com.example.demo.common.result.ResultPager;
import com.example.demo.model.User;
import com.example.demo.service.user.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class) // SpringJUnit支持，由此引入Spring-Test框架支持！
@SpringBootTest(classes = DemoApplication.class) // 指定我们SpringBoot工程的Application启动类
@WebAppConfiguration // 由于是Web项目，Junit需要模拟ServletContext，因此我们需要给我们的测试类加上@WebAppConfiguration。
public class JunitTest {
    //下边的方法跟controller写法一样
    @Autowired
    private UserService userService;
    private static Logger logger = LoggerFactory.getLogger(JunitTest.class);

    @Test
    public void test(){
//            ResultPager<User> pager = userService.findAllUser(1,10);
//        ResponseResult.SuccessResult(pager);
        logger.info("pager:{}","");
    }
}
