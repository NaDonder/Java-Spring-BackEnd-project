package com.Project.CouponProject.JWT;

import com.Project.CouponProject.Beans.ClientType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDetails {

    private String email;
    private String password;
    private ClientType clientType;

}
