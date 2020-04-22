package com.rightcode.wellcar.network.requester.payment;

import android.content.Context;

import com.rightcode.wellcar.network.model.request.payment.BuyCheck;
import com.rightcode.wellcar.network.model.request.payment.PaymentEstimateBuyInfo;
import com.rightcode.wellcar.network.requester.AbstractRequester;

import lombok.Setter;
import retrofit2.Call;

public class PaymentBuyCheckRequester extends AbstractRequester {

    @Setter
    private BuyCheck param;

    public PaymentBuyCheckRequester(Context context) {
        super(context);
    }

    @Override
    protected Call genApi() {
        Call call = networkManager.getApi().paymentBuyCheck(param);

        return call;
    }
}
