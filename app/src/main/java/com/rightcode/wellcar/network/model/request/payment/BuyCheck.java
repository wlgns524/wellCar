package com.rightcode.wellcar.network.model.request.payment;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

import lombok.Setter;

@JsonObject
public class BuyCheck {

    @JsonField
    @Setter
    String implUid;
    @JsonField
    @Setter
    String merchantUid;
}
