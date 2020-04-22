package com.rightcode.wellcar.network.model.response.car;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;
import com.rightcode.wellcar.network.model.response.Image;
import com.rightcode.wellcar.network.model.response.brand.Brand;

import lombok.Data;
import lombok.EqualsAndHashCode;

@JsonObject
@Data
@EqualsAndHashCode(callSuper = false)
public class Car {

    @JsonField
    Integer id;
    @JsonField
    String name;
    @JsonField
    String modelYear;
    @JsonField
    Image image;
    @JsonField
    Brand brand;
}
