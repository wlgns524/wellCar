package com.rightcode.wellcar.network.requester.payment;

import android.content.Context;

import com.rightcode.wellcar.network.requester.AbstractRequester;

import lombok.Setter;
import retrofit2.Call;

public class PaymentEstimateDetailRequester extends AbstractRequester {

    @Setter
    private Integer paymentId;

    public PaymentEstimateDetailRequester(Context context) {
        super(context);
    }

    @Override
    protected Call genApi() {
        Call call = networkManager.getApi().paymentEstimateDetail(paymentId);

        return call;
    }
}
