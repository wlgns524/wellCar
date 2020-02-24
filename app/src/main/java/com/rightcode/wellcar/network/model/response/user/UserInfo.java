package com.rightcode.wellcar.network.model.response.user;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

import lombok.Data;
import lombok.EqualsAndHashCode;

@JsonObject
@Data
@EqualsAndHashCode(callSuper = false)
public class UserInfo {

    @JsonField
    String loginId;
    @JsonField
    String id;
    @JsonField
    String tel;
    @JsonField
    String name;
    @JsonField
    String profile;
    @JsonField
    String birth;
    @JsonField
    String job;
    @JsonField
    Boolean notification;
    @JsonField
    String role;
    @JsonField
    Boolean active;
}