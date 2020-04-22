package com.rightcode.wellcar.network.model.request.payment;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

import lombok.Getter;
import lombok.Setter;

@JsonObject
@Getter
public class PaymentTicketBuyInfo {

    @JsonField
    @Setter
    String payMethod;
    @JsonField
    @Setter
    Integer ticket;
    @JsonField
    @Setter
    Integer price;

}
