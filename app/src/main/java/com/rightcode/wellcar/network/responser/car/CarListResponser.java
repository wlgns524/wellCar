package com.rightcode.wellcar.network.responser.car;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;
import com.rightcode.wellcar.network.model.CommonResult;
import com.rightcode.wellcar.network.model.response.car.Car;
import com.rightcode.wellcar.network.model.response.item.Item;

import java.util.ArrayList;

import lombok.Data;
import lombok.EqualsAndHashCode;

@JsonObject
@Data
@EqualsAndHashCode(callSuper = false)
public class CarListResponser extends CommonResult {

    @JsonField
    ArrayList<Car> list;
}
