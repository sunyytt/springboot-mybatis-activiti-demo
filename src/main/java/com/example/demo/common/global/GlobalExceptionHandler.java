package com.example.demo.common.global;

import com.example.demo.common.result.ResultCode;
import com.example.demo.utils.ResponseResult;
import org.apache.shiro.ShiroException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;


@RestControllerAdvice//控制器增强  包含@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     *捕获异常时加入日志
     */
//    @ExceptionHandler(value = Exception.class)
//    public void handleGlobalException(HttpServletRequest req, Exception ex) {
//        ByteArrayOutputStream buf = new ByteArrayOutputStream();
//        ex.printStackTrace(new java.io.PrintWriter(buf, true));
//        String  expMessage = buf.toString();
//        try {
//            buf.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        log.error("捕获异常:{};eString:{}",ex.getMessage(), expMessage);
//    }

    // 捕捉shiro的异常
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(ShiroException.class)
    public Object handle401(ShiroException e) {
        return ResponseResult.FailResult(ResultCode.UNAUTHORIZED,"shiro的异常");
    }

    // 捕捉UnauthorizedException
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(UnauthorizedException.class)
    public Object handle401() {
        return ResponseResult.FailResult(ResultCode.UNAUTHORIZED,"UnauthorizedException");
    }

    // 捕捉其他所有异常
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Object globalException(HttpServletRequest request, Throwable ex) {
        return ResponseResult.FailResult(ResultCode.FAIL,ex.getMessage());
//        return new ResponseBean(getStatus(request).value(), ex.getMessage(), null);
    }

    private HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return HttpStatus.valueOf(statusCode);
    }
}
