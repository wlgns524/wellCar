package com.rightcode.wellcar.network.model.request.user;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

import lombok.Setter;

@JsonObject
public class UserUpdate {

    @JsonField
    @Setter
    String password;
    @JsonField
    @Setter
    String name;
    @JsonField
    @Setter
    String owner;
    @JsonField
    @Setter
    String companyNumber;
    @JsonField
    @Setter
    String nickname;
    @JsonField
    @Setter
    String address;
    @JsonField
    @Setter
    String addressDetail;
    @JsonField
    @Setter
    String businessLicense;
    @JsonField
    @Setter
    String birth;
    @JsonField
    @Setter
    Integer carId;
}
