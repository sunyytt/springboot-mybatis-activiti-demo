package com.example.demo.service.CriteriaTest.impl;

import com.example.demo.dao.CriteriaTestMapper;
import com.example.demo.model.CriteriaTest;
import com.example.demo.model.CriteriaTestExample;
import com.example.demo.service.CriteriaTest.CriteriaTestService;
import com.example.demo.common.result.ResultPager;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by syy on 2018/05/16.
 */
@Service
@Transactional
public class CriteriaTestServiceImpl implements CriteriaTestService {

    @Autowired
    private CriteriaTestMapper criteriaTestMapper;


    public void save(CriteriaTest criteriaTest){
        criteriaTestMapper.insertSelective(criteriaTest);
    }

    public void save(List<CriteriaTest> criteriaTests){}

    public void deleteById(CriteriaTest criteriaTest){
        criteriaTestMapper.deleteByPrimaryKey(criteriaTest.getId());
    }

    public void deleteByCondition(CriteriaTest criteriaTest){
        CriteriaTestExample criteriaTestExample = new CriteriaTestExample();
        //like criteriaTestExample.createCriteria().andIdEqualTo("1");
        criteriaTestMapper.deleteByExample(criteriaTestExample);
    }

    public void updateById(CriteriaTest criteriaTest){
        criteriaTestMapper.updateByPrimaryKeySelective(criteriaTest);
    }

    public void updateByCondition(CriteriaTest criteriaTest){
        //与deleteByCondition 类似
    }

    public CriteriaTest findById(CriteriaTest criteriaTest){
            return criteriaTestMapper.selectByPrimaryKey(criteriaTest.getId());
    }

    public ResultPager<CriteriaTest> selectByConditionPager(int current, int pageSize,CriteriaTest criteriaTest){
        ResultPager.setPager(current,pageSize);
        CriteriaTestExample criteriaTestExample = new CriteriaTestExample();
        //like criteriaTestExample.createCriteria().andIdEqualTo("1");
        List<CriteriaTest> list = criteriaTestMapper.selectByExample(criteriaTestExample);
        ResultPager<CriteriaTest> pager = new ResultPager<CriteriaTest>(list);
        return pager;
    }




}