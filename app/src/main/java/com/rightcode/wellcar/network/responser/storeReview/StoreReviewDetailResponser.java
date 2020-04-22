package com.rightcode.wellcar.network.responser.storeReview;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;
import com.rightcode.wellcar.network.model.CommonResult;
import com.rightcode.wellcar.network.model.response.storeReview.StoreReview;
import com.rightcode.wellcar.network.model.response.storeReview.StoreReviewDetail;

import java.util.ArrayList;

import lombok.Data;
import lombok.EqualsAndHashCode;

@JsonObject
@Data
@EqualsAndHashCode(callSuper = false)
public class StoreReviewDetailResponser extends CommonResult {

    @JsonField
    StoreReviewDetail data;
}
