package com.rightcode.wellcar.network.requester.notice;

import android.content.Context;

import com.rightcode.wellcar.Util.DataEnums;
import com.rightcode.wellcar.network.requester.AbstractRequester;

import lombok.Setter;
import retrofit2.Call;

public class NoticeListRequester extends AbstractRequester {

    public NoticeListRequester(Context context) {
        super(context);
    }

    @Override
    protected Call genApi() {
        Call call = networkManager.getApi().noticeList();

        return call;
    }
}
