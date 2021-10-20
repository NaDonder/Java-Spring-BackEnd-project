package com.Project.CouponProject.Controllers;

import com.Project.CouponProject.Beans.ClientType;
import com.Project.CouponProject.Beans.Company;
import com.Project.CouponProject.Beans.Customer;
import com.Project.CouponProject.Exceptions.*;
import com.Project.CouponProject.JWT.JwtUtil;
import com.Project.CouponProject.JWT.UserDetails;
import com.Project.CouponProject.Login.LoginManager;
import com.Project.CouponProject.Services.AdminService;
import io.jsonwebtoken.MalformedJwtException;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("admin")
@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*")
@RequiredArgsConstructor
public class AdminController {

    private AdminService adminService = null;
    private final LoginManager loginManager;
    private final JwtUtil jwtUtil;

    /**
     * a method to get HttpHeaders in the ResponseEntity Body
     * @param token the JWT token we're passing
     * @return HttpHeaders
     */
    private HttpHeaders getHeaders(String token) {
        UserDetails userDetails = new UserDetails();
        userDetails.setEmail(jwtUtil.extractEmail(token));
        userDetails.setClientType(ClientType.valueOf((String) jwtUtil.extractAllClaims(token).get("clientType")));
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Authorization", jwtUtil.generateToken(userDetails));
        return httpHeaders;
    }

    //----------------------------- LOGIN --------------------------------

    /**
     * Controller for the admin
     * @param userDetails for validation (email and password are hard-coded)
     * @return httpStatus + JWT
     */
    @PostMapping("login")
    public ResponseEntity<?> login(@RequestBody UserDetails userDetails) {
        try {
            adminService = (AdminService) loginManager.login(userDetails.getEmail(), userDetails.getPassword(), userDetails.getClientType());
        } catch (LoginException error) {
            System.out.println(error.getMessage());
            return ResponseEntity.badRequest().body(error.getMessage());
        }
        return ResponseEntity.ok(jwtUtil.generateToken(userDetails));
    }

    //--------------------------------------------------------------------
    //--------------------------- COMPANY --------------------------------
    //--------------------------------------------------------------------

    //----------------------------- ADD --------------------------------


    /*
    @PostMapping("company/add")
    public ResponseEntity<?> addCompany(@RequestHeader(name = "Authorization") String token, @RequestBody Company company) {
        try {
            if (jwtUtil.validateToken(token)) {
                try {
                    adminService.addCompany(company);
                } catch (CompanyException error) {
                    System.out.println(error.getMessage());
                    return new ResponseEntity<>(error.getMessage(), HttpStatus.FORBIDDEN);
                }
                return new ResponseEntity<>("Company " + company.getName() + " was added", HttpStatus.CREATED);
            }
        } catch (MalformedJwtException error) {
            System.out.println("I HATE JWT -- WRONG JWT INSERTED");
        }
        return new ResponseEntity<>("Invalid token", HttpStatus.UNAUTHORIZED);
    }
*/


    /**
     * Controller for adding new Company
     * @param token JWt for authorization
     * @param company the Company we're adding
     * @return httpStatus + new JWT
     * @throws MalformedJwtException for wrong JWT
     * @throws CompanyException if exists by name or email
     */
    @PostMapping("company/add")
    public ResponseEntity<?> addCompany(@RequestHeader(name = "Authorization") String token, @RequestBody Company company) throws MalformedJwtException, CompanyException {
        if (jwtUtil.validateToken(token)) {
            adminService.addCompany(company);
        }
        return ResponseEntity.ok().headers(getHeaders(token)).body("Company was added.");
    }

/*
    @PostMapping("company/add")
    public ResponseEntity<?> addCompany(@RequestBody Company company) throws MalformedJwtException, CompanyException {
            adminService.addCompany(company);
        return ResponseEntity.ok().body("Company was added.");
    }
*/
    //----------------------------- UPDATE --------------------------------

    /*
    //@SneakyThrows
    @PostMapping("company/update")
    public ResponseEntity<?> updateCompany(@RequestHeader(name = "Authorization") String token, @RequestBody Company company) {
        try {
            if (jwtUtil.validateToken(token)) {
                try {
                    adminService.updateCompany(company);
                } catch (CompanyException error) {
                    System.out.println(error.getMessage());
                    return new ResponseEntity<>(error.getMessage(), HttpStatus.FORBIDDEN);
                }
                return new ResponseEntity<>("Company Updated", HttpStatus.OK);
            }
        } catch (MalformedJwtException error) {
            System.out.println(" >>>>> I HATE JWT ----- WRONG JWT INSERTED <<<<<");
        }
        return new ResponseEntity<>("Invalid token", HttpStatus.UNAUTHORIZED);
    }
*/

    /**
     * Controller for updating company
     * @param token for authorization
     * @param company the Company we're updating
     * @return httpStatus + new JWT
     * @throws MalformedJwtException for wrong JWT
     * @throws CompanyException if doesn't exists by ID or name was changed
     */
    @PostMapping("company/update")
    public ResponseEntity<?> updateCompany(@RequestHeader(name = "Authorization") String token, @RequestBody Company company) throws MalformedJwtException, CompanyException {
        if (jwtUtil.validateToken(token)) {
            adminService.updateCompany(company);
        }
        return ResponseEntity.ok().headers(getHeaders(token)).body("Company was updated!");
    }

    //----------------------------- DELETE --------------------------------

    /**
     * Controller for deleting Company
     * @param token for JWT authorization
     * @param id deleting by the company ID
     * @return httpStatus + new JWT
     * @throws MalformedJwtException for wrong JWT
     * @throws CompanyException if doesn't exist by ID
     */
    /*
    @DeleteMapping("deleteCompany/{id}")
    public ResponseEntity<?> deleteCompany(@RequestHeader(name = "Authorization") String token, @PathVariable int id) {
        try {
            if (jwtUtil.validateToken(token)) {
                try {
                    adminService.deleteCompany(id);
                } catch (CompanyException error) {
                    System.out.println(error.getMessage());
                    return new ResponseEntity<>(error.getMessage(), HttpStatus.FORBIDDEN);
                }
                return new ResponseEntity<>("Company was deleted Successfully", HttpStatus.OK);
            }
        } catch (MalformedJwtException error) {
            System.out.println(" >>>>> I HATE JWT ----- WRONG JWT INSERTED <<<<<");
        }
        return new ResponseEntity<>("Invalid token", HttpStatus.UNAUTHORIZED);
    }
    */
    @DeleteMapping("deleteCompany/{id}")
    public ResponseEntity<?> deleteCompany(@RequestHeader(name = "Authorization") String token, @PathVariable int id) throws MalformedJwtException, CompanyException {
        if (jwtUtil.validateToken(token)) {
            adminService.deleteCompany(id);
        }
        return ResponseEntity.ok().headers(getHeaders(token)).body("Company was deleted successfully!!!");
    }

    //----------------------------- GET ONE --------------------------------

    /**
     * Controller for getting single company
     * @param token JWT for authorization
     * @param id getting the company by ID
     * @return httpStatus + new JWT
     * @throws MalformedJwtException for wrong JWT
     * @throws CompanyException if company doesn't exist
     */
    /*
    @PostMapping("company/{id}")
    public ResponseEntity<?> getOneCompany(@RequestHeader(name = "Authorization") String token, @PathVariable int id) {
        Company incCompany;
        try {
            if (jwtUtil.validateToken(token)) {
                try {
                    incCompany = adminService.getOneCompany(id);
                } catch (CompanyException error) {
                    System.out.println(error.getMessage());
                    return new ResponseEntity<>(error.getMessage(), HttpStatus.BAD_REQUEST);
                }
                return new ResponseEntity<>(incCompany,HttpStatus.OK);
            }
        } catch (MalformedJwtException error) {
            System.out.println(" >>>>> I HATE JWT ----- WRONG JWT INSERTED <<<<<");
        }
        return new ResponseEntity<>("Invalid token", HttpStatus.UNAUTHORIZED);
    }
    */
    @PostMapping("company/{id}")
    public ResponseEntity<?> getOneCompany(@RequestHeader(name = "Authorization") String token, @PathVariable int id) throws MalformedJwtException, CompanyException {
        if (jwtUtil.validateToken(token)) {
            return ResponseEntity.ok().headers(getHeaders(token)).body(adminService.getOneCompany(id));
        }
        return new ResponseEntity<>(HttpStatus.UNAVAILABLE_FOR_LEGAL_REASONS);
    }

    //----------------------------- GET ALL --------------------------------

    /**
     * Controller for getting all Companies
     * @param token for JWT authorization
     * @return httpStatus + new JWT
     * @throws MalformedJwtException for wrong JWT
     */
    /*
    @PostMapping("company/all")
    public ResponseEntity<?> getAllCompanies(@RequestHeader(name = "Authorization") String token) {
        List<Company> companies;
        try {
            if (jwtUtil.validateToken(token)) {
                companies = adminService.getAllCompanies();
                return new ResponseEntity<>(companies, HttpStatus.OK);
            }
        } catch (MalformedJwtException error) {
            System.out.println(" >>>>> I HATE JWT ----- WRONG JWT INSERTED <<<<<");
        }
        return new ResponseEntity<>("Invalid token", HttpStatus.UNAUTHORIZED);
    }
    */
    @PostMapping("company/all")
    public ResponseEntity<?> getAllCompanies(@RequestHeader(name = "Authorization") String token) throws MalformedJwtException {
        if (jwtUtil.validateToken(token)) {
            return ResponseEntity.ok().headers(getHeaders(token)).body(adminService.getAllCompanies());
            //return new ResponseEntity<>(adminService.getAllCompanies(),getHeaders(token),HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.UNAVAILABLE_FOR_LEGAL_REASONS);
    }


    //--------------------------------------------------------------------
    //-------------------------- CUSTOMER --------------------------------
    //--------------------------------------------------------------------

    //----------------------------- ADD --------------------------------

    /*
    @PostMapping("customer/add")
    public ResponseEntity<?> addCustomer(@RequestHeader(name = "Authorization") String token, @RequestBody Customer customer) {
        try {
            if (jwtUtil.validateToken(token)) {
                try {
                    adminService.addCustomer(customer);
                } catch (CustomerException error) {
                    System.out.println(error.getMessage());
                    return new ResponseEntity<>(error.getMessage(), HttpStatus.FORBIDDEN);
                }
                return new ResponseEntity<>("Customer " + customer.getFirstName() + " was created!", HttpStatus.CREATED);
            }
        } catch (MalformedJwtException error) {
            System.out.println("I HATE JWT -- WRONG JWT INSERTED");
        }
        return new ResponseEntity<>("Invalid token", HttpStatus.UNAUTHORIZED);
    }
    */

    /**
     * Controller for adding new Customer
     * @param token JWT for authorization
     * @param customer the Customer we're adding
     * @return returns httpStatus + new JWT
     * @throws MalformedJwtException for wrong JWT
     * @throws CustomerException if already exists by email
     */
    @PostMapping("customer/add")
    public ResponseEntity<?> addCustomer(@RequestHeader(name = "Authorization") String token, @RequestBody Customer customer) throws MalformedJwtException, CustomerException {
        if (jwtUtil.validateToken(token)) {
            adminService.addCustomer(customer);
        }
        return ResponseEntity.ok().headers(getHeaders(token)).body("Customer was added!");
    }

    //----------------------------- UPDATE --------------------------------

    /**
     * Controller for updating Customer
     * @param token JWT for authorization
     * @param customer the Customer going to be updated
     * @return httpStatus + new JWT
     * @throws MalformedJwtException for wrong JWT
     * @throws CustomerException if doesn't exist by ID or email already exists
     */
    @PostMapping("customer/update")
    public ResponseEntity<?> updateCustomer(@RequestHeader(name = "Authorization") String token, @RequestBody Customer customer) throws MalformedJwtException, CustomerException {
        if (jwtUtil.validateToken(token)) {
            adminService.updateCustomer(customer);
        }
        return ResponseEntity.ok().headers(getHeaders(token)).body("Customer was updated!");
    }

    //----------------------------- DELETE --------------------------------

    /**
     * Controller for deleting customer
     * @param token JWT for authorization
     * @param id deleting customer by ID
     * @return httpStatus + new JWT
     * @throws MalformedJwtException for wrong JWT
     * @throws CustomerException if doesn't exist by ID
     */
    @DeleteMapping("deleteCustomer/{id}")
    public ResponseEntity<?> deleteCustomer(@RequestHeader(name = "Authorization") String token, @PathVariable int id) throws MalformedJwtException, CustomerException {
        if (jwtUtil.validateToken(token)) {
            adminService.deleteCustomer(id);
        }
        return ResponseEntity.ok().headers(getHeaders(token)).body("Customer was deleted!");
    }

    //----------------------------- GET ONE --------------------------------

    /**
     * Controller for getting single customer
     * @param token for JWT authorization
     * @param id getting customer by ID
     * @return httpStatus + new JWT
     * @throws MalformedJwtException for wrong JWT
     * @throws CustomerException if doesn't exist by ID
     */
    @PostMapping("customer/{id}")
    public ResponseEntity<?> getOneCustomer(@RequestHeader(name = "Authorization") String token, @PathVariable int id) throws MalformedJwtException, CustomerException {
        if (jwtUtil.validateToken(token)) {
            return ResponseEntity.ok().headers(getHeaders(token)).body(adminService.getOneCustomer(id));
        }
        return new ResponseEntity<>(HttpStatus.UNAVAILABLE_FOR_LEGAL_REASONS);
    }

    //----------------------------- GET ALL --------------------------------

    /**
     * Controller for getting all Customers
     * @param token for JWT authorization
     * @return httpStatus + new JWT
     * @throws MalformedJwtException for wrong JWT
     */
    @PostMapping("customer/all")
    public ResponseEntity<?> getAllCustomers(@RequestHeader(name = "Authorization") String token) throws MalformedJwtException {
        if (jwtUtil.validateToken(token)) {
            return ResponseEntity.ok().headers(getHeaders(token)).body(adminService.getAllCustomer());
        }
        return new ResponseEntity<>(HttpStatus.UNAVAILABLE_FOR_LEGAL_REASONS);
    }



}
