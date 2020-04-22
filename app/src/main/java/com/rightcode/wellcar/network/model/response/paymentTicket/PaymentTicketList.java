package com.rightcode.wellcar.network.model.response.paymentTicket;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;
import com.rightcode.wellcar.network.model.response.paymentEstimate.PaymentEstimateStoreDetail;

import lombok.Data;
import lombok.EqualsAndHashCode;

@JsonObject
@Data
@EqualsAndHashCode(callSuper = false)
public class PaymentTicketList {

    @JsonField
    Integer id;
    @JsonField
    String diff;
    @JsonField
    String createdAt;
    @JsonField
    String content;

}