package com.Project.CouponProject.CLR;

import com.Project.CouponProject.Beans.ClientType;
import com.Project.CouponProject.Login.LoginManager;
import com.Project.CouponProject.Repositories.CouponRepo;
import com.Project.CouponProject.Services.ClientService;
import com.Project.CouponProject.Services.CustomerService;
import com.Project.CouponProject.Util.ColorPrint;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.ansi.AnsiColor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.awt.*;

@Component
@Order(3)
@RequiredArgsConstructor
public class customerTest implements CommandLineRunner {

    private final LoginManager loginManager;

    private final CouponRepo couponRepo;

    @Override
    public void run(String... args) throws Exception {

        try {

            System.out.println(ColorPrint.ANSI_RED + "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX\n" +
                               "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX\n" +
                               "XXXXXXXXXXXXXXXXXXXXXXXX       CUSTOMER LOGIN        XXXXXXXXXXXXXXXXXXXXX\n" +
                               "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX\n" +
                               "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX\n" + ColorPrint.ANSI_RESET);

            //-------------------- LOGIN MANAGER LOGIN CHECK -----------------------------

            System.out.println(ColorPrint.ANSI_BLUE + "=====================================================" + ColorPrint.ANSI_RESET);

            ClientService clientService;

            System.out.println("Trying to login with wrong user name and password...");
            try {
                clientService = loginManager.login("greetings@zeev.lecturererererererererer", "12345", ClientType.CUSTOMER);
            } catch (Exception error) {
                System.out.println(error.getMessage());
            }

            System.out.println("Logging in with the right user name and password....");

            clientService = loginManager.login("greetings@zeev.lecturererer", "12345", ClientType.CUSTOMER);

            System.out.println(ColorPrint.ANSI_BLUE + "=====================================================" + ColorPrint.ANSI_RESET);

            //------------------------ PURCHASING COUPON ---------------------------

            System.out.println(ColorPrint.ANSI_RED + "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX\n" +
                    "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX\n" +
                    "XXXXXXXXXXXXXXXXXXXXXXXX       CUSTOMER PURCHASE        XXXXXXXXXXXXXXXXXXXXX\n" +
                    "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX\n" +
                    "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX\n" + ColorPrint.ANSI_RESET);

            System.out.println(ColorPrint.ANSI_BLUE + "=====================================================" + ColorPrint.ANSI_RESET);
            System.out.println("Hello zeev");
            System.out.println("Take some coupons");

            ((CustomerService) clientService).purchaseCoupon(couponRepo.findById(1));
            ((CustomerService) clientService).purchaseCoupon(couponRepo.findById(2));


            try {

                ((CustomerService) clientService).purchaseCoupon(couponRepo.findById(3));

            } catch (Exception error) {
                System.out.println(error.getMessage());
            }


            System.out.println("No no zeev you can't buy coupons out of date :-)");
            try {
                ((CustomerService) clientService).purchaseCoupon(couponRepo.findById(5));
            } catch (Exception error) {
                System.out.println(error.getMessage());
            }

            System.out.println("This one finished you can't buy this one... :P ");
            try {
                ((CustomerService) clientService).purchaseCoupon(couponRepo.findById(6));
            } catch (Exception error) {
                System.out.println(error.getMessage());
            }

            System.out.println(ColorPrint.ANSI_BLUE + "=====================================================" + ColorPrint.ANSI_RESET);

            //--------------------------- GETTING CUSTOMER'S COUPONS ----------------------------

            System.out.println(ColorPrint.ANSI_RED + "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX\n" +
                    "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX\n" +
                    "XXXXXXXXXXXXXXXXXXXXXXXX       CUSTOMER GET          XXXXXXXXXXXXXXXXXXXXX\n" +
                    "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX\n" +
                    "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX\n" + ColorPrint.ANSI_RESET);

            System.out.println("=====================================================");

            System.out.println("Let me see what kind of coupons you're holding there...");

            ((CustomerService) clientService).getCustomerCoupons().forEach(System.out::println);

            System.out.println(ColorPrint.ANSI_BLUE + "=====================================================" + ColorPrint.ANSI_RESET);

            //---------------------------- GETTING CUSTOMER'S DETAILS ------------------------

            System.out.println(ColorPrint.ANSI_RED + "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX\n" +
                    "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX\n" +
                    "XXXXXXXXXXXXXXXXXXXXXXXX       CUSTOMER DETAILS        XXXXXXXXXXXXXXXXXXXXX\n" +
                    "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX\n" +
                    "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX\n" + ColorPrint.ANSI_RESET);

            System.out.println("=====================================================");

            System.out.println("Who is logged in?");

            System.out.println(((CustomerService) clientService).getCustomerDetails());

            System.out.println(ColorPrint.ANSI_BLUE + "=====================================================" + ColorPrint.ANSI_RESET);


            //----------------- LAST EXCEPTION IF ALL WENT WRONG -------------------------
            //----------------- ALWAYS AT THE BOTTOM DON'T TOUCH -------------------------


        } catch (Exception error) {
            System.out.println("Something somewhere went terribly wrong --- CUSTOMER EDITION");
        }
    }
}
