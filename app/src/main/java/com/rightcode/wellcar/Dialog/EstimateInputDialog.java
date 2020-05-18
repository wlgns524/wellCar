package com.rightcode.wellcar.Dialog;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.rightcode.wellcar.R;
import com.rightcode.wellcar.RxJava.RxBus;
import com.rightcode.wellcar.RxJava.RxEvent.EstimateUpdateEvent;
import com.rightcode.wellcar.Util.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EstimateInputDialog extends BaseDialog {

    @BindView(R.id.et_estimate_price)
    EditText et_estimate_price;

    private Context mContext;
    private Integer id;

    public EstimateInputDialog(Context context, Integer id) {
        super(context);
        mContext = context;
        this.id = id;
        setContentView(R.layout.dialog_estimate_input);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.tv_dialog_yes, R.id.tv_dialog_no})
    void onClickMenu(View view) {
        switch (view.getId()) {
            case R.id.tv_dialog_yes: {
                if (TextUtils.isEmpty(et_estimate_price.getText().toString())) {
                    ToastUtil.show(mContext, "견적을 입력해주세요");
                    break;
                }
                RxBus.send(new EstimateUpdateEvent(id, Integer.parseInt(et_estimate_price.getText().toString())));
                dismiss();
                break;
            }
            case R.id.tv_dialog_no: {
                dismiss();
                break;
            }
        }
    }
}
