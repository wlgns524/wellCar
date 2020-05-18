package com.rightcode.wellcar.network.model.request.estimateStore;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

import java.io.Serializable;
import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;

@JsonObject
@Getter
public class EstimateStoreUpdate implements Serializable {

    @JsonField
    @Setter
    Integer price;

}
