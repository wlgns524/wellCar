package com.rightcode.wellcar.network.model.request.estimate;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;
import com.rightcode.wellcar.network.model.response.item.Item;

import java.io.Serializable;
import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;

@JsonObject
@Getter
public class EstimateRegister implements Serializable {

    @JsonField
    @Setter
    String si;
    @JsonField
    @Setter
    String gu;
    @JsonField
    @Setter
    ArrayList<Integer> itemIds;
    @JsonField
    @Setter
    ArrayList<Integer> storeIds;
    @JsonField
    @Setter
    String request;
}
