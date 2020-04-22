package com.rightcode.wellcar.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rightcode.wellcar.Adapter.RecyclerViewAdapter.TicketBuyRecyclerViewAdapter;
import com.rightcode.wellcar.Fragment.TopFragment;
import com.rightcode.wellcar.MemberManager;
import com.rightcode.wellcar.R;
import com.rightcode.wellcar.RxJava.Event;
import com.rightcode.wellcar.RxJava.RxBus;
import com.rightcode.wellcar.RxJava.RxEvent.EstimateSeletedEvent;
import com.rightcode.wellcar.RxJava.RxEvent.PaymentMethodSelectEvent;
import com.rightcode.wellcar.Util.FragmentUtil;
import com.rightcode.wellcar.Util.Log;
import com.rightcode.wellcar.network.model.request.payment.PaymentTicketBuyInfo;
import com.rightcode.wellcar.network.requester.paymentTicket.PaymentTicketBuyInfoRequester;
import com.rightcode.wellcar.network.requester.user.UserInfoRequester;
import com.rightcode.wellcar.network.responser.paymentEstimate.PaymentEstimateBuyInfoResponser;
import com.rightcode.wellcar.network.responser.user.UserInfoResponser;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.rightcode.wellcar.Util.ExtraData.EXTRA_ACTIVITY_COMPLETE;
import static com.rightcode.wellcar.Util.ExtraData.EXTRA_MERCHANT_UID;
import static com.rightcode.wellcar.Util.ExtraData.EXTRA_PAYMENT_URL;

public class TicketBuyActivity extends BaseActivity {


    @Event(PaymentMethodSelectEvent.class)
    public void onPaymentMethodSelectEvent(PaymentMethodSelectEvent event) {
        Log.d();
        if (event.getType().equals("ticket")) {
            PaymentTicketBuyInfo param = new PaymentTicketBuyInfo();
            param.setPayMethod(event.getMethod());
            param.setPrice(event.getPrice());
            param.setTicket(event.getCount());
            paymentTicketBuyInfo(param);
        }
    }

    @BindView(R.id.rv_ticket_buy)
    RecyclerView rv_ticket_buy;

    private TopFragment mTopFragment;
    private TicketBuyRecyclerViewAdapter mTicketBuyRecyclerViewAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_buy);

        RxBus.register(this);
        ButterKnife.bind(this);
        initialize();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.unregister(this);
    }

    //------------------------------------------------------------------------------------------
    // Override
    //------------------------------------------------------------------------------------------

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case EXTRA_ACTIVITY_COMPLETE: {
//                    RxBus.send(new EstimateSeletedEvent(estimateId));
                    userInfo();
                    break;
                }
                default:
                    break;
            }
        }
    }

    //------------------------------------------------------------------------------------------
    // private
    //------------------------------------------------------------------------------------------
    private void initialize() {
        mTopFragment = (TopFragment) FragmentUtil.findFragmentByTag(getSupportFragmentManager(), "TopFragment");
        mTopFragment.setText(TopFragment.Menu.CENTER, "이용권 구매");
        mTopFragment.setImagePadding(TopFragment.Menu.CENTER, 10);
        mTopFragment.setImage(TopFragment.Menu.LEFT, R.drawable.arrow_left);
        mTopFragment.setImagePadding(TopFragment.Menu.LEFT, 5);
        mTopFragment.setListener(TopFragment.Menu.LEFT, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishWithAnim();
            }
        });

        LinearLayoutManager verticalLayoutManager = new LinearLayoutManager(TicketBuyActivity.this, LinearLayoutManager.VERTICAL, false);
        rv_ticket_buy.setLayoutManager(verticalLayoutManager);
        mTicketBuyRecyclerViewAdapter = new TicketBuyRecyclerViewAdapter(TicketBuyActivity.this);
        rv_ticket_buy.setAdapter(mTicketBuyRecyclerViewAdapter);
    }

    private void paymentTicketBuyInfo(PaymentTicketBuyInfo param) {
        Log.d(param.getPayMethod());
        Log.d(param.getPrice());
        Log.d(param.getTicket());
        PaymentTicketBuyInfoRequester paymentTicketBuyInfoRequester = new PaymentTicketBuyInfoRequester(TicketBuyActivity.this);
        paymentTicketBuyInfoRequester.setParam(param);

        request(paymentTicketBuyInfoRequester,
                success -> {
                    PaymentEstimateBuyInfoResponser result = (PaymentEstimateBuyInfoResponser) success;
                    if (result.getCode() == 200) {
                        String url = "http://15.165.107.159:3030/payment.html?merchantUid=" + result.getMerchantUid() +
                                "&payMethod=" + param.getPayMethod() +
                                "&goodsname=wellCar 세차이용권" + param.getTicket() + "개" +
                                "&name=." +
                                "&phone=." +
                                "&email=." +
                                "&address=." +
                                "&price=" + param.getPrice();
                        Intent intent = new Intent(TicketBuyActivity.this, PaymentActivity.class);
                        intent.putExtra(EXTRA_MERCHANT_UID, result.getMerchantUid());
                        intent.putExtra(EXTRA_PAYMENT_URL, url);
                        startActivityForResult(intent, EXTRA_ACTIVITY_COMPLETE);
                    }
                }, fail -> {
                    hideLoading();
                    showServerErrorDialog(fail.getResultMsg());
                });
    }

    private void userInfo() {
        UserInfoRequester userInfoRequester = new UserInfoRequester(TicketBuyActivity.this);

        request(userInfoRequester,
                success -> {
                    UserInfoResponser result = (UserInfoResponser) success;
                    if (result.getCode() == 200) {
                        MemberManager.getInstance(TicketBuyActivity.this).updateLogInInfo(result.getUser());
                    } else {
                        MemberManager.getInstance(TicketBuyActivity.this).userLogout();
                    }
                    setResult(RESULT_OK);
                    finishWithAnim();
                }, fail -> {

                });
    }
}
