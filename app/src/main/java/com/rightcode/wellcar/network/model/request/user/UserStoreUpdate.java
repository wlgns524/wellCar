package com.rightcode.wellcar.network.model.request.user;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

import lombok.Setter;

@JsonObject
public class UserStoreUpdate {

    @JsonField
    @Setter
    String name;
    @JsonField
    @Setter
    String address;
    @JsonField
    @Setter
    String addressDetail;
    @JsonField
    @Setter
    String introduction;
    @JsonField
    @Setter
    String opens;
}
