package com.rightcode.wellcar.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.rightcode.wellcar.Adapter.RecyclerViewAdapter.CompanyImageRecyclerViewAdapter;
import com.rightcode.wellcar.Dialog.CarWashDialog;
import com.rightcode.wellcar.Dialog.CarWashTicketDialog;
import com.rightcode.wellcar.Dialog.PaymentMethodDialog;
import com.rightcode.wellcar.Fragment.TopFragment;
import com.rightcode.wellcar.MemberManager;
import com.rightcode.wellcar.R;
import com.rightcode.wellcar.RxJava.Event;
import com.rightcode.wellcar.RxJava.RxBus;
import com.rightcode.wellcar.RxJava.RxEvent.CarWashTicketEvent;
import com.rightcode.wellcar.RxJava.RxEvent.CarWashUseEvent;
import com.rightcode.wellcar.RxJava.RxEvent.EstimateSeletedEvent;
import com.rightcode.wellcar.RxJava.RxEvent.PaymentMethodSelectEvent;
import com.rightcode.wellcar.Util.DataEnums;
import com.rightcode.wellcar.Util.FragmentUtil;
import com.rightcode.wellcar.Util.ToastUtil;
import com.rightcode.wellcar.network.model.CommonResult;
import com.rightcode.wellcar.network.model.request.payment.PaymentEstimateBuyInfo;
import com.rightcode.wellcar.network.model.request.ticketHistory.TicketHistoryRegister;
import com.rightcode.wellcar.network.model.response.store.StoreDetail;
import com.rightcode.wellcar.network.requester.payment.PaymentEstimateBuyInfoRequester;
import com.rightcode.wellcar.network.requester.store.StoreDetailRequester;
import com.rightcode.wellcar.network.requester.ticketHistory.TicketHistoryRegisterRequester;
import com.rightcode.wellcar.network.responser.paymentEstimate.PaymentEstimateBuyInfoResponser;
import com.rightcode.wellcar.network.responser.store.StoreDetailResponser;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.rightcode.wellcar.Util.ExtraData.EXTRA_ACTIVITY_COMPLETE;
import static com.rightcode.wellcar.Util.ExtraData.EXTRA_COMPANY_DETAIL_TYPE;
import static com.rightcode.wellcar.Util.ExtraData.EXTRA_COMPANY_ID;
import static com.rightcode.wellcar.Util.ExtraData.EXTRA_ESTIMATE_ID;
import static com.rightcode.wellcar.Util.ExtraData.EXTRA_ESTIMATE_PRICE;
import static com.rightcode.wellcar.Util.ExtraData.EXTRA_MERCHANT_UID;
import static com.rightcode.wellcar.Util.ExtraData.EXTRA_PAYMENT_URL;

public class CompanyDetailActivity extends BaseActivity {


    @Event(CarWashUseEvent.class)
    public void onCarWashUseEvent(CarWashUseEvent event) {
        ticketHistoryRegister(event.getCode());
    }

    @Event(CarWashTicketEvent.class)
    public void onCarWashTicketEvent(CarWashTicketEvent event) {
        Intent intent = new Intent(CompanyDetailActivity.this, TicketBuyActivity.class);
        startActivity(intent);
    }

    @Event(PaymentMethodSelectEvent.class)
    public void onPaymentMethodSelectEvent(PaymentMethodSelectEvent event) {
        if (event.getType().equals("company"))
            paymentEstimateBuyInfo(event.getMethod());
    }


    @BindView(R.id.iv_thumbnail_image)
    ImageView iv_thumbnail_image;
    @BindView(R.id.tv_company_name)
    TextView tv_company_name;
    @BindView(R.id.tv_company_address)
    TextView tv_company_address;
    @BindView(R.id.tv_company_review_count)
    TextView tv_company_review_count;
    @BindView(R.id.tv_company_order_count)
    TextView tv_company_order_count;
    @BindView(R.id.iv_company_detail_image)
    ImageView iv_company_detail_image;
    @BindView(R.id.rv_company_images)
    RecyclerView rv_company_images;
    @BindView(R.id.tv_company_introduction)
    TextView tv_company_introduction;
    @BindView(R.id.tv_company_opens)
    TextView tv_company_opens;
    @BindView(R.id.tv_company_select)
    TextView tv_company_select;

    private TopFragment mTopFragment;
    private CompanyImageRecyclerViewAdapter mCompanyImageRecyclerViewAdapter;
    private Integer estimateId;
    private Integer storeId;
    private String estimatePrice;
    private String companyName;
    private DataEnums.CompanyDetailType detailType;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_detail);

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
                    RxBus.send(new EstimateSeletedEvent(estimateId));
                    setResult(RESULT_OK);
                    finishWithAnim();
                    break;
                }
                default:
                    break;
            }
        }
    }

    @OnClick({R.id.tv_company_select})
    void onClickMenu(View view) {
        switch (view.getId()) {
            case R.id.tv_company_select: {
                if (detailType.equals(DataEnums.CompanyDetailType.ESTIMATE)) {
                    PaymentMethodDialog paymentMethodDialog = new PaymentMethodDialog(CompanyDetailActivity.this, "company");
                    paymentMethodDialog.show();
                } else if (detailType.equals(DataEnums.CompanyDetailType.CLEAN)) {
                    if (MemberManager.getInstance(CompanyDetailActivity.this).getUserInfo().getCarWashTicket() > 0) {
                        CarWashDialog carWashDialog = new CarWashDialog(CompanyDetailActivity.this, companyName);
                        carWashDialog.show();
                    } else {
                        CarWashTicketDialog carWashTicketDialog = new CarWashTicketDialog(CompanyDetailActivity.this);
                        carWashTicketDialog.show();
                    }
                }
                break;
            }
        }
    }

    //----------------------------------------------------------------------------------------------
    // private
    //----------------------------------------------------------------------------------------------

    private void initialize() {
        mTopFragment = (TopFragment) FragmentUtil.findFragmentByTag(getSupportFragmentManager(), "TopFragment");
        mTopFragment.setText(TopFragment.Menu.CENTER, "업체 상세");
        mTopFragment.setImage(TopFragment.Menu.LEFT, R.drawable.arrow_left);
        mTopFragment.setImagePadding(TopFragment.Menu.LEFT, 5);
        mTopFragment.setListener(TopFragment.Menu.LEFT, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        LinearLayoutManager verticalLayoutManager = new LinearLayoutManager(CompanyDetailActivity.this, LinearLayoutManager.HORIZONTAL, false);
        rv_company_images.setLayoutManager(verticalLayoutManager);
        mCompanyImageRecyclerViewAdapter = new CompanyImageRecyclerViewAdapter(CompanyDetailActivity.this);
        rv_company_images.setAdapter(mCompanyImageRecyclerViewAdapter);

        if (getIntent() != null) {
            detailType = (DataEnums.CompanyDetailType) getIntent().getSerializableExtra(EXTRA_COMPANY_DETAIL_TYPE);
            storeId = getIntent().getIntExtra(EXTRA_COMPANY_ID, -1);
            estimateId = getIntent().getIntExtra(EXTRA_ESTIMATE_ID, -1);
            estimatePrice = getIntent().getStringExtra(EXTRA_ESTIMATE_PRICE);

            storeDetail(storeId);
        }
        if (detailType.equals(DataEnums.CompanyDetailType.BASIC)) {
            tv_company_select.setVisibility(View.GONE);
        } else {
            tv_company_select.setVisibility(View.VISIBLE);
        }
    }

    private void storeDetail(Integer storeId) {
        showLoading();
        StoreDetailRequester storeDetailRequester = new StoreDetailRequester(CompanyDetailActivity.this);
        storeDetailRequester.setStoreId(storeId);

        request(storeDetailRequester,
                success -> {
                    StoreDetailResponser result = (StoreDetailResponser) success;
                    if (result.getCode() == 200) {
                        initLayout(result.getData());
                    } else {
                        showServerErrorDialog(result.getResultMsg());
                    }
                    hideLoading();
                }, fail -> {
                    hideLoading();
                    showServerErrorDialog(fail.getResultMsg());
                });
    }

    private void initLayout(StoreDetail data) {
        Glide.with(CompanyDetailActivity.this)
                .load(data.getThumbnail() != null ? data.getThumbnail().getName() : "")
                .into(iv_thumbnail_image);

        companyName = data.getName();
        tv_company_name.setText(data.getName());
        tv_company_address.setText(data.getAddress() + data.getAddressDetail());
        tv_company_review_count.setText(data.getReviewCount().toString());
        tv_company_order_count.setText(data.getOrderCount().toString());

        Glide.with(CompanyDetailActivity.this)
                .load(data.getDetailImage() != null ? data.getDetailImage().getName() : "")
                .into(iv_company_detail_image);

        mCompanyImageRecyclerViewAdapter.setData(data.getImages());
        mCompanyImageRecyclerViewAdapter.notifyDataSetChanged();

        tv_company_introduction.setText(data.getIntroduction());
        tv_company_opens.setText(data.getOpens());
    }

    private void paymentEstimateBuyInfo(String payMethod) {
        PaymentEstimateBuyInfoRequester paymentEstimateBuyInfoRequester = new PaymentEstimateBuyInfoRequester(CompanyDetailActivity.this);
        PaymentEstimateBuyInfo param = new PaymentEstimateBuyInfo();
        param.setEstimateId(estimateId);
        param.setStoreId(storeId);
        param.setPayMethod(payMethod);

        paymentEstimateBuyInfoRequester.setParam(param);

        request(paymentEstimateBuyInfoRequester,
                success -> {
                    PaymentEstimateBuyInfoResponser result = (PaymentEstimateBuyInfoResponser) success;
                    if (result.getCode() == 200) {
                        String url = "http://15.165.107.159:3030/payment.html?merchantUid=" + result.getMerchantUid() +
                                "&payMethod=" + payMethod +
                                "&goodsname=wellCar" +
                                "&name=." +
                                "&phone=." +
                                "&email=." +
                                "&address=." +
                                "&price=" + estimatePrice;
                        Intent intent = new Intent(CompanyDetailActivity.this, PaymentActivity.class);
                        intent.putExtra(EXTRA_MERCHANT_UID, result.getMerchantUid());
                        intent.putExtra(EXTRA_PAYMENT_URL, url);
                        startActivityForResult(intent, EXTRA_ACTIVITY_COMPLETE);
                    } else {
                        showServerErrorDialog(result.getResultMsg());
                    }
                    hideLoading();
                }, fail -> {
                    hideLoading();
                    showServerErrorDialog(fail.getResultMsg());
                });
    }

    private void ticketHistoryRegister(String code) {
        showLoading();
        TicketHistoryRegisterRequester ticketHistoryRegisterRequester = new TicketHistoryRegisterRequester(CompanyDetailActivity.this);
        TicketHistoryRegister param = new TicketHistoryRegister();
        param.setCode(code);

        ticketHistoryRegisterRequester.setParam(param);
        ticketHistoryRegisterRequester.setStoreId(storeId);

        request(ticketHistoryRegisterRequester,
                success -> {
                    CommonResult result = (CommonResult) success;
                    if (result.getCode() == 200) {
                        ToastUtil.show(CompanyDetailActivity.this, "사용되었습니다");
                    } else {
                        showServerErrorDialog(result.getResultMsg());
                    }
                    hideLoading();
                }, fail -> {
                    hideLoading();
                    showServerErrorDialog(fail.getResultMsg());
                });


    }
}
