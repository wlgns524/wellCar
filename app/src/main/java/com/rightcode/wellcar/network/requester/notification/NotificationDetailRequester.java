package com.rightcode.wellcar.network.requester.notification;

import android.content.Context;

import com.rightcode.wellcar.network.requester.AbstractRequester;

import lombok.Setter;
import retrofit2.Call;

public class NotificationDetailRequester extends AbstractRequester {

    @Setter
    private String notificationToken;

    public NotificationDetailRequester(Context context) {
        super(context);
    }

    @Override
    protected Call genApi() {
        Call call = networkManager.getApi().notificationDetail(notificationToken);

        return call;
    }
}
