package com.rightcode.wellcar.Activity.Setting;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.rightcode.wellcar.Activity.BaseActivity;
import com.rightcode.wellcar.Fragment.CompanyManagementCompanyInformationFragment;
import com.rightcode.wellcar.Fragment.CompanyManagementEstimateManagementFragment;
import com.rightcode.wellcar.Fragment.CompanyManagementSettlementManagementFragment;
import com.rightcode.wellcar.Fragment.TopFragment;
import com.rightcode.wellcar.R;
import com.rightcode.wellcar.RxJava.Event;
import com.rightcode.wellcar.RxJava.RxBus;
import com.rightcode.wellcar.RxJava.RxEvent.MoveTalkEvent;
import com.rightcode.wellcar.Util.FragmentUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CompanyManagementActivity extends BaseActivity {

    @Event(MoveTalkEvent.class)
    public void onMoveTalkEvent(MoveTalkEvent event){
        finishWithAnim();
    }

    @BindView(R.id.tv_estimate_management)
    TextView tv_estimate_management;
    @BindView(R.id.tv_company_information)
    TextView tv_company_information;
    @BindView(R.id.tv_settlement_management)
    TextView tv_settlement_management;

    private Fragment currentFragment;
    private TopFragment mTopFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_management);

        ButterKnife.bind(this);
        RxBus.register(this);
        initialize();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RxBus.unregister(this);
    }

    @OnClick({R.id.tv_estimate_management, R.id.tv_company_information, R.id.tv_settlement_management})
    void onClickMenu(View view) {
        switch (view.getId()) {
            case R.id.tv_estimate_management: {
                tv_estimate_management.setBackground(getDrawable(R.drawable.app_color_border_bottom3));
                tv_company_information.setBackgroundColor(getColor(R.color.white));
                tv_settlement_management.setBackgroundColor(getColor(R.color.white));
                setFragment(CompanyManagementEstimateManagementFragment.newInstance());
                break;
            }
            case R.id.tv_company_information: {
                tv_estimate_management.setBackgroundColor(getColor(R.color.white));
                tv_company_information.setBackground(getDrawable(R.drawable.app_color_border_bottom3));
                tv_settlement_management.setBackgroundColor(getColor(R.color.white));
                setFragment(CompanyManagementCompanyInformationFragment.newInstance());
                break;
            }
            case R.id.tv_settlement_management: {
                tv_estimate_management.setBackgroundColor(getColor(R.color.white));
                tv_company_information.setBackgroundColor(getColor(R.color.white));
                tv_settlement_management.setBackground(getDrawable(R.drawable.app_color_border_bottom3));
                setFragment(CompanyManagementSettlementManagementFragment.newInstance());
                break;
            }
        }
    }


    //------------------------------------------------------------------------------------------
    // private
    //------------------------------------------------------------------------------------------

    private void initialize() {
        mTopFragment = (TopFragment) FragmentUtil.findFragmentByTag(getSupportFragmentManager(), "TopFragment");
        mTopFragment.setText(TopFragment.Menu.CENTER, "업체 관리");
        mTopFragment.setImagePadding(TopFragment.Menu.CENTER, 10);
        mTopFragment.setImage(TopFragment.Menu.LEFT, R.drawable.arrow_left);
        mTopFragment.setImagePadding(TopFragment.Menu.LEFT, 5);
        mTopFragment.setListener(TopFragment.Menu.LEFT, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishWithAnim();
            }
        });
        setFragment(CompanyManagementEstimateManagementFragment.newInstance());
    }

    private void setFragment(Fragment f) {
        if (f == null)
            return;

        FragmentUtil.startFragment(getSupportFragmentManager(), R.id.main_fl_container, f);
        FragmentUtil.removeFragment(getSupportFragmentManager(), currentFragment);
        currentFragment = f;
    }

}
