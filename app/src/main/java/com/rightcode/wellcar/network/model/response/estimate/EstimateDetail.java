package com.rightcode.wellcar.network.model.response.estimate;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;
import com.rightcode.wellcar.network.model.response.Image;

import lombok.Data;
import lombok.EqualsAndHashCode;

@JsonObject
@Data
@EqualsAndHashCode(callSuper = false)
public class EstimateDetail {

    @JsonField
    Integer id;
    @JsonField
    String name;
    @JsonField
    Double rate;
    @JsonField
    Integer reviewCount;
    @JsonField
    Integer orderCount;
    @JsonField
    String introduction;
    @JsonField
    String thumbnail;
    @JsonField
    Integer price;
    @JsonField
    Boolean isPayment;
//    @JsonField
//    EstimateDetailStore estimateStore;
}
