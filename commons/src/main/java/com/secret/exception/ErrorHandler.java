package com.secret.exception;

import com.secret.constant.RS;
import com.secret.vo.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.secret.vo.R.fail;


@RestControllerAdvice
@Slf4j
public class ErrorHandler {


    @ExceptionHandler(value = {
            MyException.class})
    public R<Boolean> handleRuntimeException(MyException ex)  {
        return fail(ex.getStatus(),ex.getMessage());
    }

    @ExceptionHandler(value = {
            IllegalArgumentException.class})
    public R<Boolean> handleIllegalArgumentException(Exception ex)  {
        log.error("assert error",ex);
        return fail(RS.SYSTEM_ERROR.status(),ex.getMessage());
    }

    @ExceptionHandler(value = {
            Exception.class})
    public R<Boolean> handleRuntimeException(Exception ex)  {
        log.error("aaa",ex);
        return R.fail();
    }


}