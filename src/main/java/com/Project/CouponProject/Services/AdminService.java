package com.Project.CouponProject.Services;

import com.Project.CouponProject.Beans.Company;
import com.Project.CouponProject.Beans.Coupon;
import com.Project.CouponProject.Beans.Customer;
import com.Project.CouponProject.Exceptions.CompanyException;
import com.Project.CouponProject.Exceptions.CustomerException;
import com.Project.CouponProject.Exceptions.LoginException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService extends ClientService {

    //--------------------------- LOGIN TO THE SYSTEM ----------------------------------

    /**
     * a method to login to system by the administrator
     *
     * @param email    for validate the user email
     * @param password for validate the user password
     * @return true or false for the LoginManager Class for the Service
     * @throws LoginException If something somewhere went terrible wrong
     */
    @Override
    public boolean login(String email, String password) throws LoginException {
        if (email.equals("admin@admin.com") && password.equals("admin")) {
            return true;
        } else {
            throw new LoginException("!!!!! ERROR ACQUIRED: !!!!!" + "\n" +
                    "Invalid user name or password");
        }
    }

    //--------------------------------- ADDING COMPANY ----------------------------------

    /**
     * a method to add company to the system
     *
     * @param company the company we want to add
     * @throws CompanyException if company exists by email or name
     */
    public void addCompany(Company company) throws CompanyException {
        if (companyRepo.existsCompanyByNameOrEmail(company.getName(), company.getEmail())) {
            throw new CompanyException("!!!!! ERROR ACQUIRED: !!!!!" + "\n" +
                    "company already exist with email or name");
        }
        companyRepo.save(company);
    }

    //--------------------------------- UPDATING COMPANY ---------------------------------------

    /**
     * a method to update the company from the system
     *
     * @param company the company we're trying to update
     * @throws CompanyException Throws Exception if ID does not exist or same Name
     */
    public void updateCompany(Company company) throws CompanyException {
        if (!companyRepo.existsById(company.getId())) {
            throw new CompanyException("!!!!! ERROR ACQUIRED: !!!!!" + "\n" +
                    "company ID does not exist");
        }
        Company updatingCompany = companyRepo.getOne(company.getId());
        if (updatingCompany.getName().equals(company.getName())) {
            companyRepo.saveAndFlush(company);
        } else {
            throw new CompanyException("!!!!! ERROR ACQUIRED: !!!!!" + "\n" +
                    "can not change company name");
        }
    }

    //------------------------------- DELETING COMPANY ---------------------------------

    /**
     * a method to delete company
     *
     * @param id deleting by ID
     * @throws CompanyException throws exception if didn't find by ID
     */
    public void deleteCompany(int id) throws CompanyException {
        if (!companyRepo.existsById(id)) {
            throw new CompanyException("!!!!! ERROR ACQUIRED: !!!!!" + "\n" +
                    "company ID does not exist");
        }
        Company toDelete = companyRepo.getOne(id);
        for (Coupon coupon: couponRepo.findAll()) {
            if (toDelete.getId() == coupon.getCompanyID()){
                for (Customer customer: customerRepo.findAll()) {
                    if (customer.getCoupons().contains(coupon)) {
                        customer.removeCoupon(coupon);
                        customerRepo.saveAndFlush(customer);
                    }
                }
            }
        }
        companyRepo.deleteById(id);
    }

    //----------------------------- GET ALL COMPANIES --------------------------------

    /**
     * a method to get all the Companies
     *
     * @return all the companies
     */
    public List<Company> getAllCompanies() {
        return companyRepo.findAll();
    }

    //---------------------------- GETTING ONE COMPANY ---------------------------------

    /**
     * a method to get company
     *
     * @param id finding by ID
     * @return The company
     * @throws CompanyException throws exception if didn't found by ID
     */
    public Company getOneCompany(int id) throws CompanyException {
        if (!companyRepo.existsById(id)) {
            throw new CompanyException("!!!!! ERROR ACQUIRED: !!!!!" + "\n" +
                    "company ID does not exist");
        } else {
            return companyRepo.findById(id);
        }
    }

    //----------------------------- ADD CUSTOMER -------------------------------------

    /**
     * a method to add Customer
     *
     * @param customer the customer we're adding
     * @throws CustomerException throws exception if customer exists by email
     */
    public void addCustomer(Customer customer) throws CustomerException {
        if (customerRepo.existsCustomerByEmail(customer.getEmail())) {
            throw new CustomerException("!!!!! ERROR ACQUIRED: !!!!!" + "\n" +
                    "Customer already exists by email");
        } else {
            customerRepo.save(customer);
        }
    }

    //--------------------------- UPDATING CUSTOMER -----------------------------------

    /**
     * a method to update Customer
     *
     * @param customer the customer we're updating
     * @throws CustomerException throws exception if didn't find by ID
     */
    public void updateCustomer(Customer customer) throws CustomerException {
        if (!customerRepo.existsById(customer.getId())) {
            throw new CustomerException("!!!!! ERROR ACQUIRED: !!!!!" + "\n" +
                    "Customer ID does not exist");
        }
        Customer updatingCustomer = customerRepo.findById(customer.getId());
        if (updatingCustomer.getEmail().equals(customer.getEmail())) {
            customerRepo.saveAndFlush(customer);
        } else {
            throw new CustomerException("!!!!! ERROR ACQUIRED: !!!!!" + "\n" +
                    "Customer already exists by email");
        }
    }

    //------------------------- DELETING CUSTOMER ----------------------------------

    /**
     * a method to delete customer
     *
     * @param id deleting by ID
     * @throws CustomerException throws exception if didn't find by ID
     */
    public void deleteCustomer(int id) throws CustomerException {
        if (!customerRepo.existsById(id)) {
            throw new CustomerException("!!!!! ERROR ACQUIRED: !!!!!" + "\n" +
                    "Customer ID does not exist");
        } else {
            Customer toDelete = customerRepo.getOne(id);
            for (Coupon coupon: couponRepo.findAll()) {
                if (toDelete.getCoupons().contains(coupon)) {
                    toDelete.removeCoupon(coupon);
                    customerRepo.saveAndFlush(toDelete);
                }
            }
            customerRepo.deleteById(id);
        }
    }

    //---------------------- GET ALL CUSTOMERS ------------------------------

    /**
     * a method to get all customer
     *
     * @return all customers
     */
    public List<Customer> getAllCustomer() {
        return customerRepo.findAll();
    }

    //-------------------- GETTING ONE CUSTOMER --------------------------------

    /**
     * a method to get one customer
     *
     * @param id finding by ID
     * @return the customer
     * @throws CustomerException throws if didn't find by ID
     */
    public Customer getOneCustomer(int id) throws CustomerException {
        if (!customerRepo.existsById(id)) {
            throw new CustomerException("!!!!! ERROR ACQUIRED: !!!!!" + "\n" +
                    "Customer ID does not exist");
        } else {
            return customerRepo.findById(id);
        }
    }
}
