package com.rightcode.wellcar.Activity;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.rightcode.wellcar.Fragment.TopFragment;
import com.rightcode.wellcar.R;
import com.rightcode.wellcar.Util.FragmentUtil;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class PaymentCompleteActivity extends BaseActivity {

    private TopFragment mTopFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_complete);

        ButterKnife.bind(this);
        initialize();
    }

    @OnClick({R.id.tv_payment_complete})
    void onClickMenu(View view) {
        switch (view.getId()) {
            case R.id.tv_payment_complete: {
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
        mTopFragment.setText(TopFragment.Menu.CENTER, "결제완료");
    }
}
