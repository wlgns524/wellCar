package com.rightcode.wellcar.network.responser.estimate;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;
import com.rightcode.wellcar.network.model.CommonResult;
import com.rightcode.wellcar.network.model.response.estimate.Estimate;
import com.rightcode.wellcar.network.model.response.item.Item;

import java.util.ArrayList;

import lombok.Data;
import lombok.EqualsAndHashCode;

@JsonObject
@Data
@EqualsAndHashCode(callSuper = false)
public class EstimateListResponser extends CommonResult {

    @JsonField
    ArrayList<Estimate> list;
}
