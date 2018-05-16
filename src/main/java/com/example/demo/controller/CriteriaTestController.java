package com.example.demo.controller;

import com.example.demo.common.result.*;
import com.example.demo.utils.ResponseResult;
import com.example.demo.model.CriteriaTest;
import com.example.demo.service.CriteriaTest.CriteriaTestService;
import org.springframework.web.bind.annotation.*;

import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

/**
* Created by syy on 2018/05/16.
*/
@RestController
@RequestMapping("/criteria/test")
public class CriteriaTestController {
    @Autowired
    private CriteriaTestService criteriaTestService;

    @PostMapping
    public Result add(@RequestBody CriteriaTest criteriaTest) {
        criteriaTestService.save(criteriaTest);
        return ResponseResult.SuccessResult();
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable String id) {
        CriteriaTest criteriaTest = new CriteriaTest();
        criteriaTest.setId(id);
        criteriaTestService.deleteById(criteriaTest);
        return ResponseResult.SuccessResult();
    }

    @PutMapping
    public Result update(@RequestBody CriteriaTest criteriaTest) {
        criteriaTestService.updateById(criteriaTest);
        return ResponseResult.SuccessResult();
    }

    @GetMapping("/{id}")
    public Result detail(@PathVariable String id) {
        CriteriaTest criteriaTest = new CriteriaTest();
        criteriaTest.setId(id);
        CriteriaTest result = criteriaTestService.findById(criteriaTest);
        return ResponseResult.SuccessResult(result);
    }

    @GetMapping
    public Result list(@RequestParam(name = "current", required = false, defaultValue = "1")
                    int current,
            @RequestParam(name= "pageSize",required = false,defaultValue ="10" )
                    int pageSize,
            CriteriaTest criteriaTest) {

        ResultPager<CriteriaTest> pager = criteriaTestService.selectByConditionPager(current,pageSize,criteriaTest);
        return ResponseResult.SuccessResult(pager);
    }
}
