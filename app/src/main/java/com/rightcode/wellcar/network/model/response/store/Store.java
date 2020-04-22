package com.rightcode.wellcar.network.model.response.store;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;
import com.rightcode.wellcar.network.model.response.Image;

import lombok.Data;
import lombok.EqualsAndHashCode;

@JsonObject
@Data
@EqualsAndHashCode(callSuper = false)
public class Store {

    @JsonField
    Integer id;
    @JsonField
    String name;
    @JsonField
    Double rate;
    @JsonField
    Integer reviewCount;
    @JsonField
    Integer orderCount;
    @JsonField
    String introduction;
    @JsonField
    String distance;
    @JsonField(name = "thumbnail")
    Image thumbnail;

}
