package com.Project.CouponProject.Thread;


import com.Project.CouponProject.Beans.Coupon;
import com.Project.CouponProject.Repositories.CouponRepo;
import com.Project.CouponProject.Services.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Component
@EnableAsync
@EnableScheduling
@RequiredArgsConstructor
public class CouponExpirationDailyJob {

    private final CouponRepo couponRepo;

    private List<Coupon> coupons;

    /**
     * A-Sync process running in the background
     * deleting coupons that are out of date
     * at 12:20:59, every day, every month, every year, by the Asia/Jerusalem time zone
     */
    @Async
    //59 sec , 20 min, 12 hour, day of the month , month , day(s) of week,  time zone
    @Scheduled(cron = "59 20 12 * * ?",zone="Asia/Jerusalem") //fixedRate is also a counting method
    public void deleteExpiredCoupon(){
        coupons = couponRepo.findAll();
        for (Coupon item: coupons) {
            if (item.getEndDate().before(Date.valueOf(LocalDate.now()))) {
                couponRepo.delete(item);
                System.out.println("The system found an expired coupon and deleted it.");
            }
        }
    }


}
