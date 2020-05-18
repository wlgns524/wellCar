package com.rightcode.wellcar.network.model.request.storeReview;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

import lombok.Setter;

@JsonObject
public class StoreReviewRegister {

    @JsonField
    @Setter
    String content;
    @JsonField
    @Setter
    Float satisfaction;
    @JsonField
    @Setter
    Float kindness;
    @JsonField
    @Setter
    Integer estimateId;
    @JsonField
    @Setter
    Integer ticketHistoryId;
}
