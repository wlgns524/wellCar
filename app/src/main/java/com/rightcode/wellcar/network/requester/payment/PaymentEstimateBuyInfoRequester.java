package com.rightcode.wellcar.network.requester.payment;

import android.content.Context;

import com.rightcode.wellcar.network.model.request.payment.PaymentEstimateBuyInfo;
import com.rightcode.wellcar.network.requester.AbstractRequester;

import lombok.Setter;
import retrofit2.Call;

public class PaymentEstimateBuyInfoRequester extends AbstractRequester {

    @Setter
    private PaymentEstimateBuyInfo param;

    public PaymentEstimateBuyInfoRequester(Context context) {
        super(context);
    }

    @Override
    protected Call genApi() {
        Call call = networkManager.getApi().paymentEstimateBuyInfo(param);

        return call;
    }
}
