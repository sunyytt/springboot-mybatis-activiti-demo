package com.example.demo.Junit.workflow;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;

import java.util.ArrayList;
import java.util.List;

public class ProMangerTaskListenerImpl implements TaskListener {
    @Override
    public void notify(DelegateTask delegateTask) {
        List<String> list = new ArrayList<String>();
        list.add("Pro11");
        list.add("Pro21");
        delegateTask.addCandidateUsers(list);

    }
}
