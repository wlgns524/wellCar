package com.rightcode.wellcar.network.responser.paymentTicket;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;
import com.rightcode.wellcar.network.model.CommonResult;
import com.rightcode.wellcar.network.model.response.paymentEstimate.PaymentEstimateList;
import com.rightcode.wellcar.network.model.response.paymentTicket.PaymentTicketList;

import java.util.ArrayList;

import lombok.Data;
import lombok.EqualsAndHashCode;

@JsonObject
@Data
@EqualsAndHashCode(callSuper = false)
public class PaymentTicketListResponser extends CommonResult {

    @JsonField
    ArrayList<PaymentTicketList> list;
}
