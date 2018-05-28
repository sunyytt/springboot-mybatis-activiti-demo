package com.example.demo.service.workflow.impl;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;

public class ApplyImpl implements TaskListener {
    @Override
    public void notify(DelegateTask delegateTask) {

        delegateTask.setAssignee("");
    }
}
