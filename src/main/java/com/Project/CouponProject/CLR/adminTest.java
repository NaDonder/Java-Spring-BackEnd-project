package com.Project.CouponProject.CLR;

import com.Project.CouponProject.Beans.ClientType;
import com.Project.CouponProject.Beans.Company;
import com.Project.CouponProject.Beans.Customer;
import com.Project.CouponProject.Login.LoginManager;
import com.Project.CouponProject.Services.AdminService;
import com.Project.CouponProject.Services.ClientService;
import com.Project.CouponProject.Util.ColorPrint;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(1)
@RequiredArgsConstructor
public class adminTest implements CommandLineRunner {

    private final LoginManager loginManager;

    @Override
    public void run(String... args) throws Exception {

        try {

            //------------------------- CREATING NEW COMPANIES ---------------------------

            Company company = Company.builder()
                    .name("Company1")
                    .email("Company@number.one")
                    .password("12345")
                    .build();

            Company company1 = Company.builder()
                    .name("Company2")
                    .email("Company@number.two")
                    .password("12345")
                    .build();

            Company company2 = Company.builder()
                    .name("Zeev the lecturererer")
                    .email("greetings@zeev.lecturererer")
                    .password("12345")
                    .build();

            Company company3 = Company.builder()
                    .name("Company4")
                    .email("Company@number.four")
                    .password("12345")
                    .build();

            //-------------------- LOGIN MANAGER LOGIN CHECK -----------------------------

            System.out.println(ColorPrint.ANSI_RED + "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX\n" +
                    "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX\n" +
                    "XXXXXXXXXXXXXXXXXXXXXXXX         ADMIN LOGIN         XXXXXXXXXXXXXXXXXXXXX\n" +
                    "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX\n" +
                    "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX\n"+ ColorPrint.ANSI_RESET);

            System.out.println(ColorPrint.ANSI_BLUE + "====================================================="+ ColorPrint.ANSI_RESET);

            ClientService clientService;

            System.out.println("Trying to login to admin facade with wrong username...");

            try {
                clientService = loginManager.login("admin1@admin.com", "admin", ClientType.ADMINISTRATOR);
            } catch (Exception error) {
                System.out.println(error.getMessage());
            }

            System.out.println(ColorPrint.ANSI_BLUE + "====================================================="+ ColorPrint.ANSI_RESET);

            System.out.println(ColorPrint.ANSI_BLUE + "====================================================="+ ColorPrint.ANSI_RESET);

            System.out.println("Logging in with the right parameters for admin...");

            clientService = loginManager.login("admin@admin.com", "admin", ClientType.ADMINISTRATOR);

            System.out.println("WE'RE LOGGED IN AS ADMINISTRATOR!!!");

            System.out.println(ColorPrint.ANSI_BLUE + "====================================================="+ ColorPrint.ANSI_RESET);

            //------------------------- ADDING COMPANIES TO THE SYSTEM ----------------------

            System.out.println(ColorPrint.ANSI_RED +"XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX\n" +
                    "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX\n" +
                    "XXXXXXXXXXXXXXXXXXXXXXXX          COMPANIES          XXXXXXXXXXXXXXXXXXXXX\n" +
                    "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX\n" +
                    "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX\n"+ ColorPrint.ANSI_RESET);


            System.out.println(ColorPrint.ANSI_BLUE + "====================================================="+ ColorPrint.ANSI_RESET);

            System.out.println(ColorPrint.ANSI_RED +"XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX\n" +
                    "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX\n" +
                    "XXXXXXXXXXXXXXXXXXXXXXXX        COMPANIES ADD        XXXXXXXXXXXXXXXXXXXXX\n" +
                    "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX\n" +
                    "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX\n"+ ColorPrint.ANSI_RESET);
            System.out.println("Adding first company...");
            ((AdminService) clientService).addCompany(company);
            System.out.println("We've added " + company.getName() + " to the system.");

            try {
                System.out.println("Adding the same company...");
                ((AdminService) clientService).addCompany(company);
            } catch (Exception error) {
                System.out.println(error.getMessage());
            }

            System.out.println("Adding second company");
            ((AdminService) clientService).addCompany(company1);
            System.out.println("We've added " + company1.getName() + " to the system.");

            System.out.println("Adding third company");
            ((AdminService) clientService).addCompany(company2);
            System.out.println("We've added " + company2.getName() + " to the system.");

            System.out.println("Adding fourth company");
            ((AdminService) clientService).addCompany(company3);
            System.out.println("We've added " + company3.getName() + " to the system.");

            System.out.println(ColorPrint.ANSI_BLUE + "====================================================="+ ColorPrint.ANSI_RESET);

            //---------------------- UPDATING COMPANY IN THE SYSTEM -------------------------

            System.out.println(ColorPrint.ANSI_RED +"XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX\n" +
                    "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX\n" +
                    "XXXXXXXXXXXXXXXXXXXXXXXX       COMPANIES UPDATE      XXXXXXXXXXXXXXXXXXXXX\n" +
                    "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX\n" +
                    "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX\n"+ ColorPrint.ANSI_RESET);

            Company updatingCompany = company;
            updatingCompany.setId(3);
            updatingCompany.setName("New Company");
            updatingCompany.setEmail("Company@number.new");

            System.out.println(ColorPrint.ANSI_BLUE + "====================================================="+ ColorPrint.ANSI_RESET);

            System.out.println("Updating a company...");

            try {
                ((AdminService) clientService).updateCompany(updatingCompany);
                System.out.println("Successfully updated the company!");
            } catch (Exception error) {
                System.out.println(error.getMessage());
            }
            System.out.println(ColorPrint.ANSI_BLUE + "====================================================="+ ColorPrint.ANSI_RESET);

            //------------------------ DELETE COMPANY FROM THE SYSTEM --------------------------

            System.out.println(ColorPrint.ANSI_RED +"XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX\n" +
                    "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX\n" +
                    "XXXXXXXXXXXXXXXXXXXXXXXX      COMPANIES DELETE       XXXXXXXXXXXXXXXXXXXXX\n" +
                    "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX\n" +
                    "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX\n"+ ColorPrint.ANSI_RESET);

            System.out.println(ColorPrint.ANSI_BLUE + "====================================================="+ ColorPrint.ANSI_RESET);

            System.out.println("Deleting company from the system...");

            try {
                ((AdminService) clientService).deleteCompany(2);
                System.out.println("Successfully deleted a company from the system");
            } catch (Exception error) {
                System.out.println(error.getMessage());
            }

            System.out.println(ColorPrint.ANSI_BLUE + "====================================================="+ ColorPrint.ANSI_RESET);

            //----------------------- GET ALL COMPANIES FROM THE SYSTEM --------------------------

            System.out.println(ColorPrint.ANSI_RED +"XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX\n" +
                    "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX\n" +
                    "XXXXXXXXXXXXXXXXXXXXXXXX        COMPANIES GET        XXXXXXXXXXXXXXXXXXXXX\n" +
                    "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX\n" +
                    "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX\n"+ ColorPrint.ANSI_RESET);

            System.out.println(ColorPrint.ANSI_BLUE + "====================================================="+ ColorPrint.ANSI_RESET);

            System.out.println("Getting all companies...");

            ((AdminService)clientService).getAllCompanies().forEach(System.out::println);

            System.out.println(ColorPrint.ANSI_BLUE + "====================================================="+ ColorPrint.ANSI_RESET);

            //--------------------- GETTING ONE COMPANY FROM THE SYSTEM ------------------------

            System.out.println(ColorPrint.ANSI_BLUE + "====================================================="+ ColorPrint.ANSI_RESET);

            System.out.println("Getting a company from the system...");

            System.out.println(((AdminService)clientService).getOneCompany(1));

            try {
                System.out.println("Trying to get a company that doesn't exist by ID");
                System.out.println(((AdminService)clientService).getOneCompany(10));
                System.out.println("IF YOU GOT THIS MESSAGE YOU FOUND A COMPANY THAT EXISTS!!!");
            } catch (Exception error) {
                System.out.println(error.getMessage());
            }

            System.out.println(ColorPrint.ANSI_BLUE + "====================================================="+ ColorPrint.ANSI_RESET);

            //-------------------------- CREATING NEW CUSTOMERS --------------------------------

            Customer customer = Customer.builder()
                    .firstName("Customer")
                    .lastName("one")
                    .email("customer@number.one")
                    .password("12345")
                    .build();

            Customer customer1 = Customer.builder()
                    .firstName("Customer")
                    .lastName("two")
                    .email("customer@number.two")
                    .password("12345")
                    .build();

            Customer customer2 = Customer.builder()
                    .firstName("Zeev De")
                    .lastName("lecturererer")
                    .email("greetings@zeev.lecturererer")
                    .password("12345")
                    .build();

            Customer customer3 = Customer.builder()
                    .firstName("Customer")
                    .lastName("four")
                    .email("customer@number.four")
                    .password("12345")
                    .build();

            //------------------- ADDING CUSTOMER TO THE SYSTEM ----------------------------

            System.out.println(ColorPrint.ANSI_RED +"XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX\n" +
                    "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX\n" +
                    "XXXXXXXXXXXXXXXXXXXXXXXX          CUSTOMERS          XXXXXXXXXXXXXXXXXXXXX\n" +
                    "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX\n" +
                    "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX\n"+ ColorPrint.ANSI_RESET);

            System.out.println(ColorPrint.ANSI_BLUE + "====================================================="+ ColorPrint.ANSI_RESET);

            System.out.println(ColorPrint.ANSI_RED +"XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX\n" +
                    "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX\n" +
                    "XXXXXXXXXXXXXXXXXXXXXXXX        CUSTOMERS ADD        XXXXXXXXXXXXXXXXXXXXX\n" +
                    "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX\n" +
                    "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX\n"+ ColorPrint.ANSI_RESET);

            System.out.println(ColorPrint.ANSI_BLUE + "====================================================="+ ColorPrint.ANSI_RESET);

            System.out.println("Adding first customer...");
            ((AdminService)clientService).addCustomer(customer);
            System.out.println("We've added " + customer.getFirstName() + " " + customer.getLastName() + " to the system.");

            try {
                System.out.println("Adding the same customer to the system...");
                ((AdminService)clientService).addCustomer(customer);
            } catch (Exception error) {
                System.out.println(error.getMessage());
            }

            System.out.println("Adding Second customer...");
            ((AdminService)clientService).addCustomer(customer1);
            System.out.println("We've added " + customer.getFirstName() + " " + customer.getLastName() + " to the system.");

            System.out.println("Adding Third customer...");
            ((AdminService)clientService).addCustomer(customer2);
            System.out.println("We've added " + customer.getFirstName() + " " + customer.getLastName() + " to the system.");

            System.out.println("Adding Fourth customer...");
            ((AdminService)clientService).addCustomer(customer3);
            System.out.println("We've added " + customer.getFirstName() + " " + customer.getLastName() + " to the system.");

            System.out.println("====================================================="+ ColorPrint.ANSI_RESET);

            //---------------------- UPDATING CUSTOMER IN THE SYSTEM -------------------------

            System.out.println(ColorPrint.ANSI_RED +"XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX\n" +
                    "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX\n" +
                    "XXXXXXXXXXXXXXXXXXXXXXXX     CUSTOMERS UPDATE        XXXXXXXXXXXXXXXXXXXXX\n" +
                    "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX\n" +
                    "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX\n"+ ColorPrint.ANSI_RESET);

            Customer updatingCustomer = customer;
            updatingCustomer.setId(1);
            updatingCustomer.setFirstName("New Customer");
            updatingCustomer.setLastName("new");
            updatingCustomer.setEmail("Customer@number.new");

            System.out.println(ColorPrint.ANSI_BLUE + "====================================================="+ ColorPrint.ANSI_RESET);

            System.out.println("Updating a Customer...");

            try {
                ((AdminService) clientService).updateCustomer(updatingCustomer);
                System.out.println("Successfully updated the customer!");
            } catch (Exception error) {
                System.out.println(error.getMessage());
            }
            System.out.println(ColorPrint.ANSI_BLUE + "====================================================="+ ColorPrint.ANSI_RESET);

            //-------------------- DELETING A CUSTOMER FROM THE SYSTEM ---------------------------

            System.out.println(ColorPrint.ANSI_RED +"XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX\n" +
                    "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX\n" +
                    "XXXXXXXXXXXXXXXXXXXXXXXX      CUSTOMERS DELETE       XXXXXXXXXXXXXXXXXXXXX\n" +
                    "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX\n" +
                    "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX\n"+ ColorPrint.ANSI_RESET);

            System.out.println(ColorPrint.ANSI_BLUE + "====================================================="+ ColorPrint.ANSI_RESET);

            System.out.println("Deleting customer from the system...");

            try {
                ((AdminService) clientService).deleteCustomer(2);
                System.out.println("Successfully deleted a customer from the system");
            } catch (Exception error) {
                System.out.println(error.getMessage());
            }

            System.out.println(ColorPrint.ANSI_BLUE + "====================================================="+ ColorPrint.ANSI_RESET);

            //-------------------- GETTING ALL CUSTOMERS FROM THE SYSTEM ---------------------------

            System.out.println(ColorPrint.ANSI_RED +"XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX\n" +
                    "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX\n" +
                    "XXXXXXXXXXXXXXXXXXXXXXXX        CUSTOMERS GET        XXXXXXXXXXXXXXXXXXXXX\n" +
                    "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX\n" +
                    "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX\n"+ ColorPrint.ANSI_RESET);

            System.out.println(ColorPrint.ANSI_BLUE + "====================================================="+ ColorPrint.ANSI_RESET);

            System.out.println("Getting all customer...");

            ((AdminService)clientService).getAllCustomer().forEach(System.out::println);

            System.out.println(ColorPrint.ANSI_BLUE + "====================================================="+ ColorPrint.ANSI_RESET);

            //-------------------- GETTING ONE CUSTOMER FROM THE SYSTEM ---------------------------

            System.out.println(ColorPrint.ANSI_BLUE + "====================================================="+ ColorPrint.ANSI_RESET);

            System.out.println("Getting a customer from the system...");

            System.out.println(((AdminService)clientService).getOneCustomer(1));

            try {
                System.out.println("Trying to get a customer that doesn't exist by ID");
                System.out.println(((AdminService)clientService).getOneCustomer(10));
                System.out.println("IF YOU GOT THIS MESSAGE YOU FOUND A CUSTOMER THAT EXISTS!!!");
            } catch (Exception error) {
                System.out.println(error.getMessage());
            }

            System.out.println(ColorPrint.ANSI_BLUE + "====================================================="+ ColorPrint.ANSI_RESET);


            //----------------- LAST EXCEPTION IF ALL WENT WRONG -------------------------
            //----------------- ALWAYS AT THE BOTTOM DON'T TOUCH -------------------------

        } catch (Exception error) {
            System.out.println("Something somewhere went terribly wrong --- ADMIN EDITION");
        }
    }
}
