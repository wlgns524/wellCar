package com.rightcode.wellcar.network.requester.event;

import android.content.Context;

import com.rightcode.wellcar.network.requester.AbstractRequester;

import lombok.Setter;
import retrofit2.Call;

public class EventDetailRequester extends AbstractRequester {

    @Setter
    private Integer id;

    public EventDetailRequester(Context context) {
        super(context);
    }

    @Override
    protected Call genApi() {
        Call call = networkManager.getApi().eventDetail(id);

        return call;
    }
}
