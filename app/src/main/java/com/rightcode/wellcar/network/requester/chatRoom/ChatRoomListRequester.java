package com.rightcode.wellcar.network.requester.chatRoom;

import android.content.Context;

import com.rightcode.wellcar.network.requester.AbstractRequester;

import retrofit2.Call;

public class ChatRoomListRequester extends AbstractRequester {

    public ChatRoomListRequester(Context context) {
        super(context);
    }

    @Override
    protected Call genApi() {
        Call call = networkManager.getApi().chatRoomList();

        return call;
    }
}
