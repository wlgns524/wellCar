package com.rightcode.wellcar.network.responser;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;
import com.rightcode.wellcar.network.model.CommonResult;

import lombok.Getter;


@JsonObject
public class CommonResponser {
    @JsonField
    @Getter
    CommonResult commonResult;

}
