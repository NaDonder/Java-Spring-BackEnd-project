package com.Project.CouponProject.Services;

import com.Project.CouponProject.Exceptions.LoginException;
import com.Project.CouponProject.Repositories.CompanyRepo;
import com.Project.CouponProject.Repositories.CouponRepo;
import com.Project.CouponProject.Repositories.CustomerRepo;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class ClientService {

    @Autowired
    protected CompanyRepo companyRepo;
    @Autowired
    protected CustomerRepo customerRepo;
    @Autowired
    protected CouponRepo couponRepo;

    public abstract boolean login(String email, String password) throws LoginException;

}
