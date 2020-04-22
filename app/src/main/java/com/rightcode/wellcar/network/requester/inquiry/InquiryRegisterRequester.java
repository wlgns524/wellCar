package com.rightcode.wellcar.network.requester.inquiry;

import android.content.Context;

import com.rightcode.wellcar.network.model.request.inquiry.InquiryRegister;
import com.rightcode.wellcar.network.requester.AbstractRequester;

import lombok.Setter;
import retrofit2.Call;

public class InquiryRegisterRequester extends AbstractRequester {

    @Setter
    private InquiryRegister param;

    public InquiryRegisterRequester(Context context) {
        super(context);
    }

    @Override
    protected Call genApi() {
        Call call = networkManager.getApi().inquiryRegister(param);

        return call;
    }
}
