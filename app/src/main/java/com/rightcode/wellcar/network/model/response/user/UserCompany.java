package com.rightcode.wellcar.network.model.response.user;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

import lombok.Data;
import lombok.EqualsAndHashCode;

@JsonObject
@Data
@EqualsAndHashCode(callSuper = false)
public class UserCompany {

    @JsonField
    Integer id;
    @JsonField
    String name;
    @JsonField
    String businessLicense;
    @JsonField
    String owner;
    @JsonField
    String companyNumber;
    @JsonField
    String address;
    @JsonField
    String addressDetail;
}
