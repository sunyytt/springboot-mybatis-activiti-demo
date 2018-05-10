package com.example.demo.Junit.workflow;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;

import java.util.ArrayList;
import java.util.List;

public class ProMangerTaskListenerImpl implements TaskListener {
    @Override
    public void notify(DelegateTask delegateTask) {
        List<String> list = new ArrayList<String>();
        list.add("Pro1");
        list.add("Pro2");
        delegateTask.addCandidateUsers(list);

    }
}
