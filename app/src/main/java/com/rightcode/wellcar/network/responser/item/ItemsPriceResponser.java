package com.rightcode.wellcar.network.responser.item;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;
import com.rightcode.wellcar.network.model.CommonResult;
import com.rightcode.wellcar.network.model.response.item.ItemPrice;

import lombok.Data;
import lombok.EqualsAndHashCode;

@JsonObject
@Data
@EqualsAndHashCode(callSuper = false)
public class ItemsPriceResponser extends CommonResult {
    @JsonField
    ItemPrice data;
}
