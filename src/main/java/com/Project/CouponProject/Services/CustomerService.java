package com.Project.CouponProject.Services;

import com.Project.CouponProject.Beans.Category;
import com.Project.CouponProject.Beans.Coupon;
import com.Project.CouponProject.Beans.Customer;
import com.Project.CouponProject.Exceptions.CouponException;
import com.Project.CouponProject.Exceptions.CustomerException;
import com.Project.CouponProject.Exceptions.LoginException;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerService extends ClientService{
    /**
     * field for getting the Customer that is logged in
     */
    private int customerID;

    //--------------------------- LOGIN TO THE SYSTEM ----------------------------------

    /**
     * a method to login to the system by the customer
     * @param email for validate the user email
     * @param password for validate the user password
     * @return true or false for the login Manager for the service
     * @throws LoginException throws exception if invalid useremail or password
     */
    @Override
    public boolean login(String email, String password) throws LoginException {
        if (customerRepo.existsCustomerByEmailAndPassword(email, password)) {
            customerID = customerRepo.findByEmailAndPassword(email, password).getId();
            return true;
        } else {
            throw new LoginException("!!!!! ERROR ACQUIRED: !!!!!" + "\n" +
                    "Invalid username or password");
        }
    }

    //----------------------- PURCHASE COUPON --------------------------------

    /**
     * a method for purchasing coupons
     * @param coupon the coupon we're trying to purchase
     * @throws CustomerException if: doesn't exist by ID, customer already have the coupon, amount 0, out of date.
     */
    public void purchaseCoupon(Coupon coupon) throws CustomerException {
        if (!couponRepo.existsById(coupon.getId())) {
            throw new CustomerException("!!!!! ERROR ACQUIRED: !!!!!" + "\n" +
                    "coupon id does not exist");
        }
        Coupon testCoupon = couponRepo.getOne(coupon.getId());
        if (getCustomerCoupons().contains(testCoupon)) {
            throw new CustomerException("!!!!! ERROR ACQUIRED: !!!!!" + "\n" +
                    "customer already has coupon");
        }
        if (testCoupon.getAmount() <= 0) {
            throw new CustomerException("!!!!! ERROR ACQUIRED: !!!!!" + "\n" +
                    "coupon amount 0");
        }
        if (testCoupon.getEndDate().before(Date.valueOf(LocalDate.now()))) {
            throw new CustomerException("!!!!! ERROR ACQUIRED: !!!!!" + "\n" +
                    "coupon is out of date");
        }
        Customer customer = getCustomerDetails();
        customer.purchaseCoupon(coupon);
        customerRepo.saveAndFlush(customer);
        coupon.setAmount(coupon.getAmount() - 1);
        couponRepo.saveAndFlush(coupon);
    }

    //----------------------- GET CUSTOMER COUPONS -----------------------

    /**
     * a method to get all the coupons of the customer that is logged in right now
     * @return all the coupons of the customer
     */
    public List<Coupon> getCustomerCoupons(){
        return customerRepo.getOne(this.customerID).getCoupons();
    }

    //----------------- GET CUSTOMER COUPONS BY CATEGORY ------------------

    /**
     * a method to get all coupons of the customer that is logged in by category
     * @param category the category we're searching for
     * @return returns the coupons of the same category
     */
    public List<Coupon> getCustomerCouponsByCategory(Category category) {
        return customerRepo.getOne(this.customerID).getCoupons().stream().filter(item -> item.getCategory().equals(category)).collect(Collectors.toList());
    }

    //----------------- GET CUSTOMER COUPONS BY CATEGORY ------------------

    /**
     * a method to get all coupons of the customer that is logged in by max Price
     * @param maxPrice the max price we're searching for
     * @return returns the coupons by the max price selected
     */
    public List<Coupon> getCustomerCouponsByMaxPrice(double maxPrice) {
        return customerRepo.getOne(this.customerID).getCoupons().stream().filter(item -> item.getPrice() <= maxPrice).collect(Collectors.toList());
    }

    //----------------------- GET CUSTOMER DETAILS -----------------------

    /**
     * a method to get the logged in customer details
     * @return the customer details
     */
    public Customer getCustomerDetails(){
        return customerRepo.findById(this.customerID);
    }

    //------------------------- GET ONE COUPON -------------------------------

    /**
     * a method to get a single coupon
     * @param couponId finding the coupon by the ID
     * @return returns the coupon that found by the ID
     * @throws CouponException throws exception if coupon doesn't exist
     */
    public Coupon getOneCoupon(int couponId) throws CouponException{
        if (!couponRepo.existsById(couponId)){
            throw new CouponException("!!!!! ERROR ACQUIRED: !!!!!" + "\n" +
                    "Coupon ID Does not exist");
        } else {
            return couponRepo.findById(couponId);
        }
    }
}
