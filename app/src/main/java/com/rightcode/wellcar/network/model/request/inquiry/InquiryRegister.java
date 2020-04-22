package com.rightcode.wellcar.network.model.request.inquiry;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

import lombok.Setter;

@JsonObject
public class InquiryRegister {

    @JsonField
    @Setter
    String title;
    @JsonField
    @Setter
    String content;
    @JsonField
    @Setter
    String tel;
}
