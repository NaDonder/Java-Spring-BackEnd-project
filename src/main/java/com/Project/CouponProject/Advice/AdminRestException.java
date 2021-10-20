package com.Project.CouponProject.Advice;

import com.Project.CouponProject.Exceptions.AdminException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ControllerAdvice
public class AdminRestException {

    @ExceptionHandler(value = {AdminException.class})
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ErrorDetail handleException(Exception error){
        return new ErrorDetail("Something somewhere went terrible wrong --- ADMIN EDITION", error.getMessage());
    }
}

