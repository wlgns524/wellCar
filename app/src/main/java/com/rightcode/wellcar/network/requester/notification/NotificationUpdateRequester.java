package com.rightcode.wellcar.network.requester.notification;

import android.content.Context;

import com.rightcode.wellcar.network.requester.AbstractRequester;

import lombok.Setter;
import retrofit2.Call;

public class NotificationUpdateRequester extends AbstractRequester {

    @Setter
    private String notificationToken;
    @Setter
    private Boolean active;
    @Setter
    private Boolean chatActive;

    public NotificationUpdateRequester(Context context) {
        super(context);
    }

    @Override
    protected Call genApi() {
        Call call = networkManager.getApi().notificationUpdate(notificationToken, active, chatActive);

        return call;
    }
}
