package com.example.demo.controller;

import com.example.demo.common.result.ResultPager;
import com.example.demo.model.FlLogs;
import com.example.demo.service.workflow.WorkFlowService;
import com.example.demo.utils.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/flow")
public class WorkFlowController {
    @Autowired
    WorkFlowService workFlowService;

    @PostMapping
    public Object add(@RequestBody FlLogs flLogs){
        workFlowService.save(flLogs);
        return ResponseResult.SuccessResult();
    }

    @PostMapping("/1")
    public Object apply(@RequestBody FlLogs flLogs){
        flLogs = workFlowService.apply(flLogs);
        return ResponseResult.SuccessResult(flLogs);
    }

    @PostMapping("/2")
    public Object approves(@RequestBody FlLogs flLogs){
       workFlowService.approves(flLogs);
       return ResponseResult.SuccessResult();
    }

    @PostMapping("/3")
    public Object queryMyTask(
            @PathVariable(value="current")  int current,
            @PathVariable(value="pageSize")  int pageSize,
            @RequestBody FlLogs flLogs ){
        ResultPager<FlLogs> pager = workFlowService.queryMyTask(current,pageSize,flLogs);
        return ResponseResult.SuccessResult(pager);
    }

    @PostMapping("/4")
    public Object queryMyApproves(
            @PathVariable(value="current",required = true)  int current,
            @PathVariable(value="pageSize",required = true)  int pageSize,
            @PathVariable(value="role",required = true)  String role,
            @RequestBody FlLogs flLogs ){

        ResultPager<FlLogs> pager = workFlowService.queryMyApproves(current,pageSize,flLogs,role);
        return ResponseResult.SuccessResult(pager);
    }


}
