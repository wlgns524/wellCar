package com.rightcode.wellcar.Activity.Setting;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;

import com.rightcode.wellcar.Activity.BaseActivity;
import com.rightcode.wellcar.Activity.Login.AddressWebActivitiy;
import com.rightcode.wellcar.Activity.Login.ChangePwActivity;
import com.rightcode.wellcar.Activity.Login.SignUpActivity;
import com.rightcode.wellcar.Dialog.CommonDialog;
import com.rightcode.wellcar.Fragment.TopFragment;
import com.rightcode.wellcar.MemberManager;
import com.rightcode.wellcar.R;
import com.rightcode.wellcar.Util.CommonUtil;
import com.rightcode.wellcar.Util.DataEnums;
import com.rightcode.wellcar.Util.FragmentUtil;
import com.rightcode.wellcar.Util.Log;
import com.rightcode.wellcar.Util.ToastUtil;
import com.rightcode.wellcar.network.model.CommonResult;
import com.rightcode.wellcar.network.model.request.user.UserUpdate;
import com.rightcode.wellcar.network.model.response.user.UserInfo;
import com.rightcode.wellcar.network.requester.user.UserDropRequester;
import com.rightcode.wellcar.network.requester.user.UserUpdateRequester;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.rightcode.wellcar.Util.ExtraData.EXTRA_ACTIVITY_ACTION;
import static com.rightcode.wellcar.Util.ExtraData.EXTRA_ACTIVITY_COMPLETE;

public class UserActivity extends BaseActivity {

    @BindView(R.id.tv_id)
    AppCompatTextView tv_id;
    @BindView(R.id.et_pw)
    AppCompatEditText et_pw;
    @BindView(R.id.et_pw_check)
    AppCompatEditText et_pw_check;
    @BindView(R.id.tv_address)
    AppCompatTextView tv_address;
    @BindView(R.id.et_address_detail)
    AppCompatEditText et_address_detail;
//    @BindView(R.id.et_tel)
//    AppCompatEditText et_tel;
//    @BindView(R.id.et_certification_number)
//    AppCompatEditText et_certification_number;

    // 업체전용
    @BindView(R.id.ll_company)
    LinearLayout ll_company;
    @BindView(R.id.tv_company_name)
    TextView tv_company_name;
    @BindView(R.id.tv_owner)
    TextView tv_owner;
    @BindView(R.id.et_company_number)
    AppCompatEditText et_company_number;
    @BindView(R.id.tv_business_license)
    TextView tv_business_license;
    // 일반전용
    @BindView(R.id.ll_customer)
    LinearLayout ll_customer;
    @BindView(R.id.et_nickname)
    AppCompatEditText et_nickname;
    @BindView(R.id.et_birth)
    AppCompatEditText et_birth;

    private TopFragment mTopFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        ButterKnife.bind(this);
        initialize();
        initLayout();
    }


    //------------------------------------------------------------------------------------------
    // Override
    //------------------------------------------------------------------------------------------

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case EXTRA_ACTIVITY_ACTION: {
                    tv_address.setText(data.getStringExtra("result"));
                    break;
                }
                default:
                    break;
            }
        }
    }

    @OnClick({R.id.ll_address, R.id.tv_user_update, R.id.tv_user_drop})
    void onClickMenu(View view) {
        switch (view.getId()) {
            case R.id.ll_address: {
                Intent intent = new Intent(UserActivity.this, AddressWebActivitiy.class);
                startActivityForResult(intent, EXTRA_ACTIVITY_ACTION);
                break;
            }
            case R.id.tv_user_update: {
                UserUpdate param = isValid();
                if (param != null) {
                    userUpdate(param);
                }

                break;
            }
            case R.id.tv_user_drop: {
                CommonDialog commonDialog = new CommonDialog(UserActivity.this);
                commonDialog.setMessage("회원탈퇴하시겠습니까 ?");
                commonDialog.setPositiveButton("확인", ok -> {
                    commonDialog.dismiss();
                    userDrop();
                });
                commonDialog.setNegativeButton("취소", cancel -> {
                    commonDialog.dismiss();
                });
                commonDialog.show();
                break;
            }
        }
    }


    //------------------------------------------------------------------------------------------
    // private
    //------------------------------------------------------------------------------------------
    private void initialize() {
        mTopFragment = (TopFragment) FragmentUtil.findFragmentByTag(getSupportFragmentManager(), "TopFragment");
        mTopFragment.setText(TopFragment.Menu.CENTER, "회원정보 수정");
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

    private void initLayout() {
        UserInfo userInfo = MemberManager.getInstance(UserActivity.this).getUserInfo();

        if (userInfo.getRole().equals(DataEnums.UserType.CUSTOMER)) {
            ll_customer.setVisibility(View.VISIBLE);
            ll_company.setVisibility(View.GONE);
            et_nickname.setText(userInfo.getGeneral().getNickname());
            et_birth.setText(userInfo.getGeneral().getBirth());
            tv_address.setText(userInfo.getGeneral().getAddress());
            et_address_detail.setText(userInfo.getGeneral().getAddressDetail());
        } else if (userInfo.getRole().equals(DataEnums.UserType.COMPANY)) {
            ll_customer.setVisibility(View.GONE);
            ll_company.setVisibility(View.VISIBLE);
            tv_company_name.setText(userInfo.getCompany().getName());
            tv_owner.setText(userInfo.getCompany().getOwner());
            et_company_number.setText(userInfo.getCompany().getCompanyNumber());
            tv_business_license.setText(userInfo.getCompany().getBusinessLicense());
            tv_address.setText(userInfo.getCompany().getAddress());
            et_address_detail.setText(userInfo.getCompany().getAddressDetail());
        }
        tv_id.setText(userInfo.getLoginId());
    }

    private UserUpdate isValid() {
        UserUpdate param = new UserUpdate();
        if (TextUtils.isEmpty(et_pw.getText().toString())) {
            ToastUtil.show(UserActivity.this, "비밀번호를 입력해주세요");
            return null;
        }

        if (TextUtils.isEmpty(et_pw_check.getText().toString())) {
            ToastUtil.show(UserActivity.this, "비밀번호 확인란을 입력해주세요");
            return null;
        }

        if (!CommonUtil.isPwNumberValid(et_pw.getText().toString())) {
            ToastUtil.show(UserActivity.this, "비밀번호 입력형식을 확인해주세요");
            return null;
        }

        if (!et_pw.getText().toString().equals(et_pw_check.getText().toString())) {
            ToastUtil.show(UserActivity.this, "비밀번호를 확인해주세요");
            return null;
        }
        if (TextUtils.isEmpty(et_address_detail.getText())) {
            ToastUtil.show(UserActivity.this, "상세주소를 입력해주세요");
            return null;
        }
        param.setPassword(et_pw.getText().toString());
        param.setAddress(tv_address.getText().toString());
        param.setAddressDetail(et_address_detail.getText().toString());

        MemberManager memberManager = MemberManager.getInstance(UserActivity.this);
        if (memberManager.getUserInfo().getRole().equals(DataEnums.UserType.COMPANY)) {
            if (TextUtils.isEmpty(et_company_number.getText().toString())) {
                ToastUtil.show(UserActivity.this, "업체 대표번호를 입력해주세요");
                return null;
            }
            param.setCompanyNumber(et_company_number.getText().toString());
        } else if (memberManager.getUserInfo().getRole().equals(DataEnums.UserType.CUSTOMER)) {
            if (TextUtils.isEmpty(et_nickname.getText())) {
                ToastUtil.show(UserActivity.this, "닉네임을 입력해주세요");
                return null;
            }

            if (TextUtils.isEmpty(et_birth.getText())) {
                ToastUtil.show(UserActivity.this, "생년월일을 입력해주세요");
                return null;
            }
            param.setNickname(et_nickname.getText().toString());
            param.setBirth(et_birth.getText().toString());
        }


        return param;
    }

    private void userUpdate(UserUpdate param) {
        showLoading();
        UserUpdateRequester userUpdateRequester = new UserUpdateRequester(UserActivity.this);
        userUpdateRequester.setParam(param);
        request(userUpdateRequester,
                success -> {
                    CommonResult result = (CommonResult) success;
                    if (result.getCode() == 200) {
                        ToastUtil.show(UserActivity.this, "변경되었습니다");
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


    private void userDrop() {
        showLoading();
        UserDropRequester userDropRequester = new UserDropRequester(UserActivity.this);
        request(userDropRequester,
                success -> {
                    CommonResult result = (CommonResult) success;
                    if (result.getCode() == 200) {
                        ToastUtil.show(UserActivity.this, "회원탈퇴되었습니다");
                        finishWithAnim();
                        MemberManager.getInstance(UserActivity.this).userLogout();
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
