package com.rightcode.wellcar.network.responser.accountCompany;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;
import com.rightcode.wellcar.network.model.CommonResult;
import com.rightcode.wellcar.network.model.response.accountCompany.AccountCompanyData;
import com.rightcode.wellcar.network.model.response.accountCompany.AccountCompanyList;

import java.util.ArrayList;

import lombok.Data;
import lombok.EqualsAndHashCode;

@JsonObject
@Data
@EqualsAndHashCode(callSuper = false)
public class SettlementListResponser extends CommonResult {

    @JsonField
    AccountCompanyData data;
    @JsonField
    ArrayList<AccountCompanyList> list;
}
