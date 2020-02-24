package com.rightcode.wellcar.network.model.request.user;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

import lombok.Setter;

@JsonObject
public class UserUpdate {

    @JsonField
    @Setter
    String password;
    @JsonField
    @Setter
    String name;
    @JsonField
    @Setter
    String birth;
}
