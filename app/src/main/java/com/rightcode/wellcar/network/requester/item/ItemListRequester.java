package com.rightcode.wellcar.network.requester.item;

import android.content.Context;

import com.rightcode.wellcar.Util.DataEnums;
import com.rightcode.wellcar.network.requester.AbstractRequester;

import lombok.Setter;
import retrofit2.Call;
import retrofit2.http.Query;

public class ItemListRequester extends AbstractRequester {

    @Setter
    private DataEnums.ItemDiffType diff;
    @Setter
    private Integer itemBrandId;
    @Setter
    private Integer storeId;

    public ItemListRequester(Context context) {
        super(context);
    }

    @Override
    protected Call genApi() {
        Call call = networkManager.getApi().itemList(diff.toString(), itemBrandId, storeId);

        return call;
    }
}
