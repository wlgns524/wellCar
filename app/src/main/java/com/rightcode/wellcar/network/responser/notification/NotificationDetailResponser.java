package com.rightcode.wellcar.network.responser.notification;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;
import com.rightcode.wellcar.network.model.CommonResult;
import com.rightcode.wellcar.network.model.response.notification.Notification;
import com.rightcode.wellcar.network.model.response.paymentEstimate.PaymentEstimateDetail;

import lombok.Data;
import lombok.EqualsAndHashCode;

@JsonObject
@Data
@EqualsAndHashCode(callSuper = false)
public class NotificationDetailResponser extends CommonResult {

    @JsonField
    Notification notification;
}
