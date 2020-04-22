package com.rightcode.wellcar.network.model.response.store;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;
import com.rightcode.wellcar.network.model.response.Image;

import java.util.ArrayList;

import lombok.Data;
import lombok.EqualsAndHashCode;

@JsonObject
@Data
@EqualsAndHashCode(callSuper = false)
public class StoreDetail {

    @JsonField
    Integer id;
    @JsonField
    Integer userId;
    @JsonField
    String name;
    @JsonField
    String manager;
    @JsonField
    String address;
    @JsonField
    String addressDetail;
    @JsonField
    String introduction;
    @JsonField
    String opens;
    @JsonField
    Boolean active;
    @JsonField
    Integer reviewCount;
    @JsonField
    Integer orderCount;
    @JsonField
    Image thumbnail;
    @JsonField
    Image detailImage;
    @JsonField
    ArrayList<Image> images;

}