package com.rightcode.wellcar.Fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rightcode.wellcar.Adapter.RecyclerViewAdapter.CarWashManagementSettlementManagementRecyclerViewAdapter;
import com.rightcode.wellcar.Dialog.DatePickerDialog;
import com.rightcode.wellcar.R;
import com.rightcode.wellcar.Util.DateUtil;
import com.rightcode.wellcar.Util.MoneyHelper;
import com.rightcode.wellcar.Util.ToastUtil;
import com.rightcode.wellcar.network.model.response.accountCompany.AccountCompanyData;
import com.rightcode.wellcar.network.requester.accountCompany.SettlementListRequester;
import com.rightcode.wellcar.network.responser.accountCompany.SettlementListResponser;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CompanyManagementSettlementManagementFragment extends BaseFragment {

    @BindView(R.id.tv_all)
    TextView tv_all;
    @BindView(R.id.tv_exchange)
    TextView tv_exchange;
    @BindView(R.id.tv_payment)
    TextView tv_payment;
    @BindView(R.id.tv_start_date)
    TextView tv_start_date;
    @BindView(R.id.tv_end_date)
    TextView tv_end_date;
    @BindView(R.id.tv_sales_price)
    TextView tv_sales_price;
    @BindView(R.id.tv_exchange_price)
    TextView tv_exchange_price;
    @BindView(R.id.tv_balance)
    TextView tv_balance;
    @BindView(R.id.rv_settlement_management)
    RecyclerView rv_settlement_management;

    private View root;
    private String diff = "전체";
    private CarWashManagementSettlementManagementRecyclerViewAdapter mCarWashManagementSettlementManagementRecyclerViewAdapter;

    //------------------------------------------------------------------------------------------
    // contructor
    //------------------------------------------------------------------------------------------

    public static CompanyManagementSettlementManagementFragment newInstance() {
        CompanyManagementSettlementManagementFragment f = new CompanyManagementSettlementManagementFragment();
        Bundle b = new Bundle();
        f.setArguments(b);
        return f;
    }

    //----------------------------------------------------------------------------------------------
    // Life Cycle
    //----------------------------------------------------------------------------------------------

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_company_management_settlement_management, container, false);

        ButterKnife.bind(this, root);
        initialize();
        settlementList();

        return root;
    }

    @OnClick({R.id.tv_all, R.id.tv_exchange, R.id.tv_payment, R.id.iv_search, R.id.tv_start_date, R.id.tv_end_date})
    void onClickMenu(View view) {
        switch (view.getId()) {
            case R.id.tv_all: {
                tv_all.setBackground(getContext().getDrawable(R.drawable.app_color_border_app_color_background_corner_5));
                tv_all.setTextColor(getContext().getColor(R.color.white));
                tv_exchange.setBackground(getContext().getDrawable(R.drawable.space_border_white_corner_5));
                tv_exchange.setTextColor(getContext().getColor(R.color.text_gray));
                tv_payment.setBackground(getContext().getDrawable(R.drawable.space_border_white_corner_5));
                tv_payment.setTextColor(getContext().getColor(R.color.text_gray));
                diff = "전체";
                settlementList();
                break;
            }
            case R.id.tv_exchange: {
                tv_all.setBackground(getContext().getDrawable(R.drawable.space_border_white_corner_5));
                tv_all.setTextColor(getContext().getColor(R.color.text_gray));
                tv_exchange.setBackground(getContext().getDrawable(R.drawable.app_color_border_app_color_background_corner_5));
                tv_exchange.setTextColor(getContext().getColor(R.color.white));
                tv_payment.setBackground(getContext().getDrawable(R.drawable.space_border_white_corner_5));
                tv_payment.setTextColor(getContext().getColor(R.color.text_gray));
                diff = "환전";
                settlementList();
                break;
            }
            case R.id.tv_payment: {
                tv_all.setBackground(getContext().getDrawable(R.drawable.space_border_white_corner_5));
                tv_all.setTextColor(getContext().getColor(R.color.text_gray));
                tv_exchange.setBackground(getContext().getDrawable(R.drawable.space_border_white_corner_5));
                tv_exchange.setTextColor(getContext().getColor(R.color.text_gray));
                tv_payment.setBackground(getContext().getDrawable(R.drawable.app_color_border_app_color_background_corner_5));
                tv_payment.setTextColor(getContext().getColor(R.color.white));
                diff = "이용권";
                settlementList();
                break;
            }
            case R.id.iv_search: {
                settlementList();
                break;
            }

            case R.id.tv_start_date:{
                DatePickerDialog dialog = new DatePickerDialog(getContext(), ((yyyy, mm, dd) -> {
                    tv_start_date.setText(transformDate(yyyy, mm, dd));
                }));
                dialog.show();
                break;
            }

            case R.id.tv_end_date:{
                DatePickerDialog dialog = new DatePickerDialog(getContext(), ((yyyy, mm, dd) -> {
                    tv_end_date.setText(transformDate(yyyy, mm, dd));
                }));
                dialog.show();
                break;
            }
        }
    }

    //----------------------------------------------------------------------------------------------
    // private
    //----------------------------------------------------------------------------------------------

    private void initialize() {
        LinearLayoutManager verticalLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rv_settlement_management.setLayoutManager(verticalLayoutManager);
        mCarWashManagementSettlementManagementRecyclerViewAdapter = new CarWashManagementSettlementManagementRecyclerViewAdapter(getContext());
        rv_settlement_management.setAdapter(mCarWashManagementSettlementManagementRecyclerViewAdapter);
    }

    private void initLayout(AccountCompanyData data) {
        tv_sales_price.setText(MoneyHelper.getKorUnit(data.getSales()));
        tv_exchange_price.setText(MoneyHelper.getKorUnit(data.getExchange()));
        tv_balance.setText(MoneyHelper.getKorUnit(data.getSales() - data.getExchange()));
    }

    private void settlementList() {
        String startDate = TextUtils.isEmpty(tv_start_date.getText().toString()) ? DateUtil.getDate() : tv_start_date.getText().toString();
        String endDate = TextUtils.isEmpty(tv_end_date.getText().toString()) ? DateUtil.getDate() : tv_end_date.getText().toString();
        if (startDate.length() != 8) {
            ToastUtil.show(getContext(), "검색 시작일자 입력형식을 확인해주세요");
            return;
        }
        if (endDate.length() != 8) {
            ToastUtil.show(getContext(), "검색 종료일자 입력형식을 확인해주세요");
            return;
        }
        SettlementListRequester settlementListRequester = new SettlementListRequester(getContext());
        settlementListRequester.setStartDate(startDate);
        settlementListRequester.setEndDate(endDate);
        settlementListRequester.setDiff(diff);

        request(settlementListRequester,
                success -> {
                    SettlementListResponser result = (SettlementListResponser) success;
                    if (result.getCode() == 200) {
                        initLayout(result.getData());
                        mCarWashManagementSettlementManagementRecyclerViewAdapter.setData(result.getList());
                        mCarWashManagementSettlementManagementRecyclerViewAdapter.notifyDataSetChanged();
                    } else {
                        showServerErrorDialog(result.getResultMsg(), finish -> {
                            finishWithAnim();
                        });
                    }
                    hideLoading();
                }, fail -> {
                    hideLoading();
                    showServerErrorDialog(fail.getResultMsg(), finish -> {
                        finishWithAnim();
                    });
                });
    }

    @OnClick({R.id.tv_start_date, R.id.tv_end_date})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_start_date:
                break;
            case R.id.tv_end_date:
                break;
        }
    }
    protected String transformDate(int year, int month, int day) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);

        return sdf.format(calendar.getTime());
    }
}

