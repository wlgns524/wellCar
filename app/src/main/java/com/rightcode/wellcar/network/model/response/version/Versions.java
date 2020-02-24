package com.rightcode.wellcar.network.model.response.version;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

import lombok.Data;
import lombok.EqualsAndHashCode;

@JsonObject
@Data
@EqualsAndHashCode(callSuper = false)
public class Versions {

    @JsonField
    Integer ios;
    @JsonField
    Integer android;
}
