package com.rightcode.wellcar.network.model.request.auth;


import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

import lombok.Setter;

@JsonObject
public class Login {

    @JsonField
    @Setter
    String loginId;
    @JsonField
    @Setter
    String password;
}