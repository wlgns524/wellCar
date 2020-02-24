package com.rightcode.wellcar.network.model;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;
import com.rightcode.wellcar.network.error.ServerResultCode;

import lombok.Getter;
import lombok.ToString;

@JsonObject
@Getter @ToString
public class Status {
    @JsonField
    ServerResultCode code;
    @JsonField
    String title;
    @JsonField
    String msg;
}