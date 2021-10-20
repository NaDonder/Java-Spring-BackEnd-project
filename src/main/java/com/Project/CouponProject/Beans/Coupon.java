package com.Project.CouponProject.Beans;

import com.Project.CouponProject.Util.ColorPrint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "coupons")
public class Coupon {
    /**
     * Fields of the coupon Bean
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "company_id")
    private int companyID;
    @Enumerated(EnumType.STRING)
    private Category category;
    private String title;
    private String description;
    private Date startDate;
    private Date endDate;
    private int amount;
    private double price;
    private String image;

    /**
     * To String to make everything more readable
     *
     * @return 1 big ToString
     */
    @Override
    public String toString() {
        return ColorPrint.ANSI_YELLOW + "Coupon's id: " + id + "\n" +
                "Coupon's companyID: " + companyID + "\n" +
                "Coupon's category: " + category + "\n" +
                "Coupon's title: " + title + "\n" +
                "Coupon's description: " + description + "\n" +
                "Coupon's startDate: " + startDate + "\n" +
                "Coupon's endDate: " + endDate + "\n" +
                "Coupon's amount: " + amount + "\n" +
                "Coupon's price: " + price + "\n" +
                "Coupon's image: " + image + "\n" +
                "------------------------------------------------" + "\n"+ ColorPrint.ANSI_RESET;
    }

}
