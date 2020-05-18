package com.rightcode.wellcar.Dialog;

import android.content.Context;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.rightcode.wellcar.R;
import com.rightcode.wellcar.RxJava.RxBus;
import com.rightcode.wellcar.RxJava.RxEvent.CarWashUseEvent;
import com.rightcode.wellcar.Util.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CarWashDialog extends BaseDialog {

    @BindView(R.id.tv_company_name)
    TextView tv_company_name;
    @BindView(R.id.et_car_wash_coupon_number)
    EditText et_car_wash_coupon_number;
    @BindView(R.id.tv_car_wash)
    TextView tv_car_wash;

    private Context mContext;

    public CarWashDialog(Context context, String companyName) {
        super(context);
        setContentView(R.layout.dialog_car_wash);
        mContext = context;

        ButterKnife.bind(this);
        initialize();
        tv_company_name.setText(companyName);
    }


    @OnClick({R.id.rl_car_wash_use})
    void onClickMenu(View view) {
        switch (view.getId()) {
            case R.id.rl_car_wash_use: {
                if (TextUtils.isEmpty(et_car_wash_coupon_number.getText().toString())) {
                    ToastUtil.show(mContext, "고유번호를 입력해주세요");
                    break;
                }
                RxBus.send(new CarWashUseEvent(et_car_wash_coupon_number.getText().toString()));
                dismiss();
                break;
            }
        }
    }


    private void initialize(){
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            Typeface typeface = mContext.getResources().getFont(R.font.jalnan_otf);
            tv_car_wash.setTypeface(typeface);
        }
    }
}
