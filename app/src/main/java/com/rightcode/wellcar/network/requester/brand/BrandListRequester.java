package com.rightcode.wellcar.network.requester.brand;

import android.content.Context;

import com.rightcode.wellcar.Util.DataEnums;
import com.rightcode.wellcar.Util.Log;
import com.rightcode.wellcar.network.requester.AbstractRequester;

import lombok.Setter;
import retrofit2.Call;

public class BrandListRequester extends AbstractRequester {

    @Setter
    private DataEnums.BrandType origin;

    public BrandListRequester(Context context) {
        super(context);
    }

    @Override
    protected Call genApi() {
        Call call = networkManager.getApi().brandList(origin.toString() == null ? null : origin.toString());

        return call;
    }
}
