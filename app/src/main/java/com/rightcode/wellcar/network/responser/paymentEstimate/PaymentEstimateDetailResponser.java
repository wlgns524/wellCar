package com.rightcode.wellcar.network.responser.paymentEstimate;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;
import com.rightcode.wellcar.network.model.CommonResult;
import com.rightcode.wellcar.network.model.response.paymentEstimate.PaymentEstimateDetail;
import com.rightcode.wellcar.network.model.response.paymentEstimate.PaymentEstimateList;

import java.util.ArrayList;

import lombok.Data;
import lombok.EqualsAndHashCode;

@JsonObject
@Data
@EqualsAndHashCode(callSuper = false)
public class PaymentEstimateDetailResponser extends CommonResult {

    @JsonField
    PaymentEstimateDetail data;
}
