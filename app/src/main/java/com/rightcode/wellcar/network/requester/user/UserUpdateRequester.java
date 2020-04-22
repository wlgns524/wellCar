package com.rightcode.wellcar.network.requester.user;

import android.content.Context;

import com.rightcode.wellcar.network.model.request.user.UserUpdate;
import com.rightcode.wellcar.network.requester.AbstractRequester;

import lombok.Setter;
import retrofit2.Call;

public class UserUpdateRequester extends AbstractRequester {

    @Setter
    UserUpdate param;

    public UserUpdateRequester(Context context) {
        super(context);
    }

    @Override
    protected Call genApi() {
        Call call = networkManager.getApi().userUpdate(param);

        return call;
    }
}
