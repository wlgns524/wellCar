package com.rightcode.wellcar.network.model.response.paymentEstimate;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

import lombok.Data;
import lombok.EqualsAndHashCode;

@JsonObject
@Data
@EqualsAndHashCode(callSuper = false)
public class PaymentEstimateDetail {

    @JsonField
    Integer id;
    @JsonField
    PaymentEstimateDetailStore estimateStore;

}