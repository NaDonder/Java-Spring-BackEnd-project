package com.Project.CouponProject.CLR;

import com.Project.CouponProject.Beans.Category;
import com.Project.CouponProject.Beans.ClientType;
import com.Project.CouponProject.Beans.Coupon;
import com.Project.CouponProject.Login.LoginManager;
import com.Project.CouponProject.Repositories.CouponRepo;
import com.Project.CouponProject.Services.ClientService;
import com.Project.CouponProject.Services.CompanyService;
import com.Project.CouponProject.Services.CustomerService;
import com.Project.CouponProject.Util.ColorPrint;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.sql.Date;

//@Component
@Order(4)
@RequiredArgsConstructor
public class test implements CommandLineRunner {

    private final LoginManager loginManager;

    private final CouponRepo couponRepo;

    ClientService clientService;

    @Override
    public void run(String... args) throws Exception {

        try {

        System.out.println(ColorPrint.ANSI_BLUE + "=============================================================" + ColorPrint.ANSI_RESET);
        System.out.println("----------- order 4 ---- test was activated  -----------");
        System.out.println(ColorPrint.ANSI_BLUE + "=============================================================" + ColorPrint.ANSI_RESET);

        clientService = loginManager.login("Company@number.four","12345", ClientType.COMPANY);

        Coupon coupon = Coupon.builder()
                .title("Natan's heavy shoes")
                .description("very heavy")
                .category(Category.Sports)
                .startDate(Date.valueOf("2021-07-30"))
                .endDate(Date.valueOf("2021-12-31"))
                .price(420.0)
                .amount(9)
                .image("heavy one")
                .build();

            Coupon coupon1 = Coupon.builder()
                    .title("Imaginary wildlife")
                    .description("Crazy night forest camping night")
                    .category(Category.Camping)
                    .startDate(Date.valueOf("2021-07-30"))
                    .endDate(Date.valueOf("2021-12-31"))
                    .price(187.0)
                    .amount(20)
                    .image("AMAZING")
                    .build();

            Coupon coupon2 = Coupon.builder()
                    .title("50% off our new bau bau")
                    .description("duck stuffed bau bau")
                    .category(Category.Restaurants)
                    .startDate(Date.valueOf("2021-07-30"))
                    .endDate(Date.valueOf("2021-12-31"))
                    .price(56.0)
                    .amount(200)
                    .image("W-O-W")
                    .build();



            ((CompanyService)clientService).addCoupon(coupon);
            ((CompanyService)clientService).addCoupon(coupon1);
            ((CompanyService)clientService).addCoupon(coupon2);

            System.out.println("Trying to update a coupon that doesn't belong to the company");

            Coupon updatingCoupon = couponRepo.findById(1);
            updatingCoupon.setId(1);
            updatingCoupon.setPrice(0.0);
            updatingCoupon.setImage("HAHA");
            updatingCoupon.setDescription("MY PRECIOUS");
            updatingCoupon.setAmount(0);

            try {
                ((CompanyService) clientService).updateCoupon(updatingCoupon);
            } catch (Exception error) {
                System.out.println(error.getMessage());
            }


            clientService = loginManager.login("Customer@number.new","12345",ClientType.CUSTOMER);



            ((CustomerService)clientService).purchaseCoupon(couponRepo.getOne(7));
            ((CustomerService)clientService).purchaseCoupon(couponRepo.getOne(8));
            ((CustomerService)clientService).purchaseCoupon(couponRepo.getOne(9));

            //-------------------------- OneToMany CHECK ---------------------------------

            clientService = loginManager.login("admin@admin.com","admin",ClientType.ADMINISTRATOR);

            //((AdminService)clientService).deleteCompany(1);

            //((AdminService)clientService).deleteCustomer(1);


            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>> All tests are done <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");

            //----------------- LAST EXCEPTION IF ALL WENT WRONG -------------------------
            //----------------- ALWAYS AT THE BOTTOM DON'T TOUCH -------------------------

        } catch (Exception error) {
            System.out.println("Something somewhere went terribly wrong --- LIMITED EDITION");
        }
    }
}
