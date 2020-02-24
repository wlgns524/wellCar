package com.rightcode.wellcar.network.model;


import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonIgnore;
import com.bluelinelabs.logansquare.annotation.JsonObject;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@JsonObject
@ToString
public class CommonResult {

    @Getter @Setter
    @JsonField(name = "status") @JsonIgnore(ignorePolicy = JsonIgnore.IgnorePolicy.SERIALIZE_ONLY)
    Integer code;
    @Getter @Setter
    @JsonField(name = "result") @JsonIgnore(ignorePolicy = JsonIgnore.IgnorePolicy.SERIALIZE_ONLY)
    Boolean result;
    @Getter @Setter
    @JsonField(name = "message") @JsonIgnore(ignorePolicy = JsonIgnore.IgnorePolicy.SERIALIZE_ONLY)
    String resultMsg;


    public boolean isSuccess() {
        return result;
    }
}