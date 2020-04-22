package com.rightcode.wellcar.network.requester.notice;

import android.content.Context;

import com.rightcode.wellcar.network.requester.AbstractRequester;

import lombok.Setter;
import retrofit2.Call;

public class NoticeDetailRequester extends AbstractRequester {

    @Setter
    private Integer id;

    public NoticeDetailRequester(Context context) {
        super(context);
    }

    @Override
    protected Call genApi() {
        Call call = networkManager.getApi().noticeDetail(id);

        return call;
    }
}
