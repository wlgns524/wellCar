package com.rightcode.wellcar.network.requester.notification;

import android.content.Context;

import com.rightcode.wellcar.network.requester.AbstractRequester;

import lombok.Setter;
import retrofit2.Call;

public class NotificationRegisterRequester extends AbstractRequester {

    @Setter
    private String notificationToken;

    public NotificationRegisterRequester(Context context) {
        super(context);
    }

    @Override
    protected Call genApi() {
        Call call = networkManager.getApi().notificationRegister(notificationToken);

        return call;
    }
}
