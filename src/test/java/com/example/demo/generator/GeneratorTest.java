package com.example.demo.generator;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GeneratorTest {

    /**
     * 如果你的电脑上许许多多的问题（日常）导致无法使用maven插件,可以在src/main/java文件夹下创建一个test类
     * 也可以在 maven project下双击mybatis-generator插件下的mybatis-generator:generate
     * 也可以 run 一下方法
     * @throws Exception
     */

    @Test
    public void generatorTest()throws Exception {
        List<String> warnings = new ArrayList<String>();
        boolean overwrite = true;
        File configFile = new File("E:/ideaworkspace/springboot-mybatis-activiti-demo/src/main/resources/mybatis-generator.xml");
        ConfigurationParser cp = new ConfigurationParser(warnings);
        Configuration config = cp.parseConfiguration(configFile);
        DefaultShellCallback callback = new DefaultShellCallback(overwrite);
        MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
        myBatisGenerator.generate(null);
    }
}
