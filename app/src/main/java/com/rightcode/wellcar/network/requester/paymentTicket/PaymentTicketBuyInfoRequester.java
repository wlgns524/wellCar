package com.rightcode.wellcar.network.requester.paymentTicket;

import android.content.Context;

import com.rightcode.wellcar.network.model.request.payment.PaymentEstimateBuyInfo;
import com.rightcode.wellcar.network.model.request.payment.PaymentTicketBuyInfo;
import com.rightcode.wellcar.network.requester.AbstractRequester;

import lombok.Setter;
import retrofit2.Call;

public class PaymentTicketBuyInfoRequester extends AbstractRequester {

    @Setter
    private PaymentTicketBuyInfo param;

    public PaymentTicketBuyInfoRequester(Context context) {
        super(context);
    }

    @Override
    protected Call genApi() {
        Call call = networkManager.getApi().paymentTicketBuyInfo(param);

        return call;
    }
}
