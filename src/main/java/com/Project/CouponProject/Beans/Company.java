package com.Project.CouponProject.Beans;

import com.Project.CouponProject.Util.ColorPrint;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "companies")
public class Company {
    /**
     * Fields of the Company Bean
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String email;
    private String password;

    /**
     * Coupon List with OneToMany annotation and Singular
     * Built with Cascade on ALL
     * and Orphan Removal to delete coupon if the company is deleted
     */
    @Singular
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "company_id")
    private List<Coupon> coupons;

    /**
     * To String method to make everything more readable
     * @return 1 big ToString
     */
    @Override
    public String toString() {
        return ColorPrint.ANSI_YELLOW + "Company's ID: " + id + "\n" +
                "Company's name: " + name + "\n" +
                "Company's email: " + email + "\n" +
                "Company's password: " + password + "\n" +
                "Company's coupons: " + "\n" + "[!!!!!!COUPONS STARTS HERE!!!!!!" + "\n" + coupons + "!!!!!!COUPONS ENDS HERE!!!!!!]" + "\n" +
                "-------------------------------------------------------" + "\n"+ ColorPrint.ANSI_RESET;
    }

    /**
     * This method is created to add coupon to by the company ID
     * @param coupon the coupon we're trying to add
     * @return adds the coupon to the coupon list
     */
    public boolean addCoupon(Coupon coupon) {
        if (coupons == null) {
            coupons = new ArrayList<>();
        }
        coupon.setCompanyID(this.id);
        return coupons.add(coupon);
    }

    /**
     * This method is created to remove coupon from the system by the running Thread
     * @param coupon the coupon we're trying to delete
     * @return removes the coupon from the coupon list
     */
    public boolean removeCoupon(Coupon coupon) {
        if (coupons == null) {
            return false;
        }
        return coupons.remove(coupon);
    }



}
