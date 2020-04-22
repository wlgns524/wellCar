package com.rightcode.wellcar.network.requester.auth;

import android.content.Context;

import com.rightcode.wellcar.Util.DataEnums;
import com.rightcode.wellcar.network.requester.AbstractRequester;

import lombok.Setter;
import retrofit2.Call;

public class CertificationNumberSMSRequester extends AbstractRequester {

    @Setter
    private String tel;
    @Setter
    private DataEnums.DiffType diff;

    public CertificationNumberSMSRequester(Context context) {
        super(context);
    }

    @Override
    protected Call genApi() {
        Call call = networkManager.getApi().certificationNumberSMS(tel, diff.toString());

        return call;
    }
}
