package com.rightcode.wellcar.network.model.request.payment;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

import lombok.Setter;

@JsonObject
public class PaymentEstimateBuyInfo {

    @JsonField
    @Setter
    String payMethod;
    @JsonField
    @Setter
    Integer estimateId;
    @JsonField
    @Setter
    Integer storeId;
}
