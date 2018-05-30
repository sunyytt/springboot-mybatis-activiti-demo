package com.example.demo.service.workflow.impl;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;

import java.util.Arrays;
import java.util.List;

public class ApproveImpl implements TaskListener {
    @Override
    public void notify(DelegateTask delegateTask) {
        System.out.println("ApproveImpl");
        List<String> roles = (List<String>) delegateTask.getVariable("roles");
        roles.forEach(r-> System.out.println(r));
        delegateTask.addCandidateUsers(roles);
    }
}
