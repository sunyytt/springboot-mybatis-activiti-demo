package com.example.demo.service.CriteriaTest;

import com.example.demo.model.CriteriaTest;
import com.example.demo.common.result.ResultPager;

import java.util.List;

/**
 * Created by syy on 2018/05/16.
 */
public interface CriteriaTestService {
    /**
     * 持久化
     * @param criteriaTest
     */
    void save(CriteriaTest criteriaTest);

    /**
     *批量持久化
     * @param criteriaTests
     */
    void save(List<CriteriaTest> criteriaTests);

    /**
    * 根据主鍵刪除
    * @param criteriaTest 主键不能为空
    */
    void deleteById(CriteriaTest criteriaTest);

    /**
    * 根据条件删除
    *@param criteriaTest
    */
    void deleteByCondition(CriteriaTest criteriaTest);

    /**
    * 根据ID更新
    * @param criteriaTest 主键不能为空
    */
    void updateById(CriteriaTest criteriaTest);

    /**
    * 根据条件更新
    * @param criteriaTest
    */
    void updateByCondition(CriteriaTest criteriaTest);

    /**
    * 根据ID查找
    * @param criteriaTest 主键不能为空
    * @return
    */
    CriteriaTest findById(CriteriaTest criteriaTest);

    /**
    * 分页根据条件查找
    * @param current 当前页
    * @param pageSize  显示多少条
    * @param criteriaTest 为空时查询所有
    * @return
    */
    ResultPager<CriteriaTest> selectByConditionPager(int current, int pageSize,CriteriaTest criteriaTest);

}
