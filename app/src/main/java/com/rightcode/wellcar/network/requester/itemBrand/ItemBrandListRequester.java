package com.rightcode.wellcar.network.requester.itemBrand;

import android.content.Context;

import com.rightcode.wellcar.Util.DataEnums;
import com.rightcode.wellcar.Util.Log;
import com.rightcode.wellcar.network.requester.AbstractRequester;

import lombok.Setter;
import retrofit2.Call;

public class ItemBrandListRequester extends AbstractRequester {

    @Setter
    private DataEnums.ItemDiffType diff;
    @Setter
    private Boolean random;
    @Setter
    private Integer storeId;

    public ItemBrandListRequester(Context context) {
        super(context);
    }

    @Override
    protected Call genApi() {
        Call call = null;
        if (diff != null) {
            call = networkManager.getApi().itemBrandList(diff.toString(), random, storeId);
        } else {
            call = networkManager.getApi().itemBrandList(null, random, storeId);
        }
        return call;
    }
}
