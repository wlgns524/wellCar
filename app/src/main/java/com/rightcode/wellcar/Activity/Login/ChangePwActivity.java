package com.rightcode.wellcar.Activity.Login;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;

import com.rightcode.wellcar.Activity.BaseActivity;
import com.rightcode.wellcar.Fragment.TopFragment;
import com.rightcode.wellcar.R;
import com.rightcode.wellcar.Util.CommonUtil;
import com.rightcode.wellcar.Util.DataEnums;
import com.rightcode.wellcar.Util.FragmentUtil;
import com.rightcode.wellcar.Util.ToastUtil;
import com.rightcode.wellcar.network.model.CommonResult;
import com.rightcode.wellcar.network.model.request.auth.PasswordChange;
import com.rightcode.wellcar.network.requester.auth.CertificationNumberSMSRequester;
import com.rightcode.wellcar.network.requester.auth.ConfirmRequester;
import com.rightcode.wellcar.network.requester.auth.PasswordChangeRequester;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ChangePwActivity extends BaseActivity {

    @BindView(R.id.et_id)
    AppCompatEditText et_id;
    @BindView(R.id.et_tel)
    AppCompatEditText et_tel;
    @BindView(R.id.et_certification_number)
    AppCompatEditText et_certification_number;
    @BindView(R.id.et_pw)
    AppCompatEditText et_pw;
    @BindView(R.id.et_pw_check)
    AppCompatEditText et_pw_check;

    private TopFragment mTopFragment;
    private Boolean telFlag = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pw);

        ButterKnife.bind(this);
        initialize();
    }

    @OnClick({R.id.tv_certification_number, R.id.tv_certification_number_check, R.id.tv_password_change})
    void onClickMenu(View view) {
        switch (view.getId()) {
            case R.id.tv_certification_number: {
                if (et_tel.getText().toString().length() != 11) {
                    ToastUtil.show(ChangePwActivity.this, "휴대폰 번호를 확인해주세요");
                    break;
                }
                certificationNumberSMS();
                break;
            }
            case R.id.tv_certification_number_check: {
                if (TextUtils.isEmpty(et_certification_number.getText().toString())) {
                    ToastUtil.show(ChangePwActivity.this, "인증번호를 입력해주세요");
                    break;
                }
                confirm();
                break;
            }
            case R.id.tv_password_change: {
                if (isValid()) {
                    changePassword();
                }
                break;
            }
        }
    }

    //------------------------------------------------------------------------------------------
    // private
    //------------------------------------------------------------------------------------------
    private void initialize() {
        mTopFragment = (TopFragment) FragmentUtil.findFragmentByTag(getSupportFragmentManager(), "TopFragment");
        mTopFragment.setText(TopFragment.Menu.CENTER, "비밀번호 변경");
        mTopFragment.setImagePadding(TopFragment.Menu.CENTER, 10);
        mTopFragment.setImage(TopFragment.Menu.LEFT, R.drawable.arrow_left);
        mTopFragment.setImagePadding(TopFragment.Menu.LEFT, 5);
        mTopFragment.setListener(TopFragment.Menu.LEFT, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishWithAnim();
            }
        });
    }

    private Boolean isValid() {
        if (TextUtils.isEmpty(et_id.getText().toString())) {
            ToastUtil.show(ChangePwActivity.this, "아이디를 입력해주세요");
            return false;
        }

        if (et_tel.getText().toString().length() != 11) {
            ToastUtil.show(ChangePwActivity.this, "휴대폰 번호를 확인해주세요");
            return false;
        }

        if (!telFlag) {
            ToastUtil.show(ChangePwActivity.this, "휴대폰 인증을 진행해주세요");
            return false;
        }

        if (TextUtils.isEmpty(et_pw.getText().toString())) {
            ToastUtil.show(ChangePwActivity.this, "비밀번호를 입력해주세요");
            return false;
        }

        if (TextUtils.isEmpty(et_pw_check.getText().toString())) {
            ToastUtil.show(ChangePwActivity.this, "비밀번호 확인란을 입력해주세요");
            return false;
        }

        if (!CommonUtil.isPwNumberValid(et_pw.getText().toString())) {
            ToastUtil.show(ChangePwActivity.this, "비밀번호 입력형식을 확인해주세요");
            return false;
        }

        if (!et_pw.getText().toString().equals(et_pw_check.getText().toString())) {
            ToastUtil.show(ChangePwActivity.this, "비밀번호를 확인해주세요");
            return false;
        }
        return true;
    }

    private void certificationNumberSMS() {
        showLoading();
        CertificationNumberSMSRequester certificationNumberSMSRequester = new CertificationNumberSMSRequester(ChangePwActivity.this);
        certificationNumberSMSRequester.setTel(et_tel.getText().toString());
        certificationNumberSMSRequester.setDiff(DataEnums.DiffType.FIND);

        request(certificationNumberSMSRequester,
                success -> {
                    CommonResult result = (CommonResult) success;
                    if (result.getCode() == 200) {
                        ToastUtil.show(ChangePwActivity.this, "인증번호가 요청되었습니다");
                    } else {
                        showServerErrorDialog(result.getResultMsg());
                    }
                    hideLoading();
                }, fail -> {
                    hideLoading();
                    showServerErrorDialog(fail.getResultMsg());
                });
    }

    private void confirm() {
        showLoading();
        ConfirmRequester confirmRequester = new ConfirmRequester(ChangePwActivity.this);
        confirmRequester.setTel(et_tel.getText().toString());
        confirmRequester.setConfirm(et_certification_number.getText().toString());

        request(confirmRequester,
                success -> {
                    CommonResult result = (CommonResult) success;
                    if (result.getCode() == 200) {
                        telFlag = true;
                        ToastUtil.show(ChangePwActivity.this, "인증되었습니다");
                    } else {
                        showServerErrorDialog(result.getResultMsg());
                    }
                    hideLoading();
                }, fail -> {
                    hideLoading();
                    showServerErrorDialog(fail.getResultMsg());
                });
    }

    private void changePassword() {
        showLoading();
        PasswordChangeRequester passwordChangeRequester = new PasswordChangeRequester(ChangePwActivity.this);
        PasswordChange param = new PasswordChange();
        param.setLoginId(et_id.getText().toString().trim());
        param.setPassword(et_pw.getText().toString());
        param.setTel(et_tel.getText().toString());
        passwordChangeRequester.setParam(param);

        request(passwordChangeRequester,
                success -> {
                    CommonResult result = (CommonResult) success;
                    if (result.getCode() == 200) {
                        ToastUtil.show(ChangePwActivity.this, "변경되었습니다");
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
