package com.rightcode.wellcar.network.requester.payment;

import android.content.Context;

import com.rightcode.wellcar.network.model.request.payment.PaymentEstimateBuyInfo;
import com.rightcode.wellcar.network.requester.AbstractRequester;

import lombok.Setter;
import retrofit2.Call;

public class PaymentEstimateListRequester extends AbstractRequester {

    public PaymentEstimateListRequester(Context context) {
        super(context);
    }

    @Override
    protected Call genApi() {
        Call call = networkManager.getApi().paymentEstimateList();

        return call;
    }
}
