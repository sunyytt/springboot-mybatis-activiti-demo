package com.example.demo.utils;



import com.example.demo.common.result.Result;
import com.example.demo.common.result.ResultCode;

import java.util.ArrayList;

/**
 * 响应结果生成工具 controller层
 */
public class ResponseResult {
    private static final String DEFAULT_SUCCESS_MESSAGE = "SUCCESS";

    public static Result SuccessResult() {
        return new Result()
                .setCode(ResultCode.SUCCESS)
                .setMessage(DEFAULT_SUCCESS_MESSAGE);
    }

    public static Result SuccessResult(Object data) {
        return new Result()
                .setCode(ResultCode.SUCCESS)
                .setMessage(DEFAULT_SUCCESS_MESSAGE)
                .setData(data);
    }

    public static Result FailResult(String message) {
        return new Result()
                .setCode(ResultCode.FAIL)
                .setMessage(message)
                .setData(new ArrayList<>());
    }
    public static Result FailResult(ResultCode resultCode,String message) {
        return new Result()
                .setCode(resultCode)
                .setMessage(message)
                .setData(new ArrayList<>());
    }
}
