package com.rightcode.wellcar.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rightcode.wellcar.Activity.TicketBuyActivity;
import com.rightcode.wellcar.Adapter.RecyclerViewAdapter.BuyTicketRecyclerViewAdapter;
import com.rightcode.wellcar.MemberManager;
import com.rightcode.wellcar.R;
import com.rightcode.wellcar.Util.DateUtil;
import com.rightcode.wellcar.network.requester.paymentTicket.PaymentTicketListRequester;
import com.rightcode.wellcar.network.responser.paymentTicket.PaymentTicketListResponser;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.app.Activity.RESULT_OK;
import static com.rightcode.wellcar.Util.ExtraData.EXTRA_ACTIVITY_ACTION;

public class BuyTicketFragment extends BaseFragment {

    @BindView(R.id.tv_month)
    TextView tv_month;
    @BindView(R.id.tv_ticket_count)
    TextView tv_ticket_count;
    @BindView(R.id.rv_ticket_use)
    RecyclerView rv_ticket_use;

    private BuyTicketRecyclerViewAdapter mBuyTicketRecyclerViewAdapter;
    private View root;
    private Integer currentYear = DateUtil.getYearToInt();
    private Integer currentMonth = DateUtil.getMonthToInt();

    //------------------------------------------------------------------------------------------
    // contructor
    //------------------------------------------------------------------------------------------

    public static BuyTicketFragment newInstance() {
        BuyTicketFragment f = new BuyTicketFragment();
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
        root = inflater.inflate(R.layout.fragment_buy_ticket, container, false);

        ButterKnife.bind(this, root);
        initialize();
        paymentTicketList();

        return root;
    }


    //------------------------------------------------------------------------------------------
    // Override
    //------------------------------------------------------------------------------------------

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case EXTRA_ACTIVITY_ACTION: {

                }
            }
        }
    }

    @OnClick({R.id.ll_last_month, R.id.ll_next_month, R.id.tv_ticket_buy})
    void onClickMenu(View view) {
        switch (view.getId()) {
            case R.id.ll_last_month: {
                if (currentMonth == 1) {
                    currentMonth = 12;
                    currentYear -= 1;
                } else {
                    currentMonth--;
                }
                paymentTicketList();
                break;
            }
            case R.id.ll_next_month: {
                if (currentMonth == 12) {
                    currentMonth = 1;
                    currentYear += 1;
                } else {
                    currentMonth++;
                }
                paymentTicketList();
                break;
            }
            case R.id.tv_ticket_buy: {
                Intent intent = new Intent(getContext(), TicketBuyActivity.class);
                startActivityForResult(intent, EXTRA_ACTIVITY_ACTION);
                break;
            }
        }
    }

    //----------------------------------------------------------------------------------------------
    // private
    //----------------------------------------------------------------------------------------------

    private void initialize() {
        LinearLayoutManager verticalLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rv_ticket_use.setLayoutManager(verticalLayoutManager);
        mBuyTicketRecyclerViewAdapter = new BuyTicketRecyclerViewAdapter(getContext());
        rv_ticket_use.setAdapter(mBuyTicketRecyclerViewAdapter);

        tv_ticket_count.setText(MemberManager.getInstance(getContext()).getUserInfo().getCarWashTicket().toString());
    }

    private void paymentTicketList() {
//        tv_month.setText(String.format("%04d년 %02d월", currentYear, currentMonth));
        tv_month.setText(String.format("%02d월", currentMonth));
        showLoading();
        PaymentTicketListRequester paymentTicketListRequester = new PaymentTicketListRequester(getContext());
        paymentTicketListRequester.setYear(currentYear);
        paymentTicketListRequester.setMonth(currentMonth);

        request(paymentTicketListRequester,
                success -> {
                    PaymentTicketListResponser result = (PaymentTicketListResponser) success;
                    if (result.getCode() == 200) {
                        mBuyTicketRecyclerViewAdapter.setData(result.getList());
                        mBuyTicketRecyclerViewAdapter.notifyDataSetChanged();
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

