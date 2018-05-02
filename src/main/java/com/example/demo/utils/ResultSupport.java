package com.example.demo.utils;

import java.io.Serializable;

/**
 * 提供基础字段，包括：success，message，code
 */
public class ResultSupport implements Serializable {
    private static final long serialVersionUID = -2235152751651905167L;
    private String message;
    private String code;

}
