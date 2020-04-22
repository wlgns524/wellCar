package com.rightcode.wellcar.network.model.request.auth;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

import lombok.Setter;

@JsonObject
public class Join {

    @JsonField
    @Setter
    String loginId;
    @JsonField
    @Setter
    String password;
    @JsonField
    @Setter
    String tel;
    @JsonField
    @Setter
    String address;
    @JsonField
    @Setter
    String addressDetail;
    @JsonField
    @Setter
    String role;

    // role - 업체
    @JsonField
    @Setter
    String name; //업체이름
    @JsonField
    @Setter
    String owner;
    @JsonField
    @Setter
    String companyNumber;
    @JsonField
    @Setter
    String businessLicense;

    // role - 일반
    @JsonField
    @Setter
    String nickname;
    @JsonField
    @Setter
    String birth;
}
