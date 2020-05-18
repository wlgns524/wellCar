package com.rightcode.wellcar.network.responser.ticketHistory;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;
import com.rightcode.wellcar.network.model.CommonResult;
import com.rightcode.wellcar.network.model.response.ticketHistory.StoreReviewManagement;

import java.util.ArrayList;

import lombok.Data;
import lombok.EqualsAndHashCode;

@JsonObject
@Data
@EqualsAndHashCode(callSuper = false)
public class TicketHistoryListResponser extends CommonResult {

    @JsonField
    ArrayList<StoreReviewManagement> list;
}
