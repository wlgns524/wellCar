package com.rightcode.wellcar.Activity.Login;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

import com.rightcode.wellcar.Activity.BaseActivity;
import com.rightcode.wellcar.Fragment.TopFragment;
import com.rightcode.wellcar.R;
import com.rightcode.wellcar.Util.FragmentUtil;
import com.rightcode.wellcar.network.requester.auth.FindLoginIdRequester;
import com.rightcode.wellcar.network.responser.auth.FindLoginIdResponser;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.rightcode.wellcar.Util.ExtraData.EXTRA_USER_TEL;

public class FindIdDepth2Activity extends BaseActivity {
    @BindView(R.id.tv_id)
    AppCompatTextView tv_id;

    private TopFragment mTopFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_id_depth2);

        ButterKnife.bind(this);
        initialize();
    }

    @OnClick({R.id.tv_login})
    void onClickMenu(View view) {
        switch (view.getId()) {
            case R.id.tv_login: {
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
        mTopFragment.setText(TopFragment.Menu.CENTER, "아이디 찾기");
        mTopFragment.setImagePadding(TopFragment.Menu.CENTER, 10);

        if (getIntent() != null) {
            findId(getIntent().getStringExtra(EXTRA_USER_TEL));
        }
    }

    private void findId(String tel) {
        showLoading();
        FindLoginIdRequester findLoginIdRequester = new FindLoginIdRequester(FindIdDepth2Activity.this);
        findLoginIdRequester.setTel(tel);

        request(findLoginIdRequester,
                success -> {
                    FindLoginIdResponser result = (FindLoginIdResponser) success;
                    if (result.getCode() == 200) {
                        tv_id.setText(result.getLoginId());
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
