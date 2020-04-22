package com.rightcode.wellcar.Activity.Estimate;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rightcode.wellcar.Activity.BaseActivity;
import com.rightcode.wellcar.Adapter.RecyclerViewAdapter.CompanyRecyclerViewAdapter;
import com.rightcode.wellcar.Component.RecyclerViewOnClickListener;
import com.rightcode.wellcar.Fragment.TopFragment;
import com.rightcode.wellcar.R;
import com.rightcode.wellcar.Util.FragmentUtil;
import com.rightcode.wellcar.Util.Log;
import com.rightcode.wellcar.Util.ToastUtil;
import com.rightcode.wellcar.network.model.CommonResult;
import com.rightcode.wellcar.network.model.request.estimate.EstimateRegister;
import com.rightcode.wellcar.network.model.response.item.Item;
import com.rightcode.wellcar.network.model.response.store.Store;
import com.rightcode.wellcar.network.requester.estimate.EstimateRegisterRequester;
import com.rightcode.wellcar.network.requester.store.StoreListRequester;
import com.rightcode.wellcar.network.responser.store.StoreListResponser;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.rightcode.wellcar.Util.ExtraData.EXTRA_ACTIVITY_COMPLETE;
import static com.rightcode.wellcar.Util.ExtraData.EXTRA_ESTIMATE_REGISTER;
import static com.rightcode.wellcar.Util.ExtraData.EXTRA_ITEMS;

public class EstimateDepth3Activity extends BaseActivity {


    @BindView(R.id.tv_address_si)
    TextView tv_address_si;
    @BindView(R.id.tv_address_gu)
    TextView tv_address_gu;
    @BindView(R.id.fl_select_list)
    TagFlowLayout fl_select_list;
    @BindView(R.id.rv_company)
    RecyclerView rv_company;

    private TopFragment mTopFragment;
    private TagAdapter adapter;
    private EstimateRegister register;
    private LayoutInflater mInflater;
    private CompanyRecyclerViewAdapter mCompanyRecyclerViewAdapter;
    private ArrayList<Item> values = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estimate_depth3);

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

    @OnClick({R.id.tv_estimate})
    void onClickMenu(View view){
        switch (view.getId()){
            case R.id.tv_estimate:{
                estimateRegist();
                break;
            }
        }
    }


    private void initialize() {
        if (getIntent() != null) {
            register = (EstimateRegister) getIntent().getSerializableExtra(EXTRA_ESTIMATE_REGISTER);
            values = (ArrayList<Item>) getIntent().getSerializableExtra(EXTRA_ITEMS);
            tv_address_si.setText(register.getSi());
            tv_address_gu.setText(register.getGu());
            storeList();
        }
        mTopFragment = (TopFragment) FragmentUtil.findFragmentByTag(getSupportFragmentManager(), "TopFragment");
        mTopFragment.setText(TopFragment.Menu.CENTER, "시공견적");
        mTopFragment.setImagePadding(TopFragment.Menu.CENTER, 10);
        mTopFragment.setImage(TopFragment.Menu.LEFT, R.drawable.arrow_left);
        mTopFragment.setImagePadding(TopFragment.Menu.LEFT, 5);
        mTopFragment.setListener(TopFragment.Menu.LEFT, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishWithAnim();
            }
        });

        // Flow Layout
        mInflater = LayoutInflater.from(EstimateDepth3Activity.this);
        // 데이터 추가
        String[] mVals = new String[values.size()];
        int size = 0;
        for (Item item : values) {
            mVals[size++] = String.format("%s(%s)", item.getName(), item.getItemBrand().getName());
        }
        adapter = new TagAdapter<String>(mVals) {
            @Override
            public View getView(FlowLayout parent, int position, String s) {
                AppCompatTextView tv = (AppCompatTextView) mInflater.inflate(R.layout.item_category, fl_select_list, false);
                tv.setText(s);
                return tv;
            }
        };
        fl_select_list.setAdapter(adapter);


        LinearLayoutManager verticalLayoutManager = new LinearLayoutManager(EstimateDepth3Activity.this, LinearLayoutManager.VERTICAL, false);
        rv_company.setLayoutManager(verticalLayoutManager);
        mCompanyRecyclerViewAdapter = new CompanyRecyclerViewAdapter(EstimateDepth3Activity.this);
        rv_company.setAdapter(mCompanyRecyclerViewAdapter);
//        rv_company.addOnItemTouchListener(new RecyclerViewOnClickListener(EstimateDepth3Activity.this, new RecyclerViewOnClickListener.OnItemClickListener() {
//            @Override
//            public void onItemClick(View view, int position) {
//                hideKeyboard(view);
//                mCompanyRecyclerViewAdapter.setSelect(position);
//            }
//        }));
    }

    private void storeList() {
        showLoading();
        StoreListRequester storeListRequester = new StoreListRequester(EstimateDepth3Activity.this);
        storeListRequester.setSi(register.getSi());
        storeListRequester.setGu(register.getGu());
        storeListRequester.setItemId(register.getItemIds());

        request(storeListRequester,
                success -> {
                    StoreListResponser result = (StoreListResponser) success;
                    if (result.getCode() == 200) {
                        mCompanyRecyclerViewAdapter.setData(result.getList());
                        mCompanyRecyclerViewAdapter.notifyDataSetChanged();
                    } else {
                        showServerErrorDialog(result.getResultMsg());
                    }
                    hideLoading();
                }, fail -> {
                    hideLoading();
                    showServerErrorDialog(fail.getResultMsg());
                });
    }

    private void estimateRegist() {
        EstimateRegisterRequester estimateRegisterRequester = new EstimateRegisterRequester(EstimateDepth3Activity.this);
        EstimateRegister param = new EstimateRegister();
        param.setGu(register.getGu());
        param.setSi(register.getSi());
        param.setItemIds(register.getItemIds());
        ArrayList<Integer> storeIds = new ArrayList<>();
        for (Store store : mCompanyRecyclerViewAdapter.getSelectItem()) {
            storeIds.add(store.getId());
        }
        if (storeIds.size() < 1) {
            ToastUtil.show(EstimateDepth3Activity.this, "업체를 선택해주세요");
            return;
        }
        param.setStoreIds(storeIds);
        estimateRegisterRequester.setParam(param);
        showLoading();
        request(estimateRegisterRequester,
                success -> {
                    CommonResult result = (CommonResult) success;
                    if (result.getCode() == 200) {
                        Intent intent = new Intent(EstimateDepth3Activity.this, EstimateCompleteActivity.class);
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
}