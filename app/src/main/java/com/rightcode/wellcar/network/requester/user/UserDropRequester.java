package com.rightcode.wellcar.network.requester.user;

import android.content.Context;

import com.rightcode.wellcar.network.model.request.user.UserUpdate;
import com.rightcode.wellcar.network.requester.AbstractRequester;

import lombok.Setter;
import retrofit2.Call;

public class UserDropRequester extends AbstractRequester {


    public UserDropRequester(Context context) {
        super(context);
    }

    @Override
    protected Call genApi() {
        Call call = networkManager.getApi().userDrop();

        return call;
    }
}
