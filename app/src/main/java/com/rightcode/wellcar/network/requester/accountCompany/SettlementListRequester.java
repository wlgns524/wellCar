package com.rightcode.wellcar.network.requester.accountCompany;

import android.content.Context;

import com.rightcode.wellcar.Util.DataEnums;
import com.rightcode.wellcar.Util.Log;
import com.rightcode.wellcar.network.requester.AbstractRequester;

import lombok.Setter;
import retrofit2.Call;

public class SettlementListRequester extends AbstractRequester {

    private String startDate;
    private String endDate;
    @Setter
    private String diff;

    public SettlementListRequester(Context context) {
        super(context);
    }

    public void setStartDate(String startDate) {
        this.startDate = String.format("%s-%s-%s", startDate.substring(0, 4), startDate.substring(4, 6), startDate.substring(6, 8));
    }

    public void setEndDate(String endDate) {
        this.endDate = String.format("%s-%s-%s", endDate.substring(0, 4), endDate.substring(4, 6), endDate.substring(6, 8));
    }

    @Override
    protected Call genApi() {
        Call call = networkManager.getApi().carWashSettlementList(startDate, endDate, diff);

        return call;
    }
}
