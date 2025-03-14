package com.example.admin.common.web;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.example.admin.common.errorcode.BaseErrorCode;
import com.example.admin.common.exception.AbstractException;
import com.example.admin.common.result.Result;
import com.example.admin.common.result.Results;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Optional;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


/**
 * @author Nruonan
 * @description
 */
@Component
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @SneakyThrows
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public Result validException(HttpServletRequest request, MethodArgumentNotValidException e){
        BindingResult result = e.getBindingResult();
        FieldError error = CollectionUtil.getFirst(result.getFieldErrors());
        String exceptionStr = Optional.ofNullable(error)
            .map(FieldError::getDefaultMessage)
            .orElse(StrUtil.EMPTY);
        log.error("[{}] {} [ex] {}", request.getMethod(), getUrl(request), exceptionStr);
        return Results.failure(BaseErrorCode.CLIENT_ERROR.code(), exceptionStr);
    }
    /**
     * 拦截应用内抛出的异常
     */
    @ExceptionHandler(value = AbstractException.class)
    public Result abortException(HttpServletRequest request, AbstractException e){
        if(e.getCause() != null){
            log.error("[{}] {} [ex] {}", request.getMethod(), request.getRequestURL().toString(), e.toString(), e.getCause());
            return Results.failure(e);
        }
        log.error("[{}] {} [ex] {}", request.getMethod(), request.getRequestURL().toString(), e.toString());
        return Results.failure(e);
    }

    /**
     * 拦截未捕获异常
     */
    @ExceptionHandler(value = Throwable.class)
    public Result defaultErrorHandler(HttpServletRequest request, Throwable throwable) {
        log.error("[{}] {} ", request.getMethod(), getUrl(request), throwable);
        return Results.failure();
    }

    private String getUrl(HttpServletRequest request){
        if (StrUtil.isEmpty(request.getQueryString())){
            return request.getRequestURL().toString();
        }
        return request.getRequestURL().toString() + "?" + request.getQueryString();
    }
}
