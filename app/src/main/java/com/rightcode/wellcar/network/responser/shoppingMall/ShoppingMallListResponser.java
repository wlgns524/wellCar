package com.rightcode.wellcar.network.responser.shoppingMall;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;
import com.rightcode.wellcar.network.model.CommonResult;
import com.rightcode.wellcar.network.model.response.shoppingMall.ShoppingMallList;

import java.util.ArrayList;

import lombok.Data;
import lombok.EqualsAndHashCode;

@JsonObject
@Data
@EqualsAndHashCode(callSuper = false)
public class ShoppingMallListResponser extends CommonResult {

    @JsonField
    ArrayList<ShoppingMallList> list;
}
