package com.rightcode.wellcar.network.requester.item;

import android.content.Context;

import com.rightcode.wellcar.network.requester.AbstractRequester;

import lombok.Setter;
import retrofit2.Call;

public class ItemsPriceRequester extends AbstractRequester {
    @Setter
    private Integer id;

    public ItemsPriceRequester(Context context) {
        super(context);
    }


    @Override
    protected Call genApi() throws Exception {
        Call call = networkManager.getApi().itemPrice(id);
        return call;
    }
}
