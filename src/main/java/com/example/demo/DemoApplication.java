package com.example.demo;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.TaskService;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 说明：
 * 注意需要加注解@SpringBootApplication，这标识为它是一个启动类。
 * @MapperScan是扫描mapper接口的，当然也可以在每一个dao接口上去加@Mapper，二者作用相同。
 * SpringApplication.run(DemoApplication.class, args);//这行代码就是启动应用的，以当前类为参数，后面一个参数可填可不填。
 * 注意事项：
 * 1、注意activiti-spring-boot-starter-basic的版本号，推荐使用5.21.0。如果使用的5.17的版本，在启动流程的时候，
 *  不会自动帮你部署，因此在这之前需要自己手动的部署一次。5.21.0版本的话就不会出现这个问题。
 * 2、注意启动流程引擎的时候传入的key要和流程配置文件中的id保持一致。（以xml文件格式打开查看）
 */
//@MapperScan("com.example.demo.dao")  DataSourceConfig 中多次扫描了
@SpringBootApplication
public class DemoApplication {
    private static Logger logger = LoggerFactory.getLogger(DemoApplication.class);

    @Autowired
    private TaskService taskService;
    @Autowired
    private ProcessEngine processEngine;

    @RequestMapping("/")
    public String index() {
        System.out.println("------------taskService:>>>>>>" + taskService);
        System.out.println("------------processEngine:>>>>>>" + processEngine);
        return "Hello world";
    }

    public static void main(String[] args) {
        logger.info("应用启动----------------------------");
        SpringApplication.run(DemoApplication.class, args);
    }
}
