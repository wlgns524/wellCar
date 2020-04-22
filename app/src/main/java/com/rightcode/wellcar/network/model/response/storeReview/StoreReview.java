package com.rightcode.wellcar.network.model.response.storeReview;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;
import com.rightcode.wellcar.network.model.response.store.Thumbnail;

import java.io.Serializable;
import java.util.ArrayList;

import lombok.Data;
import lombok.EqualsAndHashCode;

@JsonObject
@Data
@EqualsAndHashCode(callSuper = false)
public class StoreReview implements Serializable {

    @JsonField
    Integer id;
    @JsonField
    String content;
    @JsonField
    Double rate;
    @JsonField
    String createdAt;
    @JsonField
    StoreReviewUser user;
    @JsonField
    StoreReviewStore store;
    @JsonField
    ArrayList<Thumbnail> storeReviewImages;
}
