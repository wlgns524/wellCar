package com.rightcode.wellcar.network.model.response.accountCompany;


import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

import lombok.Data;
import lombok.EqualsAndHashCode;

@JsonObject
@Data
@EqualsAndHashCode(callSuper = false)
public class AccountCompanyList {

    @JsonField
    Integer id;
    @JsonField
    String name;
    @JsonField
    Integer price;
    @JsonField
    Integer count;
    @JsonField
    String status;
    @JsonField
    String title;
    @JsonField
    String createdAt;
}