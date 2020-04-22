package com.rightcode.wellcar.Activity.Login;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;

import com.rightcode.wellcar.Activity.BaseActivity;
import com.rightcode.wellcar.Fragment.TopFragment;
import com.rightcode.wellcar.R;
import com.rightcode.wellcar.Util.DataEnums;
import com.rightcode.wellcar.Util.FragmentUtil;
import com.rightcode.wellcar.Util.ToastUtil;
import com.rightcode.wellcar.network.model.CommonResult;
import com.rightcode.wellcar.network.requester.auth.CertificationNumberSMSRequester;
import com.rightcode.wellcar.network.requester.auth.ConfirmRequester;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.rightcode.wellcar.Util.ExtraData.EXTRA_ACTIVITY_COMPLETE;
import static com.rightcode.wellcar.Util.ExtraData.EXTRA_USER_ID;
import static com.rightcode.wellcar.Util.ExtraData.EXTRA_USER_PW;
import static com.rightcode.wellcar.Util.ExtraData.EXTRA_USER_TEL;

public class FindIdDepth1Activity extends BaseActivity {

    @BindView(R.id.et_tel)
    AppCompatEditText et_tel;
    @BindView(R.id.et_certification_number)
    AppCompatEditText et_certification_number;

    private TopFragment mTopFragment;
    private Boolean telFlag = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_id_depth1);

        ButterKnife.bind(this);
        initialize();
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

    @OnClick({R.id.tv_certification_number, R.id.tv_certification_number_check, R.id.tv_find_id})
    void onClickMenu(View view) {
        switch (view.getId()) {
            case R.id.tv_certification_number: {
                if (et_tel.getText().toString().length() != 11) {
                    ToastUtil.show(FindIdDepth1Activity.this, "휴대폰 번호를 확인해주세요");
                    break;
                }
                certificationNumberSMS();
                break;
            }
            case R.id.tv_certification_number_check: {
                if (TextUtils.isEmpty(et_certification_number.getText().toString())) {
                    ToastUtil.show(FindIdDepth1Activity.this, "인증번호를 입력해주세요");
                    break;
                }
                confirm();
                break;
            }
            case R.id.tv_find_id: {
                if (!telFlag) {
                    ToastUtil.show(FindIdDepth1Activity.this, "휴대폰 인증을 진행해주세요");
                    break;
                }

                Intent intent = new Intent(FindIdDepth1Activity.this, FindIdDepth2Activity.class);
                intent.putExtra(EXTRA_USER_TEL, et_tel.getText().toString());
                startActivityForResult(intent, EXTRA_ACTIVITY_COMPLETE);
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
        mTopFragment.setImage(TopFragment.Menu.LEFT, R.drawable.arrow_left);
        mTopFragment.setImagePadding(TopFragment.Menu.LEFT, 5);
        mTopFragment.setListener(TopFragment.Menu.LEFT, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishWithAnim();
            }
        });
    }

    private void certificationNumberSMS() {
        showLoading();
        CertificationNumberSMSRequester certificationNumberSMSRequester = new CertificationNumberSMSRequester(FindIdDepth1Activity.this);
        certificationNumberSMSRequester.setTel(et_tel.getText().toString());
        certificationNumberSMSRequester.setDiff(DataEnums.DiffType.FIND);

        request(certificationNumberSMSRequester,
                success -> {
                    CommonResult result = (CommonResult) success;
                    if (result.getCode() == 200) {
                        ToastUtil.show(FindIdDepth1Activity.this, "인증번호가 요청되었습니다");
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
        ConfirmRequester confirmRequester = new ConfirmRequester(FindIdDepth1Activity.this);
        confirmRequester.setTel(et_tel.getText().toString());
        confirmRequester.setConfirm(et_certification_number.getText().toString());

        request(confirmRequester,
                success -> {
                    CommonResult result = (CommonResult) success;
                    if (result.getCode() == 200) {
                        telFlag = true;
                        ToastUtil.show(FindIdDepth1Activity.this, "인증되었습니다");
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
