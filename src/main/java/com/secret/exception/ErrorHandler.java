package com.secret.exception;

import com.secret.constant.RS;
import com.secret.model.vo.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
@Slf4j
public class ErrorHandler {

    @ExceptionHandler(value = {
            MyException.class})
    public R handleRuntimeException(MyException ex)  {
        return R.fail(ex.getStatus(),ex.getMessage());
    }

    @ExceptionHandler(value = {
            IllegalArgumentException.class})
    public R handleIllegalArgumentException(Exception ex)  {
        log.error("assert error",ex);
        return R.fail(RS.SYSTEM_ERROR.status(),ex.getMessage());
    }

    @ExceptionHandler(value = {
            Exception.class})
    public R handleRuntimeException(Exception ex)  {
        log.error("aaa",ex);
        return R.fail();
    }


}