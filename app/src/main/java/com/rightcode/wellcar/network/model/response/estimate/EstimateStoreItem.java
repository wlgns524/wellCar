package com.rightcode.wellcar.network.model.response.estimate;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

import lombok.Data;

@Data
@JsonObject
public class EstimateStoreItem {
    @JsonField
    Integer id;
    @JsonField
    String name;
}
