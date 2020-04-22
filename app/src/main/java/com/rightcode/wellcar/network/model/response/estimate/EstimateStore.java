package com.rightcode.wellcar.network.model.response.estimate;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

import java.util.ArrayList;

import lombok.Data;
import lombok.EqualsAndHashCode;

@JsonObject
@Data
@EqualsAndHashCode(callSuper = false)
public class EstimateStore {

    @JsonField
    Integer id;
    @JsonField
    ArrayList<EstimateDetail> stores;
}
