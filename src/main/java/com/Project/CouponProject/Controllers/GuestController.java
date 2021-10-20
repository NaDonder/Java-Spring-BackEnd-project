package com.Project.CouponProject.Controllers;

import com.Project.CouponProject.Repositories.CouponRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("guest")
@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*")
@RequiredArgsConstructor
public class GuestController {

    private final CouponRepo couponRepo;

    //----------------------- GETTING ALL COUPONS -------------------------

    /**
     * Controller for getting all coupons
     * @return all coupons from DB
     */
    @PostMapping("/getAllCoupons")
    public ResponseEntity<?> GetAllCoupons(){
        return ResponseEntity.ok(couponRepo.findAll());
    }

}
