package com.Project.CouponProject.Controllers;

import com.Project.CouponProject.Beans.Category;
import com.Project.CouponProject.Beans.ClientType;
import com.Project.CouponProject.Beans.Coupon;
import com.Project.CouponProject.Exceptions.CouponException;
import com.Project.CouponProject.Exceptions.CustomerException;
import com.Project.CouponProject.Exceptions.LoginException;
import com.Project.CouponProject.JWT.JwtUtil;
import com.Project.CouponProject.JWT.UserDetails;
import com.Project.CouponProject.Login.LoginManager;
import com.Project.CouponProject.Services.CustomerService;
import io.jsonwebtoken.MalformedJwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("customer")
@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*")
@RequiredArgsConstructor
public class CustomerController {

    private CustomerService customerService = null;
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
     * Controller for login as a customer
     * @param userDetails checking the userDetails fields (Email, Password, ClientType)
     * @return CustomerID and JWT token
     */
    @PostMapping("login")
    public ResponseEntity<?> login(@RequestBody UserDetails userDetails) {
        try {
            customerService = (CustomerService) loginManager.login(userDetails.getEmail(), userDetails.getPassword(), userDetails.getClientType());
        } catch (LoginException error) {
            System.out.println(error.getMessage());
            return ResponseEntity.badRequest().body(error.getMessage());
        }
        return ResponseEntity.ok(jwtUtil.generateToken(userDetails));
    }

    //----------------------------- ADD --------------------------------

    /*
    @PostMapping("customer/addCoupon")
    public ResponseEntity<?> addPurchaseOfCoupon(@RequestBody Coupon coupon) {
        customerService.purchaseCoupon(coupon);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
    */
    /**
     * Controller for purchasing Coupon by the Customer
     * @param token getting the JWT for the authorization
     * @param coupon the coupon we're trying to purchase
     * @return HttpStatus 200 + new Token in the Header
     * @throws MalformedJwtException for Wrong JWT
     * @throws CustomerException for Coupon Exceptions
     */
    @PostMapping("customer/addCoupon")
    public ResponseEntity<?> addPurchaseOfCoupon(@RequestHeader(name = "Authorization") String token, @RequestBody Coupon coupon) throws MalformedJwtException, CustomerException {
        if (jwtUtil.validateToken(token)) {
            customerService.purchaseCoupon(coupon);
        }
        return ResponseEntity.ok().headers(getHeaders(token)).body("Purchase was successful!");
    }

    //----------------------------- DETAILS --------------------------------

    /*
    @SneakyThrows
    @PostMapping("customer/getDetails")
    public ResponseEntity<?> getCustomerDetails() {
        return new ResponseEntity<>(customerService.getCustomerDetails(), HttpStatus.OK);
    }
    */
    /**
     * Controller for getting the customer details
     * @param token getting the JWT for authorization
     * @return httpStatus 200 + new Token in the header
     * @throws MalformedJwtException for wrong JWT
     */
    @PostMapping("customer/getDetails")
    public ResponseEntity<?> getCustomerDetails(@RequestHeader(name = "Authorization") String token) throws MalformedJwtException {
        if (jwtUtil.validateToken(token)) {
            return ResponseEntity.ok().headers(getHeaders(token)).body(customerService.getCustomerDetails());
        }
        return new ResponseEntity<>(HttpStatus.UNAVAILABLE_FOR_LEGAL_REASONS);
    }

    //----------------------------- GET COUPONS --------------------------------

    /*
    @SneakyThrows
    @PostMapping("coupons/getCoupons")
    public ResponseEntity<?> getCustomerCoupons() {
        return new ResponseEntity<>(customerService.getCustomerCoupons(), HttpStatus.OK);
    }
    */
    /**
     * Controller for getting all logged in Customer coupons
     * @param token getting the JWT for authorization
     * @return httpStatus 200 + new token in Header
     * @throws MalformedJwtException for wrong JWT
     */
    @PostMapping("coupons/getCoupons")
    public ResponseEntity<?> getCustomerCoupons(@RequestHeader(name = "Authorization") String token) throws MalformedJwtException {
        if (jwtUtil.validateToken(token)) {
            return ResponseEntity.ok().headers(getHeaders(token)).body(customerService.getCustomerCoupons());
        }
        return new ResponseEntity<>(HttpStatus.UNAVAILABLE_FOR_LEGAL_REASONS);
    }

    //------------------------- GET COMPANY COUPONS BY CATEGORY --------------------------------

    /**
     * Controller for getting all logged in customer coupons by Category
     * @param token getting the JWT for authorization
     * @param category finding the coupons by Category selected
     * @return httpStatus 200 + new Token in header
     * @throws MalformedJwtException for wrong JWT
     */
    @PostMapping("coupons/getCouponsByCategory/{category}")
    public ResponseEntity<?> getAllCustomersCouponsByCategory(@RequestHeader(name = "Authorization") String token, @PathVariable Category category) throws MalformedJwtException {
        if (jwtUtil.validateToken(token)) {
            return ResponseEntity.ok().headers(getHeaders(token)).body(customerService.getCustomerCouponsByCategory(category));
        }
        return new ResponseEntity<>(HttpStatus.UNAVAILABLE_FOR_LEGAL_REASONS);
    }

    //------------------------- GET CUSTOMER COUPONS BY MAXPRICE --------------------------------

    /**
     * Controller for getting all logged in customer coupons by Max Price
     * @param token getting the JWT for authorization
     * @param maxPrice finding the coupons by maxPrice selected
     * @return httpStatus 200 + new Token in header
     * @throws MalformedJwtException for wrong JWT
     */
    @PostMapping("coupons/getCouponsByMaxPrice/{maxPrice}")
    public ResponseEntity<?> getAllCompanyCouponsByMaxPrice(@RequestHeader(name = "Authorization") String token, @PathVariable double maxPrice) throws MalformedJwtException {
        if (jwtUtil.validateToken(token)) {
            return ResponseEntity.ok().headers(getHeaders(token)).body(customerService.getCustomerCouponsByMaxPrice(maxPrice));
        }
        return new ResponseEntity<>(HttpStatus.UNAVAILABLE_FOR_LEGAL_REASONS);
    }

    //---------------------------- GET ONE COUPON (for REACT) --------------------------

    @PostMapping("coupon/{id}")
    public ResponseEntity<?> getOneCoupon(@RequestHeader(name = "Authorization") String token, @PathVariable int id) throws MalformedJwtException, CouponException {
        if (jwtUtil.validateToken(token)) {
            return ResponseEntity.ok().headers(getHeaders(token)).body(customerService.getOneCoupon(id));
        }
        return new ResponseEntity<>(HttpStatus.UNAVAILABLE_FOR_LEGAL_REASONS);
    }
}

