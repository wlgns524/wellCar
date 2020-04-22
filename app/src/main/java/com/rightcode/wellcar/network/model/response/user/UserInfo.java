package com.rightcode.wellcar.network.model.response.user;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;
import com.rightcode.wellcar.Util.DataEnums;
import com.rightcode.wellcar.network.model.response.car.Car;

import lombok.Data;
import lombok.EqualsAndHashCode;

@JsonObject
@Data
@EqualsAndHashCode(callSuper = false)
public class UserInfo {

    @JsonField
    String tel;
    @JsonField
    Integer id;
    @JsonField
    String loginId;
    @JsonField
    Integer carWashTicket;
    @JsonField
    Integer point;
    @JsonField
    String role;
    @JsonField
    Car car;
    @JsonField
    UserCompany company;
    @JsonField
    UserStore store;
    @JsonField
    UserGeneral general;

//    @JsonField
//    String password;
//    @JsonField
//    Integer carId;
//    @JsonField
//    String updatedAt;

    public DataEnums.UserType getRole() {
        if (role.equals("업체")) {
            return DataEnums.UserType.COMPANY;
        } else if (role.equals("고객")) {
            return DataEnums.UserType.CUSTOMER;
        } else {
            return null;
        }
    }
}