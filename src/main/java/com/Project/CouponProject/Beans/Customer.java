package com.Project.CouponProject.Beans;

import com.Project.CouponProject.Util.ColorPrint;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "customers")
public class Customer {
    /**
     * Fields of Customer Bean
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    /**
     *  Coupon List with OneToMany annotation and Singular
     *  Built with Cascade on ALL
     *  and Orphan Removal to delete coupon if the company is deleted
     */
    @Singular
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "customers_vs_coupons", joinColumns = @JoinColumn(name = "customer_id"), inverseJoinColumns = @JoinColumn(name = "coupon_id"))
    private List<Coupon> coupons;

    /**
     * to string to make everything more readable
     * @return 1 big ToString
     */
    @Override
    public String toString() {
        return ColorPrint.ANSI_YELLOW + "Customer's ID: " + id + "\n" +
                "Customer's first name: " + firstName + "\n" +
                "Customer's last name: " + lastName + "\n" +
                "Customer's email: " + email + "\n" +
                "Customer's password: " + password + "\n" +
                "Customer's coupons: " + coupons + "\n" +
                "------------------------------------------------" + "\n" + ColorPrint.ANSI_RESET;
    }

    /**
     * A method created to purchase coupon from the customer side
     * @param coupon the coupon we're trying to add
     * @return Purchase coupon by the customer
     */
    public boolean purchaseCoupon(Coupon coupon) {
        if (coupons == null) {
            coupons = new ArrayList<>();
        }
        return coupons.add(coupon);
    }

    /**
     * A method created to remove the coupon from the customer side
     * @param coupon the coupon we're trying to delete
     * @return Remove coupon by the customer
     */
    public boolean removeCoupon(Coupon coupon) {
        if (coupons == null) {
            return false;
        }
        return coupons.remove(coupon);

    }
}
