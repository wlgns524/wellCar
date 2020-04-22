package com.rightcode.wellcar.network.requester.paymentTicket;

import android.content.Context;

import com.rightcode.wellcar.network.requester.AbstractRequester;

import lombok.Setter;
import retrofit2.Call;

public class PaymentTicketListRequester extends AbstractRequester {

    @Setter
    private Integer year;
    @Setter
    private Integer month;

    public PaymentTicketListRequester(Context context) {
        super(context);
    }

    @Override
    protected Call genApi() {
        Call call = networkManager.getApi().paymentTicketList(String.format("%04d-%02d", year, month));

        return call;
    }
}
