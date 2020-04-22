package com.rightcode.wellcar.network.responser.auth;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;
import com.bluelinelabs.logansquare.annotation.OnJsonParseComplete;
import com.rightcode.wellcar.network.model.CommonResult;
import com.rightcode.wellcar.network.model.response.user.UserInfo;

import lombok.Data;
import lombok.EqualsAndHashCode;

@JsonObject
@Data
@EqualsAndHashCode(callSuper = false)
public class LoginResponser extends CommonResult {

    @JsonField
    String token;
    @JsonField
    String role;
    @JsonField
    UserInfo userInfo;

    @OnJsonParseComplete
    void onParseComplete() {
    }
}
