package com.rightcode.wellcar.network.requester.car;

import android.content.Context;

import com.rightcode.wellcar.network.requester.AbstractRequester;

import lombok.Setter;
import retrofit2.Call;

public class CarListRequester extends AbstractRequester {

    @Setter
    private Integer brandId;
    @Setter
    private String name;

    public CarListRequester(Context context) {
        super(context);
    }

    @Override
    protected Call genApi() {
        Call call = networkManager.getApi().carList(brandId, name);

        return call;
    }
}
