package com.rightcode.wellcar.Activity.Setting;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.rightcode.wellcar.Activity.BaseActivity;
import com.rightcode.wellcar.Fragment.BuyEstimateFragment;
import com.rightcode.wellcar.Fragment.BuyTicketFragment;
import com.rightcode.wellcar.Fragment.TopFragment;
import com.rightcode.wellcar.R;
import com.rightcode.wellcar.Util.FragmentUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BuyActivity extends BaseActivity {

    @BindView(R.id.rl_estimate)
    RelativeLayout rl_estimate;
    @BindView(R.id.iv_estimate)
    ImageView iv_estimate;
    @BindView(R.id.rl_ticket)
    RelativeLayout rl_ticket;
    @BindView(R.id.iv_ticket)
    ImageView iv_ticket;

    private TopFragment mTopFragment;
    private Fragment currentFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy);

        ButterKnife.bind(this);
        initialize();
    }

    @OnClick({R.id.rl_estimate, R.id.rl_ticket})
    void onClickMenu(View view) {
        switch (view.getId()) {
            case R.id.rl_estimate: {
                setFragment(BuyEstimateFragment.newInstance());
                rl_estimate.setBackground(getDrawable(R.drawable.bg_slide_left));
                rl_ticket.setBackgroundColor(getColor(R.color.color_f5f5f5));
                iv_estimate.setImageDrawable(getDrawable(R.drawable.ic_money_select));
                iv_ticket.setImageDrawable(getDrawable(R.drawable.ic_clean_car_black));
                break;
            }
            case R.id.rl_ticket: {
                setFragment(BuyTicketFragment.newInstance());
                rl_estimate.setBackgroundColor(getColor(R.color.color_f5f5f5));
                rl_ticket.setBackground(getDrawable(R.drawable.bg_slide_right));
                iv_estimate.setImageDrawable(getDrawable(R.drawable.ic_money));
                iv_ticket.setImageDrawable(getDrawable(R.drawable.ic_clean_car));
                break;
            }
        }
    }


    //------------------------------------------------------------------------------------------
    // private
    //------------------------------------------------------------------------------------------
    private void initialize() {
        mTopFragment = (TopFragment) FragmentUtil.findFragmentByTag(getSupportFragmentManager(), "TopFragment");
        mTopFragment.setText(TopFragment.Menu.CENTER, "구매관리");
        mTopFragment.setImagePadding(TopFragment.Menu.CENTER, 10);
        mTopFragment.setImage(TopFragment.Menu.LEFT, R.drawable.arrow_left);
        mTopFragment.setImagePadding(TopFragment.Menu.LEFT, 5);
        mTopFragment.setListener(TopFragment.Menu.LEFT, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishWithAnim();
            }
        });
        setFragment(BuyEstimateFragment.newInstance());
    }


    //----------------------------------------------------------------------------------------------
    // Private Function
    //----------------------------------------------------------------------------------------------
    private void setFragment(Fragment f) {
        if (f == null)
            return;

        FragmentUtil.startFragment(getSupportFragmentManager(), R.id.main_fl_container, f);
        FragmentUtil.removeFragment(getSupportFragmentManager(), currentFragment);
        currentFragment = f;
    }
}
