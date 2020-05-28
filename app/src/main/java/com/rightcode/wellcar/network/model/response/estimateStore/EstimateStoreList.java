package com.rightcode.wellcar.network.model.response.estimateStore;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;
import com.rightcode.wellcar.network.model.response.estimate.EstimateStoreItem;

import java.util.ArrayList;

import lombok.Data;
import lombok.EqualsAndHashCode;

@JsonObject
@Data
@EqualsAndHashCode(callSuper = false)
public class EstimateStoreList {

    @JsonField
    Integer id;
    @JsonField
    String carName;
    @JsonField
    String carModelYear;
    @JsonField
    String userName;
    @JsonField
    String carBrandImageName;
    @JsonField
    String carImageName;
    @JsonField
    ArrayList<EstimateStoreItem> items;
    @JsonField
    Integer price;
    @JsonField
    String request;
    @JsonField
    String createdAt;
}
