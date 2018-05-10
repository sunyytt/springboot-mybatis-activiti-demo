package com.example.demo.Junit.workflow;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;

import java.util.ArrayList;
import java.util.List;

public class DepTaskListenerImpl implements TaskListener {
    @Override
    public void notify(DelegateTask delegateTask) {
        //怎么传值？
        /**
         * 再啰嗦一下，如果是设置用户组的话，这里应该是delegateTask.addCandidateGroup("某组");
         * 设置流程变量值：delegateTask.setVariable("inputName", "小明");//inputName流程变量名
         */
        List<String> list = new ArrayList<String>();
        list.add("dep1");
        list.add("dep2");
        delegateTask.addCandidateUsers(list);

    }
}
