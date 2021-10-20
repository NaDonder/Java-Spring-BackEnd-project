package com.Project.CouponProject.Repositories;

import com.Project.CouponProject.Beans.Coupon;
import com.Project.CouponProject.Beans.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepo extends JpaRepository<Customer, Integer> {
    /**
     * a Repository to find existing customer
     * @param email finding by email
     * @param password finding by password
     * @return true if found, false if not
     */
    boolean existsCustomerByEmailAndPassword(String email, String password);

    /**
     * a Repository to find Customer
     * @param email finding by email
     * @param password finding by password
     * @return Customer if found
     */
    Customer findByEmailAndPassword(String email, String password);

    /**
     * a Repository to find existing Customer
     * @param email finding by email
     * @return true if found, false if not
     */
    boolean existsCustomerByEmail(String email);

    /**
     * a Repository to find Customer
     * @param id finding by ID
     * @return Customer if found
     */
    Customer findById(int id);

    /**
     * a Repository to find Containing coupons
     * @param coupon the coupon were trying to find
     * @return List of Customers by containing coupon
     */
    boolean findByCouponsContaining(Coupon coupon);

}
