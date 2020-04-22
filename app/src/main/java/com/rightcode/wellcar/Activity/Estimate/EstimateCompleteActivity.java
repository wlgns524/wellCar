package com.rightcode.wellcar.Activity.Estimate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.rightcode.wellcar.Activity.BaseActivity;
import com.rightcode.wellcar.Activity.Setting.EstimateCustomerActivity;
import com.rightcode.wellcar.Fragment.TopFragment;
import com.rightcode.wellcar.R;
import com.rightcode.wellcar.Util.FragmentUtil;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class EstimateCompleteActivity extends BaseActivity {

    private TopFragment mTopFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estimate_complete);

        ButterKnife.bind(this);
        initialize();
    }

    @OnClick({R.id.tv_estimate_complete})
    void onClickMenu(View view) {
        switch (view.getId()) {
            case R.id.tv_estimate_complete: {
                Intent intent = new Intent(EstimateCompleteActivity.this, EstimateCustomerActivity.class);
                startActivity(intent);
                setResult(RESULT_OK);
                finishWithAnim();
                break;
            }
        }
    }

    //------------------------------------------------------------------------------------------
    // private
    //------------------------------------------------------------------------------------------
    private void initialize() {
        mTopFragment = (TopFragment) FragmentUtil.findFragmentByTag(getSupportFragmentManager(), "TopFragment");
        mTopFragment.setText(TopFragment.Menu.CENTER, "견적 문의");
        mTopFragment.setImagePadding(TopFragment.Menu.CENTER, 10);
    }
}
