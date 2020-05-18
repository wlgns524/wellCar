package com.rightcode.wellcar.network.model.response.estimate;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;
import com.rightcode.wellcar.network.model.response.item.Item;

import java.util.ArrayList;

import lombok.Data;
import lombok.EqualsAndHashCode;

@JsonObject
@Data
@EqualsAndHashCode(callSuper = false)
public class Estimate {

    @JsonField
    Integer id;
    @JsonField
    String si;
    @JsonField
    String gu;
    @JsonField
    ArrayList<Item> items;
    @JsonField
    Integer storeCount;
}
