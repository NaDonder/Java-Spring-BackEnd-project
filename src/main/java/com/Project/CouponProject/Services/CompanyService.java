package com.Project.CouponProject.Services;

import com.Project.CouponProject.Beans.Category;
import com.Project.CouponProject.Beans.Company;
import com.Project.CouponProject.Beans.Coupon;
import com.Project.CouponProject.Beans.Customer;
import com.Project.CouponProject.Exceptions.CouponException;
import com.Project.CouponProject.Exceptions.LoginException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CompanyService extends ClientService {
    /**
     * field for getting the Company that is logged in
     */
    private int companyID;

    //--------------------------- LOGIN TO THE SYSTEM ----------------------------------

    /**
     * a method to login to the system by the company
     *
     * @param email    for validate the user email
     * @param password for validate the user password
     * @return true of false for the Login Manager for the service
     * @throws LoginException throws exception if invalid useremail or password
     */
    @Override
    public boolean login(String email, String password) throws LoginException {
        if (companyRepo.existsCompanyByEmailAndPassword(email, password)) {
            companyID = companyRepo.findByEmailAndPassword(email, password).getId();
            return true;
        } else {
            throw new LoginException("!!!!! ERROR ACQUIRED: !!!!!" + "\n" +
                    "Invalid username or password");
        }
    }

    //---------------------- ADDING COUPON -------------------------------

    /**
     * a method to add coupon to the company
     *
     * @param coupon the coupon we're adding
     * @throws CouponException throws exception if same title or same coupon for the same company
     */
    public void addCoupon(Coupon coupon) throws CouponException {
        if (couponRepo.existsCouponByCompanyIDAndTitle(this.companyID, coupon.getTitle())) {
            throw new CouponException("!!!!! ERROR ACQUIRED: !!!!!" + "\n" +
                    "You cannot add coupon with the same name to the same company");
        } else {
            Company company = companyRepo.getOne(this.companyID);
            company.addCoupon(coupon);
            companyRepo.saveAndFlush(company);
        }
    }

    //------------------------- UPDATING COUPON ----------------------------

    /**
     * a method to update coupon
     *
     * @param coupon the coupon were updating
     * @throws CouponException throws exception if didn't find ID or doesn't belong to the company
     */
    public void updateCoupon(Coupon coupon) throws CouponException {
        if (!couponRepo.existsById(coupon.getId())) {
            throw new CouponException("Coupon Id Does not exist\n" +
                    "!!! ERROR ACQUIRED !!!");
        }
        if (coupon.getCompanyID() != companyID) {
            throw new CouponException("Coupon does not belong to the company!\n" +
                    "!!! ERROR ACQUIRED !!!");
        }
        couponRepo.saveAndFlush(coupon);
    }

    //------------------------- DELETING COUPON ------------------------------

    /**
     * Method to delete coupon
     * deleting coupon will delete the coupon from the Customer coupon list
     * @param couponId deleting by coupon ID
     * @throws CouponException throws exception if didn't find by ID or the coupon doesn't belong to the company
     */
    public void deleteCoupon(int couponId) throws CouponException {
        if (!couponRepo.existsById(couponId)) {
            throw new CouponException("Coupon ID Does not exist\n" +
                    "!!! ERROR ACQUIRED !!!");
        }
        Coupon coupon = couponRepo.getOne(couponId);
        if (coupon.getCompanyID() != companyID) {
            throw new CouponException("Coupon does not belong to the company!\n" +
                    "!!! ERROR ACQUIRED !!!");
        }
        for (Customer customer : customerRepo.findAll()) {
            if (customer.getCoupons().contains(coupon)) {
                customer.removeCoupon(coupon);
                customerRepo.saveAndFlush(customer);
            }
        }
        Company company = companyRepo.getOne(companyID);
        company.removeCoupon(coupon);
        companyRepo.saveAndFlush(company);
        couponRepo.delete(coupon);
    }

    //------------------------- GET ONE COUPON -------------------------------

    /**
     * a method to get a single coupon
     *
     * @param couponId finding the coupon by the ID
     * @return returns the coupon that found by the ID
     * @throws CouponException throws exception if coupon doesn't exist
     */
    public Coupon getOneCoupon(int couponId) throws CouponException {
        if (!couponRepo.existsById(couponId)) {
            throw new CouponException("!!!!! ERROR ACQUIRED: !!!!!" + "\n" +
                    "Coupon ID Does not exist");
        } else {
            return couponRepo.findById(couponId);
        }
    }

    //--------------------- GET COMPANY COUPONS -----------------------------

    /**
     * a method to get all company's coupons
     *
     * @return all logged in company coupons
     */
    public List<Coupon> getCompanyCoupons() {
        return companyRepo.getOne(this.companyID).getCoupons();
    }

    //-------------------- GET COMPANY COUPONS BY CATEGORY ----------------------

    /**
     * a method to get all company coupons
     *
     * @param category finding by category
     * @return all logged in company coupons
     */
    public List<Coupon> getCompanyCouponsByCategory(Category category) {
        return companyRepo.getOne(this.companyID).getCoupons().stream().filter(item -> item.getCategory().equals(category)).collect(Collectors.toList());
    }

    //------------------ GET COMPANY COUPONS BY MAX PRICE -------------------

    /**
     * a method to get all company coupons
     *
     * @param maxPrice finding by maxPrice
     * @return all logged in company coupons
     */
    public List<Coupon> getCompanyCouponsByMaxPrice(double maxPrice) {
        //return couponRepo.findByPriceLessThanEqual(maxPrice);
        return companyRepo.getOne(this.companyID).getCoupons().stream().filter(item -> item.getPrice() <= maxPrice).collect(Collectors.toList());
    }

    //------------------------ GET COMPANY'S DETAILS -----------------------

    /**
     * a method to get logged in Company details
     *
     * @return the logged in company details
     */
    public Company getCompanyDetails() {
        return companyRepo.findById(this.companyID);
    }


}
