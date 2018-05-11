package com.example.demo.Junit.workflow;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;

public class UserIdTaskListenerImpl implements TaskListener {
    @Override
    public void notify(DelegateTask delegateTask) {
        //指定个人任务的办理人，也可以指定组任务的办理人
        //个人任务：通过类查询数据库，将下一个任务的办理人查询获取
        // 根据业务查询，比如查到了"郭襄"
        //然后通过setAssignee()的方法指定任务的办理人
        delegateTask.setAssignee("杨过");
    }
}
