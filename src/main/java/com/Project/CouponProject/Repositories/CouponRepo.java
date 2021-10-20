package com.Project.CouponProject.Repositories;

import com.Project.CouponProject.Beans.Category;
import com.Project.CouponProject.Beans.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CouponRepo extends JpaRepository<Coupon, Integer> {
    /**
     * a Repository to find existing coupon
     * @param companyId finding by CompanyID
     * @param title finding by title
     * @return true if found, false if not
     */
    boolean existsCouponByCompanyIDAndTitle(int companyId, String title);

    /**
     * a Repository to find Coupons
     * @param category finding by Category (enum)
     * @return List of coupons
     */
    List<Coupon> findByCategory(Category category);

    /**
     * a Repository to find Coupons
     * @param maxPrice finding by Max Price and less
     * @return List of coupons
     */
    List<Coupon> findByPriceLessThanEqual(double maxPrice);

    /**
     * a Repository to find Coupon
     * @param id finding by ID
     * @return Coupon if found
     */
    Coupon findById(int id);

}
