package com.rightcode.wellcar.Activity.Estimate;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.rd.PageIndicatorView;
import com.rightcode.wellcar.Activity.BaseActivity;
import com.rightcode.wellcar.Adapter.RecyclerViewAdapter.CompanyRecyclerViewAdapter;
import com.rightcode.wellcar.Adapter.RecyclerViewAdapter.EstimateRecyclerViewAdapter;
import com.rightcode.wellcar.Adapter.ViewPagerAdapter.HomeBannerViewPagerAdapter;
import com.rightcode.wellcar.Component.CustomViewPager;
import com.rightcode.wellcar.Component.RecyclerViewOnClickListener;
import com.rightcode.wellcar.Fragment.TopFragment;
import com.rightcode.wellcar.MemberManager;
import com.rightcode.wellcar.R;
import com.rightcode.wellcar.Util.CommonUtil;
import com.rightcode.wellcar.Util.FragmentUtil;
import com.rightcode.wellcar.Util.Log;
import com.rightcode.wellcar.Util.ToastUtil;
import com.rightcode.wellcar.network.model.CommonResult;
import com.rightcode.wellcar.network.model.request.estimate.EstimateRegister;
import com.rightcode.wellcar.network.model.response.item.Item;
import com.rightcode.wellcar.network.model.response.store.Store;
import com.rightcode.wellcar.network.requester.estimate.EstimateRegisterRequester;
import com.rightcode.wellcar.network.requester.event.EventListRequester;
import com.rightcode.wellcar.network.requester.store.StoreListRequester;
import com.rightcode.wellcar.network.responser.event.EventListResponser;
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

public class EstimateDepth3Activity extends BaseActivity implements ViewPager.OnPageChangeListener {


    @BindView(R.id.tv_address_si)
    TextView tv_address_si;
    @BindView(R.id.tv_address_gu)
    TextView tv_address_gu;
    @BindView(R.id.fl_select_list)
    TagFlowLayout fl_select_list;
    @BindView(R.id.cv_event)
    CustomViewPager cv_event;
    @BindView(R.id.pageindicator)
    PageIndicatorView pageindicator;
    @BindView(R.id.rv_company)
    RecyclerView rv_company;

    private TopFragment mTopFragment;
    private TagAdapter adapter;
    private EstimateRegister register;
    private LayoutInflater mInflater;
    private HomeBannerViewPagerAdapter mHomeBannerViewPagerAdapter;
    private EstimateRecyclerViewAdapter mEstimateRecyclerViewAdapter;
    private ArrayList<Item> values = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estimate_depth3);

        ButterKnife.bind(this);
        initialize();
        eventList();
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

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        pageindicator.setSelected(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @OnClick({R.id.tv_estimate})
    void onClickMenu(View view) {
        switch (view.getId()) {
            case R.id.tv_estimate: {
                if (mEstimateRecyclerViewAdapter.getData() == null) {
                    ToastUtil.show(EstimateDepth3Activity.this, "옵션을 다시 선택해주세요");
                    return;
                }
                ArrayList<Integer> storeIds = new ArrayList<>();
                for (Store store : mEstimateRecyclerViewAdapter.getSelectItem()) {
                    storeIds.add(store.getId());
                }
                if (storeIds.size() < 1) {
                    ToastUtil.show(EstimateDepth3Activity.this, "업체를 선택해주세요");
                    return;
                }
                estimateRegist(storeIds);
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

        //event Adapter
        mHomeBannerViewPagerAdapter = new HomeBannerViewPagerAdapter(getSupportFragmentManager(), EstimateDepth3Activity.this);
        cv_event.setAdapter(mHomeBannerViewPagerAdapter);
        cv_event.addOnPageChangeListener(this);

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
        mEstimateRecyclerViewAdapter = new EstimateRecyclerViewAdapter(EstimateDepth3Activity.this);
        rv_company.setAdapter(mEstimateRecyclerViewAdapter);
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
                        mEstimateRecyclerViewAdapter.setData(result.getList());
                        mEstimateRecyclerViewAdapter.notifyDataSetChanged();
                    } else {
                        showServerErrorDialog(result.getResultMsg());
                    }
                    hideLoading();
                }, fail -> {
                    hideLoading();
                    showServerErrorDialog(fail.getResultMsg());
                });
    }

    private void estimateRegist(ArrayList<Integer> storeIds) {
        showLoading();

        EstimateRegisterRequester estimateRegisterRequester = new EstimateRegisterRequester(EstimateDepth3Activity.this);
        EstimateRegister param = new EstimateRegister();
        param.setGu(register.getGu());
        param.setSi(register.getSi());
        param.setItemIds(register.getItemIds());
        param.setStoreIds(storeIds);
        if(TextUtils.isEmpty(register.getRequest())){
            param.setRequest("");
        }else{
            param.setRequest(register.getRequest());
        }

        estimateRegisterRequester.setParam(param);

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

    private void eventList() {
        showLoading();
        EventListRequester eventListRequester = new EventListRequester(EstimateDepth3Activity.this);
        eventListRequester.setLatitude(MemberManager.getInstance(EstimateDepth3Activity.this).getLocation().getLatitude());
        eventListRequester.setLongitude(MemberManager.getInstance(EstimateDepth3Activity.this).getLocation().getLongitude());

        request(eventListRequester,
                success -> {
                    EventListResponser result = (EventListResponser) success;
                    if (result.getCode() == 200) {
                        pageindicator.setCount(result.getList().size());
                        mHomeBannerViewPagerAdapter.setData(result.getList());
                        mHomeBannerViewPagerAdapter.notifyDataSetChanged();
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