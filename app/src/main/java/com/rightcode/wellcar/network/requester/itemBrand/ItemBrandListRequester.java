package com.rightcode.wellcar.network.requester.itemBrand;

import android.content.Context;

import com.rightcode.wellcar.Util.DataEnums;
import com.rightcode.wellcar.network.requester.AbstractRequester;

import lombok.Setter;
import retrofit2.Call;

public class ItemBrandListRequester extends AbstractRequester {

    @Setter
    private DataEnums.ItemDiffType diff;

    public ItemBrandListRequester(Context context) {
        super(context);
    }

    @Override
    protected Call genApi() {
        Call call = networkManager.getApi().itemBrandList(diff.toString());

        return call;
    }
}
