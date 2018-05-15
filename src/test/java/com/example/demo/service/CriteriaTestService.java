package com.example.demo.service;

import com.example.demo.dao.CriteriaTestMapper;
import com.example.demo.model.CriteriaTest;
import com.example.demo.model.CriteriaTestExample;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CriteriaTestService {

    private static Logger logger = LoggerFactory.getLogger(CriteriaTestService.class);

    @Autowired
    CriteriaTestMapper criteriaTestMapper;

    @Test
    public void selectByCondition(){
        CriteriaTestExample criteriaTestExample = new CriteriaTestExample();
        criteriaTestExample.createCriteria().andUserNameEqualTo("username");
        criteriaTestExample.setOrderByClause("creat_date asc");
        List<CriteriaTest> criteriaTests= criteriaTestMapper.selectByExample(criteriaTestExample);
        for(CriteriaTest criteriaTest :criteriaTests){
            logger.info("UserName:{}",criteriaTest.getUserName());
            logger.info("email:{}",criteriaTest.getEmail());
            logger.info("CreatDate:{}",criteriaTest.getCreatDate());

        }
        logger.info("size:{}",criteriaTests.size());
        logger.info("email:{}",criteriaTests.get(0).getEmail());

    }

    @Test
    public void save(){
        CriteriaTest criteriaTest = new CriteriaTest();
        String id = UUID.randomUUID().toString();

        criteriaTest.setId(id);
        criteriaTest.setUserName("username");
        criteriaTest.setEmail("122222@qq.com");
        criteriaTest.setCreatDate(new Date());
        criteriaTestMapper.insertSelective(criteriaTest);
    }

    @Test
    public void deleteById(){
        CriteriaTestExample criteriaTestExample = new CriteriaTestExample();
        criteriaTestExample.createCriteria().andIdEqualTo("1");
        criteriaTestMapper.deleteByExample(criteriaTestExample);
    }

    @Test
    public void findById (){
        CriteriaTest criteriaTest = criteriaTestMapper.selectByPrimaryKey("2");
        logger.info("email:{}",criteriaTest.getEmail());
    }

    @Test
    public void updateById(){
        CriteriaTest criteriaTest =  new CriteriaTest();
        criteriaTest.setId("2");
        criteriaTest.setEmail("43343434");
        criteriaTestMapper.updateByPrimaryKeySelective(criteriaTest);
    }

}
