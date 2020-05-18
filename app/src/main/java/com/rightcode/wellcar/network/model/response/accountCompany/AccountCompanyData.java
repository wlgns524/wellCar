package com.rightcode.wellcar.network.model.response.accountCompany;


import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

import lombok.Data;
import lombok.EqualsAndHashCode;

@JsonObject
@Data
@EqualsAndHashCode(callSuper = false)
public class AccountCompanyData {

    @JsonField
    Integer sales;
    @JsonField
    Integer exchange;
}