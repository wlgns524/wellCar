package com.rightcode.wellcar.network.requester.chatRoom;

import android.content.Context;

import com.rightcode.wellcar.network.requester.AbstractRequester;

import lombok.Setter;
import retrofit2.Call;

public class ChatRoomRemoveRequester extends AbstractRequester {

    @Setter
    private Integer id;

    public ChatRoomRemoveRequester(Context context) {
        super(context);
    }

    @Override
    protected Call genApi() {
        Call call = networkManager.getApi().chatRoomRemove(id);

        return call;
    }
}
