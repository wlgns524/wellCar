package com.rightcode.wellcar.Activity;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rightcode.wellcar.Adapter.RecyclerViewAdapter.CompanyRecyclerViewAdapter;
import com.rightcode.wellcar.Adapter.RecyclerViewAdapter.CompanySelectRecyclerViewAdapter;
import com.rightcode.wellcar.Fragment.TopFragment;
import com.rightcode.wellcar.MemberManager;
import com.rightcode.wellcar.R;
import com.rightcode.wellcar.Util.FragmentUtil;
import com.rightcode.wellcar.network.requester.store.StoreListRequester;
import com.rightcode.wellcar.network.responser.store.StoreListResponser;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.rightcode.wellcar.Util.ExtraData.EXTRA_BRAND_ID;

public class CompanySelectActivity extends BaseActivity {

    @BindView(R.id.rv_company)
    RecyclerView rv_company;

    private TopFragment mTopFragment;
    private CompanySelectRecyclerViewAdapter mCompanySelectRecyclerViewAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_select);

        ButterKnife.bind(this);
        initialize();
    }

    //------------------------------------------------------------------------------------------
    // private
    //------------------------------------------------------------------------------------------

    private void initialize() {
        mTopFragment = (TopFragment) FragmentUtil.findFragmentByTag(getSupportFragmentManager(), "TopFragment");
        mTopFragment.setText(TopFragment.Menu.CENTER, "업체 선택");
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
            storeList(getIntent().getIntExtra(EXTRA_BRAND_ID, 0));
        }

        LinearLayoutManager verticalLayoutManager = new LinearLayoutManager(CompanySelectActivity.this, LinearLayoutManager.VERTICAL, false);
        rv_company.setLayoutManager(verticalLayoutManager);
        mCompanySelectRecyclerViewAdapter = new CompanySelectRecyclerViewAdapter(CompanySelectActivity.this);
        rv_company.setAdapter(mCompanySelectRecyclerViewAdapter);
    }

    private void storeList(Integer itemBrandId) {
        showLoading();
        StoreListRequester storeListRequester = new StoreListRequester(CompanySelectActivity.this);
        storeListRequester.setLatitude(MemberManager.getInstance(CompanySelectActivity.this).getLocation().getLatitude());
        storeListRequester.setLongitude(MemberManager.getInstance(CompanySelectActivity.this).getLocation().getLongitude());
        storeListRequester.setItemBrandId(itemBrandId);

        request(storeListRequester,
                success -> {
                    StoreListResponser result = (StoreListResponser) success;
                    if (result.getCode() == 200) {
                        mCompanySelectRecyclerViewAdapter.setData(result.getList());
                        mCompanySelectRecyclerViewAdapter.notifyDataSetChanged();
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
