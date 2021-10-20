package com.Project.CouponProject.CLR;

import com.Project.CouponProject.Beans.Category;
import com.Project.CouponProject.Beans.ClientType;
import com.Project.CouponProject.Beans.Coupon;
import com.Project.CouponProject.Login.LoginManager;
import com.Project.CouponProject.Services.ClientService;
import com.Project.CouponProject.Services.CompanyService;
import com.Project.CouponProject.Util.ColorPrint;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.sql.Date;

@Component
@Order(2)
@RequiredArgsConstructor
public class companyTest implements CommandLineRunner {

    private final LoginManager loginManager;

    @Override
    public void run(String... args) throws Exception {

        try {

            //-------------------- LOGIN MANAGER LOGIN CHECK -----------------------------

            System.out.println(ColorPrint.ANSI_RED +
                    "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX\n" +
                    "XXXXXXXXXXXXXXXXXXXXXXXX       COMPANY LOGIN         XXXXXXXXXXXXXXXXXXXXX\n" +
                    "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX\n" +
                    "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX\n" + ColorPrint.ANSI_RESET);

            System.out.println(ColorPrint.ANSI_BLUE +"====================================================="+ ColorPrint.ANSI_RESET);

            ClientService clientService;

            System.out.println("Trying to login to company facade with wrong username...");

            try {
                clientService = loginManager.login("Company@number.one123", "12345", ClientType.COMPANY);
            } catch (Exception error) {
                System.out.println(error.getMessage());
            }

            System.out.println(ColorPrint.ANSI_BLUE +"====================================================="+ ColorPrint.ANSI_RESET);

            System.out.println(ColorPrint.ANSI_BLUE +"====================================================="+ ColorPrint.ANSI_RESET);

            System.out.println("Logging in with the right parameters for company...");

            clientService = loginManager.login("Company@number.one", "12345", ClientType.COMPANY);

            System.out.println("WE'RE LOGGED IN AS COMPANY!!!");

            System.out.println(ColorPrint.ANSI_BLUE +"====================================================="+ ColorPrint.ANSI_RESET);

            //------------------------ CREATING COUPONS -----------------------------------

            Coupon coupon = Coupon.builder()
                    .title("ELECTRIC DUCK 5000")
                    .description("amazing electric duck")
                    .category(Category.Electricity)
                    .startDate(Date.valueOf("2021-07-18"))
                    .endDate(Date.valueOf("2021-12-31"))
                    .amount(10)
                    .price(160.0)
                    .image("https://images.pexels.com/photos/6328132/pexels-photo-6328132.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500")
                    .build();

            Coupon coupon1 = Coupon.builder()
                    .title("ELECTRIC DUCK 6000")
                    .description("amazing electric duck")
                    .category(Category.Electricity)
                    .startDate(Date.valueOf("2021-07-18"))
                    .endDate(Date.valueOf("2021-12-31"))
                    .amount(10)
                    .price(170.0)
                    .image("https://images.pexels.com/photos/4477120/pexels-photo-4477120.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500")
                    .build();

            Coupon coupon2 = Coupon.builder()
                    .title("Amazing road trip")
                    .description("5 day road trip")
                    .category(Category.Travelling)
                    .startDate(Date.valueOf("2021-07-18"))
                    .endDate(Date.valueOf("2021-12-31"))
                    .amount(5)
                    .price(200.0)
                    .image("https://images.pexels.com/photos/4339681/pexels-photo-4339681.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500")
                    .build();

            Coupon coupon3 = Coupon.builder()
                    .title("The All-will-be-ok")
                    .description("everything just better")
                    .category(Category.Healthcare)
                    .startDate(Date.valueOf("2021-07-18"))
                    .endDate(Date.valueOf("2021-12-31"))
                    .amount(42)
                    .price(500.0)
                    .image("https://images.pexels.com/photos/2383010/pexels-photo-2383010.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500")
                    .build();

            //-------------------- CREATING 2 COUPONS FOR EXCEPTION THROW ------------------

            Coupon outOfDate = Coupon.builder()
                    .title("Out of date coupon")
                    .description("The Coupon is out of date")
                    .category(Category.Camping)
                    .startDate(Date.valueOf("2021-07-22"))
                    .endDate(Date.valueOf("2021-07-18"))
                    .amount(50)
                    .price(9999.0)
                    .image("https://images.pexels.com/photos/2422265/pexels-photo-2422265.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500")
                    .build();

            Coupon amountZero = Coupon.builder()
                    .title("Amount 0 coupon")
                    .description("The amount of the coupon is 0")
                    .category(Category.Restaurants)
                    .startDate(Date.valueOf("2021-07-22"))
                    .endDate(Date.valueOf("2021-12-30"))
                    .amount(0)
                    .price(9999.0)
                    .image("https://images.pexels.com/photos/1484516/pexels-photo-1484516.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500")
                    .build();

            //--------------------- ADDING COUPONS TO THE SYSTEM ------------------------------

            System.out.println(ColorPrint.ANSI_RED +"XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX\n" +
                    "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX\n" +
                    "XXXXXXXXXXXXXXXXXXXXXXXX       COMPANY ADD           XXXXXXXXXXXXXXXXXXXXX\n" +
                    "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX\n" +
                    "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX\n" + ColorPrint.ANSI_RESET);

            System.out.println(ColorPrint.ANSI_BLUE +"====================================================="+ ColorPrint.ANSI_RESET);

            System.out.println("Adding coupons to the system...");

            System.out.println("Adding First coupon...");
            ((CompanyService) clientService).addCoupon(coupon);
            System.out.println("Adding " + coupon.getTitle() + " was successful");

            try {
                System.out.println("Trying to add the first coupon AGAIN to the company");
                ((CompanyService) clientService).addCoupon(coupon);
                System.out.println("WE DON'T WANT THIS MESSAGE ON THE SCREEN");
            } catch (Exception error) {
                System.out.println(error.getMessage());
            }

            System.out.println("Adding Second coupon...");
            ((CompanyService) clientService).addCoupon(coupon1);
            System.out.println("Adding " + coupon1.getTitle() + " was successful");

            System.out.println("Adding Third coupon...");
            ((CompanyService) clientService).addCoupon(coupon2);
            System.out.println("Adding " + coupon2.getTitle() + " was successful");

            System.out.println("Adding Fourth coupon...");
            ((CompanyService) clientService).addCoupon(coupon3);
            System.out.println("Adding " + coupon3.getTitle() + " was successful");

            System.out.println("---------------");
            System.out.println("Exception throwing coupons");
            System.out.println("---------------");

            System.out.println("Adding out of date coupon");
            ((CompanyService)clientService).addCoupon(outOfDate);
            System.out.println("Adding zero amount coupon");
            ((CompanyService)clientService).addCoupon(amountZero);

            System.out.println(ColorPrint.ANSI_BLUE +"====================================================="+ ColorPrint.ANSI_RESET);

            //-------------------- UPDATING COUPON FROM THE SYSTEM ----------------------

            System.out.println(ColorPrint.ANSI_RED +"XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX\n" +
                    "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX\n" +
                    "XXXXXXXXXXXXXXXXXXXXXXXX       COMPANY UPDATE        XXXXXXXXXXXXXXXXXXXXX\n" +
                    "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX\n" +
                    "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX\n" + ColorPrint.ANSI_RESET);

            Coupon updatingCoupon = coupon;
            updatingCoupon.setId(1);
            updatingCoupon.setPrice(150.0);
            updatingCoupon.setImage("https://images.pexels.com/photos/6328132/pexels-photo-6328132.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500");
            updatingCoupon.setDescription("new amazing electric duck");
            updatingCoupon.setAmount(5);

            System.out.println(ColorPrint.ANSI_BLUE +"====================================================="+ ColorPrint.ANSI_RESET);

            System.out.println("Updating coupon from the system: ");

            try {
                ((CompanyService) clientService).updateCoupon(updatingCoupon);
                System.out.println("Successfully updated the coupon!");
            } catch (Exception error) {
                System.out.println(error.getMessage());
            }

            System.out.println(ColorPrint.ANSI_BLUE +"====================================================="+ ColorPrint.ANSI_RESET);

            //-------------------- DELETING COUPON FROM THE SYSTEM -----------------------

            System.out.println(ColorPrint.ANSI_RED +"XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX\n" +
                    "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX\n" +
                    "XXXXXXXXXXXXXXXXXXXXXXXX       COMPANY DELETE        XXXXXXXXXXXXXXXXXXXXX\n" +
                    "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX\n" +
                    "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX\n" + ColorPrint.ANSI_RESET);

            System.out.println(ColorPrint.ANSI_BLUE +"====================================================="+ ColorPrint.ANSI_RESET);

            System.out.println("Trying to delete a non existing coupon: (ID - 10)");

            try {
                ((CompanyService) clientService).deleteCoupon(10);
            } catch (Exception error) {
                System.out.println(error.getMessage());
            }

            System.out.println("Deleting coupon number 4 from the system: ");

            ((CompanyService) clientService).deleteCoupon(4);

            System.out.println(ColorPrint.ANSI_BLUE +"====================================================="+ ColorPrint.ANSI_RESET);


            //----------------------- GET ALL COMPANY'S COUPONS --------------------------

            System.out.println(ColorPrint.ANSI_RED +"XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX\n" +
                    "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX\n" +
                    "XXXXXXXXXXXXXXXXXXXXXXXX       COMPANY GET           XXXXXXXXXXXXXXXXXXXXX\n" +
                    "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX\n" +
                    "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX\n" + ColorPrint.ANSI_RESET);

            System.out.println(ColorPrint.ANSI_BLUE +"====================================================="+ ColorPrint.ANSI_RESET);

            System.out.println("Getting all company's coupons: ");

            ((CompanyService) clientService).getCompanyCoupons().forEach(System.out::println);

            System.out.println(ColorPrint.ANSI_BLUE +"====================================================="+ ColorPrint.ANSI_RESET);

            //--------------------- GET ALL COMPANY'S COUPONS BY CATEGORY ----------------------

            System.out.println(ColorPrint.ANSI_RED +"XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX\n" +
                    "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX\n" +
                    "XXXXXXXXXXXXXXXXXXXXXXXX  COMPANY COUPON BY CATEGORY XXXXXXXXXXXXXXXXXXXXX\n" +
                    "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX\n" +
                    "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX\n" + ColorPrint.ANSI_RESET);

            System.out.println(ColorPrint.ANSI_BLUE +"====================================================="+ ColorPrint.ANSI_RESET);

            System.out.println("Getting all company's coupons by Electricity category: ");

            ((CompanyService) clientService).getCompanyCouponsByCategory(Category.Electricity).forEach(System.out::println);

            System.out.println(ColorPrint.ANSI_BLUE +"====================================================="+ ColorPrint.ANSI_RESET);

            //------------------- GET ALL COMPANY'S COUPONS BY MAX PRICE ---------------------

            System.out.println(ColorPrint.ANSI_RED +"XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX\n" +
                    "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX\n" +
                    "XXXXXXXXXXXXXXXXXXXXXXXX   COMPANY COUPON BY PRICE   XXXXXXXXXXXXXXXXXXXXX\n" +
                    "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX\n" +
                    "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX\n" + ColorPrint.ANSI_RESET);

            System.out.println(ColorPrint.ANSI_BLUE +"====================================================="+ ColorPrint.ANSI_RESET);

            System.out.println("Getting all company's coupons by max price: ");

            ((CompanyService) clientService).getCompanyCouponsByMaxPrice(170.0).forEach(System.out::println);

            System.out.println(ColorPrint.ANSI_BLUE +"====================================================="+ ColorPrint.ANSI_RESET);

            //------------------------------ GET COMPANY DETAILS ----------------------

            System.out.println(ColorPrint.ANSI_RED +"XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX\n" +
                    "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX\n" +
                    "XXXXXXXXXXXXXXXXXXXXXXXX       COMPANY DETAIL        XXXXXXXXXXXXXXXXXXXXX\n" +
                    "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX\n" +
                    "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX\n" + ColorPrint.ANSI_RESET);

            System.out.println(ColorPrint.ANSI_BLUE +"====================================================="+ ColorPrint.ANSI_RESET);

            System.out.println("Getting company details: ");

            System.out.println(((CompanyService) clientService).getCompanyDetails());

            System.out.println(ColorPrint.ANSI_BLUE +"====================================================="+ ColorPrint.ANSI_RESET);


            //----------------- LAST EXCEPTION IF ALL WENT WRONG -------------------------
            //----------------- ALWAYS AT THE BOTTOM DON'T TOUCH -------------------------

        } catch (Exception error) {
            System.out.println("Something somewhere went terribly wrong --- COMPANY EDITION");
        }
    }
}
