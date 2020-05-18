package com.rightcode.wellcar.network.model.response.ticketHistory;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;
import com.rightcode.wellcar.network.model.response.store.Thumbnail;
import com.rightcode.wellcar.network.model.response.storeReview.StoreReviewStore;
import com.rightcode.wellcar.network.model.response.storeReview.StoreReviewUser;

import java.io.Serializable;
import java.util.ArrayList;

import lombok.Data;
import lombok.EqualsAndHashCode;

@JsonObject
@Data
@EqualsAndHashCode(callSuper = false)
public class StoreReviewManagement {

    @JsonField
    Integer id;
    @JsonField
    String name;
    @JsonField
    Double rate;
    @JsonField
    Integer reviewCount;
    @JsonField
    String introduction;
    @JsonField
    Boolean isReviewMine;
    @JsonField
    String thumbnail;
    @JsonField
    Integer ticketHistoryId;
}
