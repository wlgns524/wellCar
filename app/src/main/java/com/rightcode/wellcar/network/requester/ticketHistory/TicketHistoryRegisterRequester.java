package com.rightcode.wellcar.network.requester.ticketHistory;

import android.content.Context;

import com.rightcode.wellcar.Util.FileUtil;
import com.rightcode.wellcar.network.model.request.ticketHistory.TicketHistoryRegister;
import com.rightcode.wellcar.network.requester.AbstractRequester;

import java.io.File;
import java.util.ArrayList;

import lombok.Setter;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;

public class TicketHistoryRegisterRequester extends AbstractRequester {

    @Setter
    private Integer storeId;
    @Setter
    private TicketHistoryRegister param;

    public TicketHistoryRegisterRequester(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected Call genApi() {
        Call call = networkManager.getApi().ticketHistoryRegister(storeId, param);

        return call;
    }
}
