package com.rightcode.wellcar.network.requester.user;

import android.content.Context;

import com.rightcode.wellcar.network.model.request.user.UserStoreUpdate;
import com.rightcode.wellcar.network.model.request.user.UserUpdate;
import com.rightcode.wellcar.network.requester.AbstractRequester;

import lombok.Setter;
import retrofit2.Call;

public class UserStoreUpdateRequester extends AbstractRequester {

    @Setter
    UserStoreUpdate param;

    public UserStoreUpdateRequester(Context context) {
        super(context);
    }

    @Override
    protected Call genApi() {
        Call call = networkManager.getApi().userStoreUpdate(param);

        return call;
    }
}
