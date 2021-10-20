package com.Project.CouponProject.Login;

import com.Project.CouponProject.Beans.ClientType;
import com.Project.CouponProject.Exceptions.LoginException;
import com.Project.CouponProject.Services.AdminService;
import com.Project.CouponProject.Services.ClientService;
import com.Project.CouponProject.Services.CompanyService;
import com.Project.CouponProject.Services.CustomerService;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@NoArgsConstructor
public class LoginManager {
    /**
     * Fields of the Services so we can return them to the LoginManager Class
     */
    @Autowired
    AdminService adminService;
    @Autowired
    CompanyService companyService;
    @Autowired
    CustomerService customerService;

    /**
     * A method to give Login to the Client Type trying to log in
     * @param email we're going to check the email of the Client Type
     * @param password we're going to check the password of the Client Type
     * @param clientType The Client Type trying to log in
     * @return The Service we're trying to get by the Client Type
     * @throws LoginException Login Exception if something somewhere went terrible wrong
     */
    public ClientService login(String email, String password, ClientType clientType) throws LoginException {
        switch (clientType) {
            case ADMINISTRATOR:
                return adminService.login(email, password)?adminService:null;
            case COMPANY:
                return companyService.login(email, password)?companyService:null;
            case CUSTOMER:
                return customerService.login(email, password)?customerService:null;
            default:
                System.out.println("Something somewhere went terrible wrong -- DIKLA EDITION");
                return null;
        }
    }
}
