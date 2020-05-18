
package com.rightcode.wellcar.Activity.Setting;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rightcode.wellcar.Activity.BaseActivity;
import com.rightcode.wellcar.Adapter.RecyclerViewAdapter.CarWashCompanyRecyclerViewAdapter;
import com.rightcode.wellcar.Dialog.CommonDialog;
import com.rightcode.wellcar.Fragment.TopFragment;
import com.rightcode.wellcar.R;
import com.rightcode.wellcar.Util.FragmentUtil;
import com.rightcode.wellcar.network.requester.store.StoreListRequester;
import com.rightcode.wellcar.network.responser.store.StoreListResponser;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.rightcode.wellcar.Util.ExtraData.EXTRA_ACTIVITY_COMPLETE;
import static com.rightcode.wellcar.Util.ExtraData.EXTRA_ADDRESS_GU;
import static com.rightcode.wellcar.Util.ExtraData.EXTRA_ADDRESS_SI;

public class CarWashStoreListActivity extends BaseActivity {

    @BindView(R.id.tv_address_si)
    TextView tv_address_si;
    @BindView(R.id.tv_address_gu)
    TextView tv_address_gu;
    @BindView(R.id.rv_car_wash_company)
    RecyclerView rv_car_wash_company;

    private TopFragment mTopFragment;
    private CarWashCompanyRecyclerViewAdapter mCarWashCompanyRecyclerViewAdapter;
    private String addressSi;
    private String addressGu;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_wash_store_list);

        ButterKnife.bind(this);
        initialize();
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
                    setResult(RESULT_OK);
                    finishWithAnim();
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
        mTopFragment.setText(TopFragment.Menu.CENTER, "셀프 세차장");
        mTopFragment.setImagePadding(TopFragment.Menu.CENTER, 10);
        mTopFragment.setImage(TopFragment.Menu.LEFT, R.drawable.arrow_left);
        mTopFragment.setImagePadding(TopFragment.Menu.LEFT, 5);
        mTopFragment.setListener(TopFragment.Menu.LEFT, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishWithAnim();
            }
        });
        if (getIntent() != null) {
            addressSi = getIntent().getStringExtra(EXTRA_ADDRESS_SI);
            addressGu = getIntent().getStringExtra(EXTRA_ADDRESS_GU);
            initLayout();
            storeList();
        }

        LinearLayoutManager verticalLayoutManager = new LinearLayoutManager(CarWashStoreListActivity.this, LinearLayoutManager.VERTICAL, false);
        rv_car_wash_company.setLayoutManager(verticalLayoutManager);
        mCarWashCompanyRecyclerViewAdapter = new CarWashCompanyRecyclerViewAdapter(CarWashStoreListActivity.this);
        rv_car_wash_company.setAdapter(mCarWashCompanyRecyclerViewAdapter);
    }

    private void initLayout() {
        tv_address_si.setText(addressSi);
        tv_address_gu.setText(addressGu);
    }

    private void storeList() {
        showLoading();
        String[] diff = new String[1];
        diff[0] = "셀프세차";
        StoreListRequester storeListRequester = new StoreListRequester(CarWashStoreListActivity.this);
        storeListRequester.setDiff(diff);
        storeListRequester.setSi(addressSi);
        storeListRequester.setGu(addressGu);

        request(storeListRequester,
                success -> {
                    StoreListResponser result = (StoreListResponser) success;
                    if (result.getCode() == 200) {
                        if (result.getList().size() > 0) {
                            mCarWashCompanyRecyclerViewAdapter.setData(result.getList());
                            mCarWashCompanyRecyclerViewAdapter.notifyDataSetChanged();
                        } else {
                            CommonDialog commonDialog = new CommonDialog(CarWashStoreListActivity.this);
                            commonDialog.setMessage("검색된 구역에 등록된 세차장이 없습니다.");
                            commonDialog.setPositiveButton("확인", ok -> {
                                finishWithAnim();
                            });
                            commonDialog.show();
                        }
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