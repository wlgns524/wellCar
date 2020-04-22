package com.rightcode.wellcar.Dialog;

import android.content.Context;
import android.view.View;

import com.rightcode.wellcar.R;
import com.rightcode.wellcar.RxJava.RxBus;
import com.rightcode.wellcar.RxJava.RxEvent.CarWashTicketEvent;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class CarWashTicketDialog extends BaseDialog {

    public CarWashTicketDialog(Context context) {
        super(context);
        setContentView(R.layout.dialog_car_wash_ticket);

        ButterKnife.bind(this);
    }

    @OnClick({R.id.tv_car_wash_ticket_no, R.id.tv_car_wash_ticket_yes})
    void onClickMenu(View view) {
        switch (view.getId()) {
            case R.id.tv_car_wash_ticket_yes: {
                RxBus.send(new CarWashTicketEvent());
                dismiss();
                break;
            }
            case R.id.tv_car_wash_ticket_no: {
                dismiss();
                break;
            }
        }
    }
}
