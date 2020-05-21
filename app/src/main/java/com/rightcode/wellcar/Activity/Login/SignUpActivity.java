package com.rightcode.wellcar.Activity.Login;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;

import com.rightcode.wellcar.Activity.BaseActivity;
import com.rightcode.wellcar.Activity.CarRegist.CarRegisterActivity;
import com.rightcode.wellcar.ApiEnums;
import com.rightcode.wellcar.Fragment.TopFragment;
import com.rightcode.wellcar.MemberManager;
import com.rightcode.wellcar.R;
import com.rightcode.wellcar.Util.CommonUtil;
import com.rightcode.wellcar.Util.DataEnums;
import com.rightcode.wellcar.Util.FragmentUtil;
import com.rightcode.wellcar.Util.ToastUtil;
import com.rightcode.wellcar.network.model.CommonResult;
import com.rightcode.wellcar.network.model.request.auth.Join;
import com.rightcode.wellcar.network.model.request.auth.Login;
import com.rightcode.wellcar.network.requester.auth.CertificationNumberSMSRequester;
import com.rightcode.wellcar.network.requester.auth.ConfirmRequester;
import com.rightcode.wellcar.network.requester.auth.ExistLoginIdRequester;
import com.rightcode.wellcar.network.requester.auth.JoinRequester;
import com.rightcode.wellcar.network.requester.auth.LoginRequester;
import com.rightcode.wellcar.network.responser.auth.LoginResponser;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

import static com.rightcode.wellcar.Util.ExtraData.EXTRA_ACTIVITY_ACTION;
import static com.rightcode.wellcar.Util.ExtraData.EXTRA_ACTIVITY_COMPLETE;
import static com.rightcode.wellcar.Util.ExtraData.EXTRA_CAR_REGIST_ROOT;
import static com.rightcode.wellcar.Util.ExtraData.EXTRA_SIGN_UP;
import static com.rightcode.wellcar.Util.ExtraData.EXTRA_USER_ID;
import static com.rightcode.wellcar.Util.ExtraData.EXTRA_USER_PW;

public class SignUpActivity extends BaseActivity {

    @BindView(R.id.tv_customer)
    AppCompatTextView tv_customer;
    @BindView(R.id.tv_company)
    AppCompatTextView tv_company;
    @BindView(R.id.et_id)
    AppCompatEditText et_id;
    @BindView(R.id.et_pw)
    AppCompatEditText et_pw;
    @BindView(R.id.et_pw_check)
    AppCompatEditText et_pw_check;
    @BindView(R.id.tv_address)
    AppCompatTextView tv_address;
    @BindView(R.id.et_address_detail)
    AppCompatEditText et_address_detail;
    @BindView(R.id.et_tel)
    AppCompatEditText et_tel;
    @BindView(R.id.et_certification_number)
    AppCompatEditText et_certification_number;

    // 업체전용
    @BindView(R.id.ll_company)
    LinearLayout ll_company;
    @BindView(R.id.et_company_name)
    AppCompatEditText et_company_name;
    @BindView(R.id.et_owner)
    AppCompatEditText et_owner;
    @BindView(R.id.et_company_number)
    AppCompatEditText et_company_number;
    @BindView(R.id.et_business_license)
    AppCompatEditText et_business_license;
    // 일반전용
    @BindView(R.id.ll_customer)
    LinearLayout ll_customer;
    @BindView(R.id.et_nickname)
    AppCompatEditText et_nickname;
    @BindView(R.id.et_birth)
    AppCompatEditText et_birth;

    //약관동의
    @BindView(R.id.rb_operation_policy)
    RadioButton rb_operation_policy;
    @BindView(R.id.rb_terms_of_service)
    RadioButton rb_terms_of_service;
    @BindView(R.id.rb_privacy)
    RadioButton rb_privacy;
    @BindView(R.id.rb_member_terms)
    RadioButton rb_member_terms;
    @BindView(R.id.rb_all)
    RadioButton rb_all;


    private TopFragment mTopFragment;
    private DataEnums.UserType role;
    private Boolean idFlag = false;
    private Boolean telFlag = false;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

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
                case EXTRA_ACTIVITY_ACTION: {
                    tv_address.setText(data.getStringExtra("result"));
                    break;
                }
                default:
                    break;
            }
        } else if (resultCode == RESULT_CANCELED) {
            switch (requestCode) {
                case EXTRA_ACTIVITY_COMPLETE: {
                    finishWithAnim();
                }
                break;
            }
        }
    }

    //------------------------------------------------------------------------------------------
    // OnCheckedChanged
    //------------------------------------------------------------------------------------------

    @OnCheckedChanged({R.id.rb_operation_policy, R.id.rb_terms_of_service, R.id.rb_privacy, R.id.rb_member_terms, R.id.rb_all})
    void onGenderSelected(CompoundButton button, boolean checked) {
        if (checked) {
            switch (button.getId()) {
                case R.id.rb_all: {
                    rb_operation_policy.setChecked(true);
                    rb_terms_of_service.setChecked(true);
                    rb_privacy.setChecked(true);
                    rb_member_terms.setChecked(true);
                    break;
                }
                case R.id.rb_operation_policy: {
                    if (rb_terms_of_service.isChecked() && rb_privacy.isChecked() && rb_member_terms.isChecked()) {
                        rb_all.setChecked(true);
                    }
                    break;
                }
                case R.id.rb_terms_of_service: {
                    if (rb_operation_policy.isChecked() && rb_privacy.isChecked() && rb_member_terms.isChecked()) {
                        rb_all.setChecked(true);
                    }
                    break;
                }
                case R.id.rb_privacy: {
                    if (rb_operation_policy.isChecked() && rb_terms_of_service.isChecked() && rb_member_terms.isChecked()) {
                        rb_all.setChecked(true);
                    }
                    break;
                }
                case R.id.rb_member_terms: {
                    if (rb_operation_policy.isChecked() && rb_terms_of_service.isChecked() && rb_privacy.isChecked()) {
                        rb_all.setChecked(true);
                    }
                    break;
                }
            }
        } else {
            rb_all.setChecked(false);
        }
    }

    //------------------------------------------------------------------------------------------
    // OnClick
    //------------------------------------------------------------------------------------------

    @OnClick({R.id.tv_customer, R.id.tv_company, R.id.ll_address,
            R.id.tv_id_exist, R.id.tv_certification_number, R.id.tv_certification_number_check, R.id.tv_signup,
            R.id.tv_operation_policy, R.id.tv_terms_of_service, R.id.tv_privacy, R.id.tv_member_terms})
    void onClickMenu(View view) {
        switch (view.getId()) {
            case R.id.tv_customer: {
                tv_customer.setSelected(true);
                tv_company.setSelected(false);
                ll_customer.setVisibility(View.VISIBLE);
                ll_company.setVisibility(View.GONE);
                role = DataEnums.UserType.CUSTOMER;
                break;
            }
            case R.id.tv_company: {
                tv_customer.setSelected(false);
                tv_company.setSelected(true);
                ll_customer.setVisibility(View.GONE);
                ll_company.setVisibility(View.VISIBLE);
                role = DataEnums.UserType.COMPANY;
                break;
            }
            case R.id.ll_address: {
                Intent intent = new Intent(SignUpActivity.this, AddressWebActivitiy.class);
                startActivityForResult(intent, EXTRA_ACTIVITY_ACTION);
                break;
            }
            case R.id.tv_id_exist: {
                if (TextUtils.isEmpty(et_id.getText().toString())) {
                    ToastUtil.show(SignUpActivity.this, "아이디를 입력해주세요");
                    break;
                }
                existLoginId();
                break;
            }
            case R.id.tv_certification_number: {
                if (et_tel.getText().toString().length() != 11) {
                    ToastUtil.show(SignUpActivity.this, "휴대폰 번호를 확인해주세요");
                    break;
                }
                certificationNumberSMS();
                break;
            }
            case R.id.tv_certification_number_check: {
                if (TextUtils.isEmpty(et_certification_number.getText().toString())) {
                    ToastUtil.show(SignUpActivity.this, "인증번호를 입력해주세요");
                    break;
                }
                confirm();
                break;
            }
            case R.id.tv_signup: {
                if (isValid()) {
                    join();
                }
                break;
            }
            case R.id.tv_operation_policy: {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://15.165.107.159:3030/operationalPolicy.html"));
                startActivity(intent);
                break;
            }
            case R.id.tv_terms_of_service: {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://15.165.107.159:3030/use.html"));
                startActivity(intent);
                break;
            }
            case R.id.tv_privacy: {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://15.165.107.159:3030/privacy.html"));
                startActivity(intent);
                break;
            }
            case R.id.tv_member_terms: {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://15.165.107.159:3030/memberRefund.html"));
                startActivity(intent);
                break;
            }

        }
    }

    //------------------------------------------------------------------------------------------
    // private
    //------------------------------------------------------------------------------------------
    private void initialize() {
        mTopFragment = (TopFragment) FragmentUtil.findFragmentByTag(getSupportFragmentManager(), "TopFragment");
        mTopFragment.setText(TopFragment.Menu.CENTER, "회원가입");
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
            ToastUtil.show(SignUpActivity.this, "아이디를 입력해주세요");
            return false;
        }

        if (!idFlag) {
            ToastUtil.show(SignUpActivity.this, "아이디 중복검사를 진행해주세요");
            return false;
        }

        if (TextUtils.isEmpty(et_pw.getText().toString())) {
            ToastUtil.show(SignUpActivity.this, "비밀번호를 입력해주세요");
            return false;
        }

        if (TextUtils.isEmpty(et_pw_check.getText().toString())) {
            ToastUtil.show(SignUpActivity.this, "비밀번호 확인란을 입력해주세요");
            return false;
        }

        if (!CommonUtil.isPwNumberValid(et_pw.getText().toString())) {
            ToastUtil.show(SignUpActivity.this, "비밀번호 입력형식을 확인해주세요");
            return false;
        }

        if (!et_pw.getText().toString().equals(et_pw_check.getText().toString())) {
            ToastUtil.show(SignUpActivity.this, "비밀번호를 확인해주세요");
            return false;
        }

        if (role == null) {
            ToastUtil.show(SignUpActivity.this, "가입형태를 선택해주세요");
            return false;
        }

        if (role.equals(DataEnums.UserType.CUSTOMER)) {
            if (TextUtils.isEmpty(et_nickname.getText().toString())) {
                ToastUtil.show(SignUpActivity.this, "닉네임를 입력해주세요");
                return false;
            }

            if (TextUtils.isEmpty(et_birth.getText().toString())) {
                ToastUtil.show(SignUpActivity.this, "생년월일을 입력해주세요");
                return false;
            }
        } else if (role.equals(DataEnums.UserType.COMPANY)) {
            if (TextUtils.isEmpty(et_company_name.getText().toString())) {
                ToastUtil.show(SignUpActivity.this, "업체명을 입력해주세요");
                return false;
            }

            if (TextUtils.isEmpty(et_owner.getText().toString())) {
                ToastUtil.show(SignUpActivity.this, "대표자명을 입력해주세요");
                return false;
            }

            if (TextUtils.isEmpty(et_company_number.getText().toString())) {
                ToastUtil.show(SignUpActivity.this, "업체 대표번호를 입력해주세요");
                return false;
            }

            if (TextUtils.isEmpty(et_business_license.getText().toString())) {
                ToastUtil.show(SignUpActivity.this, "사업자 등록번호를 입력해주세요");
                return false;
            }
        }

        if (TextUtils.isEmpty(tv_address.getText().toString())) {
            ToastUtil.show(SignUpActivity.this, "주소를 입력해주세요");
            return false;
        }

        if (TextUtils.isEmpty(et_address_detail.getText().toString())) {
            ToastUtil.show(SignUpActivity.this, "상세 주소를 입력해주세요");
            return false;
        }

        if (TextUtils.isEmpty(et_tel.getText().toString())) {
            ToastUtil.show(SignUpActivity.this, "휴대폰 번호를 입력해주세요");
            return false;
        }

        if (et_tel.getText().toString().length() != 11) {
            ToastUtil.show(SignUpActivity.this, "휴대폰 번호를 확인해주세요");
            return false;
        }

        if (!telFlag) {
            ToastUtil.show(SignUpActivity.this, "휴대폰 인증을 진행해주세요");
            return false;
        }

        if (!rb_all.isChecked()) {
            ToastUtil.show(SignUpActivity.this, "약관을 모두 동의해주세요");
            return false;
        }

        return true;
    }

    private void join() {
        showLoading();
        JoinRequester joinRequester = new JoinRequester(SignUpActivity.this);
        Join param = new Join();
        param.setLoginId(et_id.getText().toString().trim());
        param.setPassword(et_pw.getText().toString().trim());
        param.setAddress(tv_address.getText().toString());
        param.setAddressDetail(et_address_detail.getText().toString());
        param.setTel(et_tel.getText().toString());
        param.setRole(role.toString());
        if (role.equals(DataEnums.UserType.CUSTOMER)) {
            param.setNickname(et_nickname.getText().toString());
            param.setBirth(et_birth.getText().toString());
        } else if (role.equals(DataEnums.UserType.COMPANY)) {
            param.setName(et_company_name.getText().toString());
            param.setOwner(et_owner.getText().toString());
            param.setCompanyNumber(et_company_number.getText().toString());
            param.setBusinessLicense(et_business_license.getText().toString());
        }
        joinRequester.setParam(param);

        request(joinRequester,
                success -> {
                    CommonResult result = (CommonResult) success;
                    if (result.getCode() == 200) {
//                        Intent intent = new Intent(SignUpActivity.this, SignUpCompleteActivity.class);
//                        intent.putExtra(EXTRA_USER_ID, et_id.getText().toString());
//                        intent.putExtra(EXTRA_USER_PW, et_pw.getText().toString());
//                        startActivityForResult(intent, EXTRA_ACTIVITY_COMPLETE);
                        login();
                    } else {
                        showServerErrorDialog(result.getResultMsg());
                    }
                    hideLoading();
                }, fail -> {
                    hideLoading();
                    showServerErrorDialog(fail.getResultMsg());
                });
    }

    private void existLoginId() {
        showLoading();
        ExistLoginIdRequester existLoginIdRequester = new ExistLoginIdRequester(SignUpActivity.this);
        existLoginIdRequester.setLoginId(et_id.getText().toString().trim());

        request(existLoginIdRequester,
                success -> {
                    CommonResult result = (CommonResult) success;
                    if (result.getCode() == 200) {
                        idFlag = true;
                        ToastUtil.show(SignUpActivity.this, "사용가능한 아이디입니다.");
                    } else {
                        showServerErrorDialog(result.getResultMsg());
                    }
                    hideLoading();
                }, fail -> {
                    hideLoading();
                    showServerErrorDialog(fail.getResultMsg());
                });
    }

    private void certificationNumberSMS() {
        showLoading();
        CertificationNumberSMSRequester certificationNumberSMSRequester = new CertificationNumberSMSRequester(SignUpActivity.this);
        certificationNumberSMSRequester.setTel(et_tel.getText().toString());
        certificationNumberSMSRequester.setDiff(DataEnums.DiffType.JOIN);

        request(certificationNumberSMSRequester,
                success -> {
                    CommonResult result = (CommonResult) success;
                    if (result.getCode() == 200) {
                        ToastUtil.show(SignUpActivity.this, "인증번호가 요청되었습니다");
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
        ConfirmRequester confirmRequester = new ConfirmRequester(SignUpActivity.this);
        confirmRequester.setTel(et_tel.getText().toString());
        confirmRequester.setConfirm(et_certification_number.getText().toString());

        request(confirmRequester,
                success -> {
                    CommonResult result = (CommonResult) success;
                    if (result.getCode() == 200) {
                        telFlag = true;
                        ToastUtil.show(SignUpActivity.this, "인증되었습니다");
                    } else {
                        showServerErrorDialog(result.getResultMsg());
                    }
                    hideLoading();
                }, fail -> {
                    hideLoading();
                    showServerErrorDialog(fail.getResultMsg());
                });
    }

    private void login() {
        showLoading();
        LoginRequester loginRequester = new LoginRequester(SignUpActivity.this);

        Login param = new Login();
        param.setLoginId(et_id.getText().toString());
        param.setPassword(et_pw.getText().toString());

        loginRequester.setParam(param);
        request(loginRequester,
                success -> {
                    LoginResponser result = (LoginResponser) success;
                    hideLoading();
                    if (result.getCode() == 200) {
                        MemberManager.getInstance(SignUpActivity.this).setServiceToken(result.getToken());
                        MemberManager.getInstance(SignUpActivity.this).setServiceToken(result.getToken());
                        MemberManager.getInstance(SignUpActivity.this).userLogin(et_id.getText().toString().trim(), et_pw.getText().toString());
                        Intent intent = new Intent(SignUpActivity.this, CarRegisterActivity.class);
                        intent.putExtra(EXTRA_CAR_REGIST_ROOT, EXTRA_SIGN_UP);
                        startActivityForResult(intent, EXTRA_ACTIVITY_COMPLETE);
//                        setResult(RESULT_OK);
//                        finishWithAnim();
                    } else {
//                        setResult(RESULT_CANCELED);
//                        finishWithAnim();
                    }
                }, fail -> {
                    hideLoading();
                    setResult(RESULT_CANCELED);
                    finishWithAnim();
                });
    }
}
