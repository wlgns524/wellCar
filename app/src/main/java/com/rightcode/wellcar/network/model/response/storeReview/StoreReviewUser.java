package com.rightcode.wellcar.network.model.response.storeReview;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;

@JsonObject
@Data
@EqualsAndHashCode(callSuper = false)
public class StoreReviewUser implements Serializable {

    @JsonField
    Integer id;
    @JsonField
    StoreReviewGeneral general;
}
