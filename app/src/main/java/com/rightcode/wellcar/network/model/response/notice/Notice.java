package com.rightcode.wellcar.network.model.response.notice;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

import lombok.Data;
import lombok.EqualsAndHashCode;

@JsonObject
@Data
@EqualsAndHashCode(callSuper = false)
public class Notice {

    @JsonField
    Integer id;
    @JsonField
    String title;
    @JsonField
    String createdAt;
}
