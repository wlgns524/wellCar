package com.rightcode.wellcar.network.model.response.event;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

import lombok.Data;
import lombok.EqualsAndHashCode;

@JsonObject
@Data
@EqualsAndHashCode(callSuper = false)
public class EventDetail {

    @JsonField
    Integer id;
    @JsonField
    String title;
    @JsonField
    String thumbnail;
    @JsonField
    String image;
    @JsonField
    String url;
    @JsonField
    Integer viewCount;
    @JsonField
    Integer method;
    @JsonField
    Boolean active;
    @JsonField
    Integer sortCode;
}

