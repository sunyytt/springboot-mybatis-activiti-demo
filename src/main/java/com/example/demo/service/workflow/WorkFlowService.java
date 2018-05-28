package com.example.demo.service.workflow;

import com.example.demo.common.result.ResultPager;
import com.example.demo.model.FlLogs;
import com.example.demo.model.WorkFlow;

import java.util.List;

public interface WorkFlowService {
    void save(FlLogs flLogs);
    FlLogs apply(FlLogs flLogs);
    void approves(FlLogs flLogs);
    ResultPager<FlLogs> queryMyTask(int current, int pageSize, FlLogs flLogs);
    ResultPager<FlLogs> queryMyApproves(int current, int pageSize, FlLogs flLogs,String role);


//    void start(WorkFlow workFlow);
//
//    List<WorkFlow> queryFlowByUserId(String userId);
//
//    void updateById(WorkFlow workFlow);
}
