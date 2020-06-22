package com.rightcode.wellcar.Dialog;

import android.content.Context;
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
import com.rightcode.wellcar.network.model.response.item.ItemPrice;
import com.rightcode.wellcar.network.requester.item.ItemsPriceRequester;
import com.rightcode.wellcar.network.responser.item.ItemsPriceResponser;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EstimateInputDialog extends BaseDialog {

    @BindView(R.id.et_estimate_price)
    EditText et_estimate_price;
    @BindView(R.id.ll_film)
    LinearLayout llFilm;
    @BindView(R.id.tv_message)
    EditText tv_message;
    @BindView(R.id.tv_estimate_total_price)
    TextView tv_estimate_total_price;
    @BindView(R.id.tv_estimate_price)
    TextView tv_estimate_price;
    @BindView(R.id.et_package_film_minimum)
    EditText etPackageFilmMinimum;

    private Context mContext;
    private Integer id;
    private Integer itemId;
    private int filmPrice;
    private int totalPrice;
    private int editedFilmPrice;
    private int editedTotalPrice;
    private ItemPrice data;

    public EstimateInputDialog(Context context, Integer id, Integer itemId) {
        super(context);
        mContext = context;
        this.id = id;
        this.itemId = itemId;
        setContentView(R.layout.dialog_estimate_input);
        ButterKnife.bind(this);
        itemPrice();

    }

    @OnClick({R.id.tv_dialog_yes, R.id.tv_dialog_no})
    void onClickMenu(View view) {
        switch (view.getId()) {
            case R.id.tv_dialog_yes: {
                if (TextUtils.isEmpty(et_estimate_price.getText().toString())) {
                    ToastUtil.show(mContext, "견적을 입력해주세요");
                    break;
                }
                if(filmPrice != 0){
                    if(editedFilmPrice < filmPrice){
                        ToastUtil.show(mContext, "패키지 필름 가격이 최소 지정금액보다 적습니다.");
                        break;
                    }
                }
                RxBus.send(new EstimateUpdateEvent(id, totalPrice, tv_message.getText().toString()));
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
                data = result.getData();
                if (result.getData().getPrice() == null) {
                    llFilm.setVisibility(View.GONE);
                    tv_estimate_price.setText("견적 구성금액");
                    et_estimate_price.addTextChangedListener(new NumberTextWatcher(et_estimate_price, price -> {
                        totalPrice = price;
                        tv_estimate_total_price.setText(MoneyHelper.getKorUnit(totalPrice));
                    }));
                } else {
                    llFilm.setVisibility(View.VISIBLE);
                    filmPrice = result.getData().getPrice();
                    totalPrice = filmPrice;
                    editedFilmPrice = filmPrice;
                    tv_estimate_total_price.setText(MoneyHelper.getKor(totalPrice));
                    tv_estimate_price.setText("나머지 추가 구성금액");
                    etPackageFilmMinimum.setText(MoneyHelper.getKor(filmPrice));
                    etPackageFilmMinimum.addTextChangedListener(new NumberTextWatcher(etPackageFilmMinimum, price -> {
                        try {
                            editedFilmPrice = price;
                            totalPrice = editedTotalPrice + editedFilmPrice;
                            tv_estimate_total_price.setText(MoneyHelper.getKorUnit(totalPrice));
                            Log.d(editedFilmPrice + " / " + totalPrice);
                        } catch (Exception e){
                            e.printStackTrace();
                        }
                    }));
                    et_estimate_price.addTextChangedListener(new NumberTextWatcher(et_estimate_price, price -> {
                        try {
                            editedTotalPrice = price;
                            totalPrice = editedTotalPrice + editedFilmPrice;
                            tv_estimate_total_price.setText(MoneyHelper.getKorUnit(totalPrice));
                            Log.d(editedTotalPrice + " / " + totalPrice);
                        } catch (Exception e){
                            e.printStackTrace();
                        }
                    }));
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

    private void changeTotalPrice(){

    }
}
