package com.Project.CouponProject.Advice;

import com.Project.CouponProject.Exceptions.AdminException;
import com.Project.CouponProject.Exceptions.CompanyException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ControllerAdvice
public class CompanyRestException {
    @ExceptionHandler(value = {CompanyException.class})
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ErrorDetail handleException(Exception error){
        return new ErrorDetail("Something somewhere went terrible wrong --- COMPANY EDITION", error.getMessage());
    }
}
