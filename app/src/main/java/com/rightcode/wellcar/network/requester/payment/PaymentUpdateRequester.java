package com.rightcode.wellcar.network.requester.payment;

import android.content.Context;

import com.rightcode.wellcar.network.model.CommonResult;
import com.rightcode.wellcar.network.model.request.payment.PaymentUpdate;
import com.rightcode.wellcar.network.requester.AbstractRequester;
import com.rightcode.wellcar.network.requester.CommonApiRequester;

import lombok.Setter;
import retrofit2.Call;
import retrofit2.Callback;

public class PaymentUpdateRequester extends AbstractRequester {

    @Setter
    Integer paymentId;

    @Setter
    PaymentUpdate paymentUpdate;

    public PaymentUpdateRequester(Context context) {
        super(context);
    }

    @Override
    protected Call genApi() throws Exception {
        Call call = networkManager.getApi().paymentUpdate(paymentId, paymentUpdate);
        return call;
    }
}
