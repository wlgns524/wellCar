package com.rightcode.wellcar.Activity.Login;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;

import com.rightcode.wellcar.Activity.BaseActivity;
import com.rightcode.wellcar.Activity.MainActivity;
import com.rightcode.wellcar.MemberManager;
import com.rightcode.wellcar.R;
import com.rightcode.wellcar.Util.ToastUtil;
import com.rightcode.wellcar.network.model.request.auth.Login;
import com.rightcode.wellcar.network.requester.auth.LoginRequester;
import com.rightcode.wellcar.network.requester.user.UserInfoRequester;
import com.rightcode.wellcar.network.responser.auth.LoginResponser;
import com.rightcode.wellcar.network.responser.user.UserInfoResponser;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.rightcode.wellcar.Util.ExtraData.EXTRA_ACTIVITY_COMPLETE;

public class LoginActivity extends BaseActivity {

    @BindView(R.id.et_id)
    AppCompatEditText et_id;
    @BindView(R.id.et_pw)
    AppCompatEditText et_pw;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);
//        initialize();
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

    //------------------------------------------------------------------------------------------
    // OnClick
    //------------------------------------------------------------------------------------------

    @OnClick({R.id.tv_login, R.id.tv_find_id, R.id.tv_password_change, R.id.tv_signup})
    void onClickMenu(View view) {
        switch (view.getId()) {
            case R.id.tv_login: {
                if (TextUtils.isEmpty(et_id.getText().toString())) {
                    ToastUtil.show(LoginActivity.this, "아이디를 입력해주세요");
                    break;
                }
                if (TextUtils.isEmpty(et_pw.getText().toString())) {
                    ToastUtil.show(LoginActivity.this, "비밀번호를 입력해주세요");
                    break;
                }
                login();
                break;
            }
            case R.id.tv_find_id: {
                Intent intent = new Intent(LoginActivity.this, FindIdDepth1Activity.class);
                startActivity(intent);
                break;
            }
            case R.id.tv_password_change: {
                Intent intent = new Intent(LoginActivity.this, ChangePwActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.tv_signup: {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivityForResult(intent, EXTRA_ACTIVITY_COMPLETE);
                break;
            }
        }
    }

    private void login() {
        showLoading();
        LoginRequester loginRequester = new LoginRequester(LoginActivity.this);
        Login param = new Login();
        param.setLoginId(et_id.getText().toString().trim());
        param.setPassword(et_pw.getText().toString());
        loginRequester.setParam(param);

        request(loginRequester,
                success -> {
                    LoginResponser result = (LoginResponser) success;
                    if (result.getCode() == 200) {
                        MemberManager.getInstance(LoginActivity.this).setServiceToken(result.getToken());
                        MemberManager.getInstance(LoginActivity.this).userLogin(et_id.getText().toString().trim(), et_pw.getText().toString());
                        userInfo();
                    } else {
                        showServerErrorDialog(result.getResultMsg());
                        hideLoading();
                    }
                }, fail -> {
                    hideLoading();
                    showServerErrorDialog(fail.getResultMsg());
                });
    }

    private void userInfo() {
        UserInfoRequester userInfoRequester = new UserInfoRequester(LoginActivity.this);

        request(userInfoRequester,
                success -> {
                    UserInfoResponser result = (UserInfoResponser) success;
                    if (result.getCode() == 200) {
                        MemberManager.getInstance(LoginActivity.this).updateLogInInfo(result.getUser());
                        setResult(RESULT_OK);
                        finishWithAnim();
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
