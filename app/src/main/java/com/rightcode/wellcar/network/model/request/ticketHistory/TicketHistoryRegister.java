package com.rightcode.wellcar.network.model.request.ticketHistory;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

import lombok.Setter;

@JsonObject
public class TicketHistoryRegister {

    @JsonField
    @Setter
    String code;
}
