package com.rightcode.wellcar.network.model.response.paymentEstimate;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;
import com.rightcode.wellcar.network.model.response.Image;

import lombok.Data;
import lombok.EqualsAndHashCode;

@JsonObject
@Data
@EqualsAndHashCode(callSuper = false)
public class PaymentEstimateDetailStore {

    @JsonField
    Integer id;
    @JsonField
    PaymentEstimateDetailStoreEstimate estimate;
    @JsonField
    PaymentEstimateDetailStoreStore store;

}