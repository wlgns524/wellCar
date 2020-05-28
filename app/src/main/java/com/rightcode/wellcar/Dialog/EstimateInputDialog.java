package com.rightcode.wellcar.Dialog;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rightcode.wellcar.R;
import com.rightcode.wellcar.RxJava.RxBus;
import com.rightcode.wellcar.RxJava.RxEvent.EstimateUpdateEvent;
import com.rightcode.wellcar.Util.Log;
import com.rightcode.wellcar.Util.MoneyHelper;
import com.rightcode.wellcar.Util.NumberTextWatcher;
import com.rightcode.wellcar.Util.ToastUtil;
import com.rightcode.wellcar.network.requester.item.ItemsPriceRequester;
import com.rightcode.wellcar.network.responser.item.ItemsPriceResponser;

import java.text.DecimalFormat;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EstimateInputDialog extends BaseDialog {

    @BindView(R.id.et_estimate_price)
    EditText et_estimate_price;
    @BindView(R.id.tv_film)
    TextView tvFilm;
    @BindView(R.id.ll_film)
    LinearLayout llFilm;
    @BindView(R.id.tv_message)
    EditText tv_message;
    @BindView(R.id.tv_estimate_total_price)
    TextView tv_estimate_total_price;
    @BindView(R.id.tv_estimate_price)
    TextView tv_estimate_price;

    private Context mContext;
    private Integer id;
    private Integer itemId;
    private int filmPrice;

    public EstimateInputDialog(Context context, Integer id, Integer itemId) {
        super(context);
        mContext = context;
        this.id = id;
        this.itemId = itemId;
        setContentView(R.layout.dialog_estimate_input);
        ButterKnife.bind(this);
        itemPrice();

        et_estimate_price.addTextChangedListener(new NumberTextWatcher(et_estimate_price, tv_estimate_total_price, filmPrice));

    }

    @OnClick({R.id.tv_dialog_yes, R.id.tv_dialog_no})
    void onClickMenu(View view) {
        switch (view.getId()) {
            case R.id.tv_dialog_yes: {
                if (TextUtils.isEmpty(et_estimate_price.getText().toString())) {
                    ToastUtil.show(mContext, "견적을 입력해주세요");
                    break;
                }
                Number amount;
                try{
                    amount = MoneyHelper.dfKor.parse(tv_estimate_total_price.getText().toString());
                } catch (Exception e){
                    amount = 0;
                }



                RxBus.send(new EstimateUpdateEvent(id, amount.intValue(), tv_message.getText().toString()));
                dismiss();
                break;
            }
            case R.id.tv_dialog_no: {
                dismiss();
                break;
            }
        }
    }



    private void itemPrice() {
        ItemsPriceRequester itemsPriceRequester = new ItemsPriceRequester(mContext);

        itemsPriceRequester.setId(itemId);
        Log.d(itemId);
        request(itemsPriceRequester, success -> {
            ItemsPriceResponser result = (ItemsPriceResponser) success;
            if (result.getCode() == 200) {
                if (result.getData().getPrice() == null) {
                    llFilm.setVisibility(View.GONE);
                    tv_estimate_price.setText("견적 구성금액");
                } else {
                    llFilm.setVisibility(View.VISIBLE);
                    tv_estimate_price.setText("나머지 추가 구성금액");
                    filmPrice = result.getData().getPrice();
                    tvFilm.setText(MoneyHelper.getKor(filmPrice) + "원");
                }
            } else {
                ToastUtil.show(mContext, "데이터를 불러오지 못했습니다.");
                dismiss();
            }
        }, fail -> {
            ToastUtil.show(mContext, "데이터를 불러오지 못했습니다.");
            dismiss();
        });
    }
}
