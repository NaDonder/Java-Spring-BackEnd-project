package com.Project.CouponProject.Controllers;

import com.Project.CouponProject.Beans.Category;
import com.Project.CouponProject.Beans.ClientType;
import com.Project.CouponProject.Beans.Coupon;
import com.Project.CouponProject.Exceptions.CompanyException;
import com.Project.CouponProject.Exceptions.CouponException;
import com.Project.CouponProject.Exceptions.LoginException;
import com.Project.CouponProject.JWT.JwtUtil;
import com.Project.CouponProject.JWT.UserDetails;
import com.Project.CouponProject.Login.LoginManager;
import com.Project.CouponProject.Repositories.CouponRepo;
import com.Project.CouponProject.Services.CompanyService;
import io.jsonwebtoken.MalformedJwtException;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("company")
@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*")
@RequiredArgsConstructor
public class CompanyController {

    private CompanyService companyService = null;
    private final LoginManager loginManager;
    private final JwtUtil jwtUtil;
    private final CouponRepo couponRepo;
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
     * Controller for company login
     * @param userDetails checking the userDetails fields (Email, Password, ClientType)
     * @return the customer ID and JWT token
     */
    @PostMapping("login")
    public ResponseEntity<?> login(@RequestBody UserDetails userDetails) {
        try {
            companyService = (CompanyService) loginManager.login(userDetails.getEmail(), userDetails.getPassword(), userDetails.getClientType());
        } catch (LoginException error) {
            System.out.println(error.getMessage());
            return ResponseEntity.badRequest().body(error.getMessage());
        }
        return ResponseEntity.ok(jwtUtil.generateToken(userDetails));
    }

    //----------------------------- ADD --------------------------------

    /**
     * Controller for adding coupon to the system
     * @param token JWT token for authorization
     * @param coupon coupon we're adding to the system
     * @return httpStatus + new JWT
     * @throws MalformedJwtException for wrong JWT
     * @throws CouponException if coupon exists
     */
    /*
    @PostMapping("coupon/add")
    public ResponseEntity<?> addCoupon(@RequestHeader(name = "Authorization") String token, @RequestBody Coupon coupon) {
        try {
            if (jwtUtil.validateToken(token)) {
                try {
                    companyService.addCoupon(coupon);
                } catch (CouponException error) {
                    System.out.println(error.getMessage());
                    return new ResponseEntity<>(error.getMessage(), HttpStatus.FORBIDDEN);
                }
                return new ResponseEntity<>("Coupon " + coupon.getTitle() + " was added", HttpStatus.CREATED);
            }
        } catch (MalformedJwtException error) {
            System.out.println(" >>>>> I HATE JWT ----- WRONG JWT INSERTED <<<<<");
        }
        return new ResponseEntity<>("Invalid token", HttpStatus.UNAUTHORIZED);
    }
    */
    @PostMapping("coupon/add")
    public ResponseEntity<?> addCoupon(@RequestHeader(name = "Authorization") String token, @RequestBody Coupon coupon) throws MalformedJwtException, CouponException {
        if (jwtUtil.validateToken(token)) {
            companyService.addCoupon(coupon);
        }
        return ResponseEntity.ok().headers(getHeaders(token)).body("Coupon was added!!!");
    }

    //----------------------------- UPDATE --------------------------------

    /**
     * Controller to update the coupon
     * @param token token for authorization
     * @param coupon the coupon
     * @return httpStatus + new JWT
     * @throws MalformedJwtException for wrong JWT
     * @throws CouponException if coupon exist by name or belong to different company
     */
    /*
    @PostMapping("coupon/update")
    public ResponseEntity<?> updateCoupon(@RequestHeader(name = "Authorization") String token, @RequestBody Coupon coupon) {
        try {
            if (jwtUtil.validateToken(token)) {
                try {
                    companyService.updateCoupon(coupon);
                } catch (CouponException error) {
                    System.out.println(error.getMessage());
                    return new ResponseEntity<>(error.getMessage(), HttpStatus.FORBIDDEN);
                }
                return new ResponseEntity<>("Coupon was updated!", HttpStatus.OK);
            }
        } catch (MalformedJwtException error) {
            System.out.println(" >>>>> I HATE JWT ----- WRONG JWT INSERTED <<<<<");
        }
        return new ResponseEntity<>("Invalid token", HttpStatus.UNAUTHORIZED);
    }
    */
    @PostMapping("coupon/update")
    public ResponseEntity<?> updateCoupon(@RequestHeader(name = "Authorization") String token, @RequestBody Coupon coupon) throws MalformedJwtException, CouponException {
        if (jwtUtil.validateToken(token)) {
            companyService.updateCoupon(coupon);
        }
        return ResponseEntity.ok().headers(getHeaders(token)).body("Customer updated!");
    }

    //----------------------------- DELETE --------------------------------

    /**
     * Controller to delete coupon
     * @param token JWT for authorization
     * @param id id of the coupon we will delete
     * @return httpStatus + new jWT
     * @throws MalformedJwtException for wrong JWT
     * @throws CouponException if coupon doesn't exist
     */
    /*
    @DeleteMapping("deleteCoupon/{id}")
    public ResponseEntity<?> deleteCoupon(@RequestHeader(name = "Authorization") String token, @PathVariable int id) {
        try {
            if (jwtUtil.validateToken(token)) {
                try {
                    companyService.deleteCoupon(id);
                } catch (CouponException error) {
                    System.out.println(error.getMessage());
                    return new ResponseEntity<>(error.getMessage(), HttpStatus.FORBIDDEN);
                }
                return new ResponseEntity<>("Coupon was deleted Successfully", HttpStatus.OK);
            }
        } catch (MalformedJwtException error) {
            System.out.println(" >>>>> I HATE JWT ----- WRONG JWT INSERTED <<<<<");
        }
        return new ResponseEntity<>("Invalid token", HttpStatus.UNAUTHORIZED);
    }
    */
    @DeleteMapping("deleteCoupon/{id}")
    public ResponseEntity<?> deleteCoupon(@RequestHeader(name = "Authorization") String token, @PathVariable int id) throws MalformedJwtException, CouponException {
        if (jwtUtil.validateToken(token)) {
            companyService.deleteCoupon(id);
        }
        return ResponseEntity.ok().headers(getHeaders(token)).body("Coupon was deleted Successfully");
    }

    //----------------------------- GET COMPANY COUPONS --------------------------------

    /**
     * Controller to get all company Coupons
     * @param token for JWT authorization
     * @return httpStatus + new JWT
     * @throws MalformedJwtException for wrong JWT
     */
    /*
    @PostMapping("company/getCoupons")
    public ResponseEntity<?> getAllCompanyCoupons(@RequestHeader(name = "Authorization") String token) {
        try {
            if (jwtUtil.validateToken(token)) {
                return new ResponseEntity<>(companyService.getCompanyCoupons(), HttpStatus.OK);
            }
        } catch (MalformedJwtException error) {
            System.out.println(" >>>>> I HATE JWT ----- WRONG JWT INSERTED <<<<<");
        }
        return new ResponseEntity<>("Invalid Token", HttpStatus.UNAUTHORIZED);
    }
    */
    @PostMapping("company/getCoupons")
    public ResponseEntity<?> getAllCompanyCoupons (@RequestHeader(name = "Authorization") String token) throws MalformedJwtException {
        if (jwtUtil.validateToken(token)) {
            return ResponseEntity.ok().headers(getHeaders(token)).body(companyService.getCompanyCoupons());
        }
        return new ResponseEntity<>(HttpStatus.UNAVAILABLE_FOR_LEGAL_REASONS);
    }

    //------------------------- GET COMPANY COUPONS BY CATEGORY --------------------------------

    /**
     * Controller for getting all company coupons by category
     * @param token JWT for authorization
     * @param category the selected category
     * @return httpStatus + new JWT
     * @throws MalformedJwtException for wrong JWT
     */
    /*
    @PostMapping("company/getCouponsByCategory/{category}")
    public ResponseEntity<?> getAllCompanyCouponsByCategory(@RequestHeader(name = "Authorization") String token, @PathVariable Category category) {
        try {
            if (jwtUtil.validateToken(token)) {
                return new ResponseEntity<>(companyService.getCompanyCouponsByCategory(category), HttpStatus.OK);
            }
        } catch (MalformedJwtException error) {
            System.out.println(" >>>>> I HATE JWT ----- WRONG JWT INSERTED <<<<<");
        }
        return new ResponseEntity<>("Invalid Token", HttpStatus.UNAUTHORIZED);
    }
    */
    @PostMapping("company/getCouponsByCategory/{category}")
    public ResponseEntity<?> getAllCompanyCouponsByCategory(@RequestHeader(name = "Authorization") String token, @PathVariable Category category) throws MalformedJwtException {
        if (jwtUtil.validateToken(token)) {
            return ResponseEntity.ok().headers(getHeaders(token)).body(companyService.getCompanyCouponsByCategory(category));
        }
        return new ResponseEntity<>(HttpStatus.UNAVAILABLE_FOR_LEGAL_REASONS);
    }


    //------------------------- GET COMPANY COUPONS BY MAXPRICE --------------------------------

    /**
     * Controller for getting all company coupons by max Price
     * @param token JWT for authorization
     * @param maxPrice the selected Max Price
     * @return httpStatus + new JWT
     * @throws MalformedJwtException for wrong JWT
     */
    /*
    @PostMapping("company/getCouponsByMaxPrice/{maxPrice}")
    public ResponseEntity<?> getAllCompanyCouponsByMaxPrice(@RequestHeader(name = "Authorization") String token, @PathVariable int maxPrice) {
        try {
            if (jwtUtil.validateToken(token)) {
                return new ResponseEntity<>(companyService.getCompanyCouponsByMaxPrice(maxPrice), HttpStatus.OK);
            }
        } catch (MalformedJwtException error) {
            System.out.println(" >>>>> I HATE JWT ----- WRONG JWT INSERTED <<<<<");
        }
        return new ResponseEntity<>("Invalid Token", HttpStatus.UNAUTHORIZED);
    }
    */
    @PostMapping("company/getCouponsByMaxPrice/{maxPrice}")
    public ResponseEntity<?> getAllCompanyCouponsByMaxPrice(@RequestHeader(name = "Authorization") String token, @PathVariable double maxPrice) throws MalformedJwtException {
        if (jwtUtil.validateToken(token)) {
            return ResponseEntity.ok().headers(getHeaders(token)).body(companyService.getCompanyCouponsByMaxPrice(maxPrice));
        }
        return new ResponseEntity<>(HttpStatus.UNAVAILABLE_FOR_LEGAL_REASONS);
    }


    //-------------------------- GET COMPANY DETAILS -------------------------------

    /**
     * Controller for getting logged in company details
     * @param token for JWT authorization
     * @return httpStatus + new JWT
     * @throws MalformedJwtException for wrong JWT
     */
    /*
    @PostMapping("company/getCompanyDetails")
    public ResponseEntity<?> getCompanyDetails(@RequestHeader(name = "Authorization") String token) {
        try {
            if (jwtUtil.validateToken(token)) {
                return new ResponseEntity<>(companyService.getCompanyDetails(), HttpStatus.OK);
            }
        } catch (MalformedJwtException error) {
            System.out.println(" >>>>> I HATE JWT ----- WRONG JWT INSERTED <<<<<");
        }
        return new ResponseEntity<>("Invalid Token", HttpStatus.UNAUTHORIZED);
    }
    */
    @PostMapping("company/getCompanyDetails")
    public ResponseEntity<?> getCompanyDetails(@RequestHeader(name = "Authorization") String token) throws MalformedJwtException {
        if (jwtUtil.validateToken(token)) {
            return ResponseEntity.ok().headers(getHeaders(token)).body(companyService.getCompanyDetails());
        }
        return new ResponseEntity<>(HttpStatus.UNAVAILABLE_FOR_LEGAL_REASONS);
    }

    //---------------------------- GET ONE COUPON (for REACT) --------------------------

    /**
     * Controller for getting single coupon
     * @param token token for authorization
     * @param id getting by ID
     * @return httpStatus + new JWT
     * @throws MalformedJwtException for wrong JWT
     * @throws CouponException if doesn't exist by ID
     */
    @PostMapping("coupon/{id}")
    public ResponseEntity<?> getOneCoupon(@RequestHeader(name = "Authorization") String token, @PathVariable int id) throws MalformedJwtException, CouponException {
        if (jwtUtil.validateToken(token)) {
            return ResponseEntity.ok().headers(getHeaders(token)).body(companyService.getOneCoupon(id));
        }
        return new ResponseEntity<>(HttpStatus.UNAVAILABLE_FOR_LEGAL_REASONS);
    }


}
