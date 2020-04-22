package com.rightcode.wellcar.network.model.response.user;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

import lombok.Data;
import lombok.EqualsAndHashCode;

@JsonObject
@Data
@EqualsAndHashCode(callSuper = false)
public class UserGeneral {

    @JsonField
    Integer id;
    @JsonField
    String nickname;
    @JsonField
    String address;
    @JsonField
    String addressDetail;
    @JsonField
    String birth;
}
