package com.rightcode.wellcar.network.responser.estimate;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;
import com.rightcode.wellcar.network.model.CommonResult;
import com.rightcode.wellcar.network.model.response.estimateStore.EstimateStoreList;

import java.util.ArrayList;

import lombok.Data;
import lombok.EqualsAndHashCode;

@JsonObject
@Data
@EqualsAndHashCode(callSuper = false)
public class EstimateStoreListResponser extends CommonResult {

    @JsonField
    ArrayList<EstimateStoreList> list;
}
