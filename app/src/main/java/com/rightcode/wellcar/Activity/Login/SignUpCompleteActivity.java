package com.rightcode.wellcar.Activity.Login;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;

import com.rightcode.wellcar.Activity.BaseActivity;
import com.rightcode.wellcar.Fragment.TopFragment;
import com.rightcode.wellcar.MemberManager;
import com.rightcode.wellcar.R;
import com.rightcode.wellcar.Util.DataEnums;
import com.rightcode.wellcar.Util.FragmentUtil;
import com.rightcode.wellcar.Util.Log;
import com.rightcode.wellcar.network.model.request.auth.Login;
import com.rightcode.wellcar.network.requester.auth.LoginRequester;
import com.rightcode.wellcar.network.responser.auth.LoginResponser;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.rightcode.wellcar.Util.ExtraData.EXTRA_USER_ID;
import static com.rightcode.wellcar.Util.ExtraData.EXTRA_USER_PW;

public class SignUpCompleteActivity extends BaseActivity {

    private String userId;
    private String userPw;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_complete);

        ButterKnife.bind(this);
        initialize();
    }


    @OnClick({R.id.tv_login})
    void onClickMenu(View view) {
        switch (view.getId()) {
            case R.id.tv_login: {
                login();
                break;
            }
        }
    }

    //------------------------------------------------------------------------------------------
    // private
    //------------------------------------------------------------------------------------------
    private void initialize() {
        if (getIntent() != null) {
            userId = getIntent().getStringExtra(EXTRA_USER_ID);
            userPw = getIntent().getStringExtra(EXTRA_USER_PW);
        }
    }

    private void login() {
        showLoading();
        LoginRequester loginRequester = new LoginRequester(SignUpCompleteActivity.this);

        Login param = new Login();
        param.setLoginId(userId);
        param.setPassword(userPw);

        loginRequester.setParam(param);
        request(loginRequester,
                success -> {
                    LoginResponser result = (LoginResponser) success;
                    hideLoading();
                    if (result.getCode() == 200) {
                        MemberManager.getInstance(SignUpCompleteActivity.this).setServiceToken(result.getToken());
                        setResult(RESULT_OK);
                        finishWithAnim();
                    } else {
                        setResult(RESULT_CANCELED);
                        finishWithAnim();
                    }
                }, fail -> {
                    hideLoading();
                    setResult(RESULT_CANCELED);
                    finishWithAnim();
                });
    }
}
