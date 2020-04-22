package com.rightcode.wellcar.Activity.Setting;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rightcode.wellcar.Activity.BaseActivity;
import com.rightcode.wellcar.Adapter.RecyclerViewAdapter.EstimateCompanyRecyclerViewAdapter;
import com.rightcode.wellcar.Fragment.TopFragment;
import com.rightcode.wellcar.R;
import com.rightcode.wellcar.Util.FragmentUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EstimateCompanyActivity extends BaseActivity {

    @BindView(R.id.rv_estimate_company)
    RecyclerView rv_estimate_company;

    private TopFragment mTopFragment;
    private EstimateCompanyRecyclerViewAdapter mEstimateCompanyRecyclerViewAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estimate_company);

        ButterKnife.bind(this);
        initialize();
    }


    //------------------------------------------------------------------------------------------
    // private
    //------------------------------------------------------------------------------------------
    private void initialize() {
        mTopFragment = (TopFragment) FragmentUtil.findFragmentByTag(getSupportFragmentManager(), "TopFragment");
        mTopFragment.setText(TopFragment.Menu.CENTER, "견적 관리");
        mTopFragment.setImagePadding(TopFragment.Menu.CENTER, 10);
        mTopFragment.setImage(TopFragment.Menu.LEFT, R.drawable.arrow_left);
        mTopFragment.setImagePadding(TopFragment.Menu.LEFT, 5);
        mTopFragment.setListener(TopFragment.Menu.LEFT, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishWithAnim();
            }
        });

        LinearLayoutManager verticalLayoutManager = new LinearLayoutManager(EstimateCompanyActivity.this, LinearLayoutManager.VERTICAL, false);
        rv_estimate_company.setLayoutManager(verticalLayoutManager);
        mEstimateCompanyRecyclerViewAdapter = new EstimateCompanyRecyclerViewAdapter(EstimateCompanyActivity.this);
        rv_estimate_company.setAdapter(mEstimateCompanyRecyclerViewAdapter);
    }
}
