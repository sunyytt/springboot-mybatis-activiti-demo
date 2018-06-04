package com.example.demo.controller;

import com.example.demo.common.result.ResultPager;
import com.example.demo.model.FlLogs;
import com.example.demo.service.workflow.WorkFlowService;
import com.example.demo.utils.ResponseResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/flow")
public class WorkFlowController {
    @Autowired
    WorkFlowService workFlowService;

    @PostMapping
    public Object add(@RequestBody FlLogs flLogs){
        if(StringUtils.isEmpty(flLogs.getId())){
            workFlowService.save(flLogs);
        }else{
            workFlowService.update(flLogs);
        }
        return ResponseResult.SuccessResult();
    }

    @PostMapping("/1")
    public Object apply(@RequestBody FlLogs flLogs){
        flLogs = workFlowService.apply(flLogs);
        return ResponseResult.SuccessResult();
    }

    @PostMapping("/2")
    public Object approves(@RequestBody FlLogs flLogs){
       workFlowService.approves(flLogs);
       return ResponseResult.SuccessResult();
    }

    @PostMapping("/{current}/{pageSize}")
    public Object queryMyTask(
            @PathVariable(value="current",required = true)  int current,
            @PathVariable(value="pageSize",required = true)  int pageSize,
            @RequestBody FlLogs flLogs ){
        ResultPager<FlLogs> pager = workFlowService.queryMyTask(current,pageSize,flLogs);
        return ResponseResult.SuccessResult(pager);
    }

    @PostMapping("/{current}/{pageSize}/{role}")
    public Object queryMyApproves(
            @PathVariable(value="current",required = true)  int current,
            @PathVariable(value="pageSize",required = true)  int pageSize,
            @PathVariable(value="role",required = true)  String role,
            @RequestBody FlLogs flLogs ){

        ResultPager<FlLogs> pager = workFlowService.queryMyApproves(current,pageSize,flLogs,role);
        return ResponseResult.SuccessResult(pager);
    }

    @GetMapping("/{id}")
    public Object detail( @PathVariable(value="id",required = true) String id){
        FlLogs flLogs = workFlowService.getFlLogsById(id);
        return ResponseResult.SuccessResult(flLogs);
    }

    @DeleteMapping("/{id}")
    public Object delete(@PathVariable(value="id",required = true) String id){
        workFlowService.delete(id);
        return ResponseResult.SuccessResult();
    }


}
