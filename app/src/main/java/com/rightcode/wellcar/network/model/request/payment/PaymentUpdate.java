package com.rightcode.wellcar.network.model.request.payment;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

import lombok.Setter;

@Setter
@JsonObject
public class PaymentUpdate {
    @JsonField
    Boolean isConstruction;
}
