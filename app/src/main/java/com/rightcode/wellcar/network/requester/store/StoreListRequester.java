package com.rightcode.wellcar.network.requester.store;

import android.content.Context;

import com.rightcode.wellcar.network.requester.AbstractRequester;

import java.util.ArrayList;

import lombok.Setter;
import retrofit2.Call;

public class StoreListRequester extends AbstractRequester {

    @Setter
    private Double latitude;
    @Setter
    private Double longitude;
    @Setter
    private String[] diff;
    @Setter
    private String si;
    @Setter
    private String gu;
    @Setter
    private ArrayList<Integer> itemId;
    @Setter
    private Integer itemBrandId;


    public StoreListRequester(Context context) {
        super(context);
    }

    @Override
    protected Call genApi() {
        Call call = networkManager.getApi().storeList(latitude, longitude, si, gu, itemId, itemBrandId, diff);

        return call;
    }
}
