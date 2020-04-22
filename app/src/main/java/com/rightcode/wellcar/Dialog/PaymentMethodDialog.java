package com.rightcode.wellcar.Dialog;

import android.content.Context;
import android.view.View;
import android.widget.RadioButton;

import com.rightcode.wellcar.R;
import com.rightcode.wellcar.RxJava.RxBus;
import com.rightcode.wellcar.RxJava.RxEvent.PaymentMethodSelectEvent;
import com.rightcode.wellcar.Util.Log;
import com.rightcode.wellcar.Util.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import lombok.Setter;

public class PaymentMethodDialog extends BaseDialog {

    @BindView(R.id.rb_card)
    RadioButton rb_card;
    @BindView(R.id.rb_phone)
    RadioButton rb_phone;
    @BindView(R.id.rb_transport)
    RadioButton rb_transport;

    private Context mContext;
    // type - company
    // type - ticket
    private String type;
    @Setter
    private Integer count;
    @Setter
    private Integer price;

    public PaymentMethodDialog(Context context, String type) {
        super(context);
        setContentView(R.layout.dialog_payment_method);
        ButterKnife.bind(this);
        mContext = context;
        this.type = type;
    }

    @OnClick({R.id.btn_dialog_left, R.id.btn_dialog_right})
    void onClickMenu(View view) {
        switch (view.getId()) {
            case R.id.btn_dialog_left: {
                dismiss();
                break;
            }
            case R.id.btn_dialog_right: {
                if (!rb_card.isChecked() && !rb_phone.isChecked() && !rb_transport.isChecked()) {
                    ToastUtil.show(mContext, "결제수단을 선택해주세요");
                }
                String method = null;
                if (rb_card.isChecked())
                    method = "card";
                if (rb_phone.isChecked())
                    method = "phone";
                if (rb_transport.isChecked())
                    method = "trans";
                PaymentMethodSelectEvent paymentMethodSelectEvent = new PaymentMethodSelectEvent(method, type);
                Log.d(count);
                if (count != null)
                    paymentMethodSelectEvent.setCount(count);
                if (price != null)
                    paymentMethodSelectEvent.setPrice(price);
                RxBus.send(paymentMethodSelectEvent);
                dismiss();
                break;
            }
        }
    }
}
