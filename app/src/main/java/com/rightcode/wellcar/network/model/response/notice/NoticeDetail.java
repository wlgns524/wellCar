package com.rightcode.wellcar.network.model.response.notice;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

import lombok.Data;
import lombok.EqualsAndHashCode;

@JsonObject
@Data
@EqualsAndHashCode(callSuper = false)
public class NoticeDetail {

    @JsonField
    Integer id;
    @JsonField
    String title;
    @JsonField
    String content;
    @JsonField
    String createdAt;
}
