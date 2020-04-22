package com.rightcode.wellcar.network.requester.auth;

import android.content.Context;

import com.rightcode.wellcar.Util.DataEnums;
import com.rightcode.wellcar.network.requester.AbstractRequester;

import lombok.Setter;
import retrofit2.Call;

public class ConfirmRequester extends AbstractRequester {

    @Setter
    private String tel;
    @Setter
    private String confirm;

    public ConfirmRequester(Context context) {
        super(context);
    }

    @Override
    protected Call genApi() {
        Call call = networkManager.getApi().confirm(tel, confirm);

        return call;
    }
}
