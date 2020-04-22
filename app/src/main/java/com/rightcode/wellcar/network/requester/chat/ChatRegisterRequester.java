package com.rightcode.wellcar.network.requester.chat;

import android.content.Context;

import com.rightcode.wellcar.network.model.request.chat.ChatRegister;
import com.rightcode.wellcar.network.model.request.estimate.EstimateRegister;
import com.rightcode.wellcar.network.requester.AbstractRequester;

import lombok.Setter;
import retrofit2.Call;

public class ChatRegisterRequester extends AbstractRequester {

    @Setter
    private Integer chatRoomId;
    @Setter
    private ChatRegister param;

    public ChatRegisterRequester(Context context) {
        super(context);
    }

    @Override
    protected Call genApi() {
        Call call = networkManager.getApi().chatRegister(chatRoomId, param);

        return call;
    }
}
