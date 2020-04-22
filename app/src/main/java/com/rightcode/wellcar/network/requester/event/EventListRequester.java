package com.rightcode.wellcar.network.requester.event;

import android.content.Context;

import com.rightcode.wellcar.network.requester.AbstractRequester;

import lombok.Setter;
import retrofit2.Call;

public class EventListRequester extends AbstractRequester {

    @Setter
    private Double latitude;
    @Setter
    private Double longitude;
    @Setter
    private String location;

    public EventListRequester(Context context) {
        super(context);
    }

    @Override
    protected Call genApi() {
        Call call = networkManager.getApi().eventList(latitude, longitude, location);

        return call;
    }
}
