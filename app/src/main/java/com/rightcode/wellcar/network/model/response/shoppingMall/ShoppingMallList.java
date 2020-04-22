package com.rightcode.wellcar.network.model.response.shoppingMall;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;
import com.rightcode.wellcar.network.model.response.Image;

import lombok.Data;
import lombok.EqualsAndHashCode;

@JsonObject
@Data
@EqualsAndHashCode(callSuper = false)
public class ShoppingMallList {

    @JsonField
    Integer id;
    @JsonField
    String name;
    @JsonField
    String thumbnail;
    @JsonField
    String url;
    @JsonField
    Integer viewCount;
    @JsonField
    Boolean active;

}
