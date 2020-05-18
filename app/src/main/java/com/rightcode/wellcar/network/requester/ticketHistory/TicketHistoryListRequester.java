package com.rightcode.wellcar.network.requester.ticketHistory;

import android.content.Context;

import com.rightcode.wellcar.network.model.request.ticketHistory.TicketHistoryRegister;
import com.rightcode.wellcar.network.requester.AbstractRequester;

import lombok.Setter;
import retrofit2.Call;

public class TicketHistoryListRequester extends AbstractRequester {

    public TicketHistoryListRequester(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected Call genApi() {
        Call call = networkManager.getApi().ticketHistoryManagement();

        return call;
    }
}
