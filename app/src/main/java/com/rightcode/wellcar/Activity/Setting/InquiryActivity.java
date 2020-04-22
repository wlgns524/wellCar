package com.rightcode.wellcar.Activity.Setting;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.Nullable;

import com.rightcode.wellcar.Activity.BaseActivity;
import com.rightcode.wellcar.Fragment.TopFragment;
import com.rightcode.wellcar.R;
import com.rightcode.wellcar.Util.FragmentUtil;
import com.rightcode.wellcar.Util.ToastUtil;
import com.rightcode.wellcar.network.model.CommonResult;
import com.rightcode.wellcar.network.model.request.inquiry.InquiryRegister;
import com.rightcode.wellcar.network.requester.inquiry.InquiryRegisterRequester;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.http.Body;

public class InquiryActivity extends BaseActivity {

    @BindView(R.id.et_inquiry_tel)
    EditText et_inquiry_tel;
    @BindView(R.id.et_inquiry_title)
    EditText et_inquiry_title;
    @BindView(R.id.et_inquiry_content)
    EditText et_inquiry_content;

    private TopFragment mTopFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inquiry);

        ButterKnife.bind(this);
        initialize();
    }

    @OnClick({R.id.tv_inquiry_regist})
    void onClickMenu(View view) {
        switch (view.getId()) {
            case R.id.tv_inquiry_regist: {
                if (TextUtils.isEmpty(et_inquiry_tel.getText().toString())) {
                    ToastUtil.show(InquiryActivity.this, "핸드폰번호를 입력해주세요");
                    return;
                }
                if (TextUtils.isEmpty(et_inquiry_title.getText().toString())) {
                    ToastUtil.show(InquiryActivity.this, "제목을 입력해주세요");
                    return;
                }
                if (TextUtils.isEmpty(et_inquiry_content.getText().toString())) {
                    ToastUtil.show(InquiryActivity.this, "문의사항을 입력해주세요");
                    return;
                }

                inquiryRegist();
                break;
            }
        }
    }


    //------------------------------------------------------------------------------------------
    // private
    //------------------------------------------------------------------------------------------

    private void initialize() {
        mTopFragment = (TopFragment) FragmentUtil.findFragmentByTag(getSupportFragmentManager(), "TopFragment");
        mTopFragment.setText(TopFragment.Menu.CENTER, "문의하기");
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

    private void inquiryRegist() {
        showLoading();
        InquiryRegisterRequester inquiryRegisterRequester = new InquiryRegisterRequester(InquiryActivity.this);

        InquiryRegister param = new InquiryRegister();
        param.setTel(et_inquiry_tel.getText().toString());
        param.setTitle(et_inquiry_title.getText().toString());
        param.setContent(et_inquiry_content.getText().toString());

        inquiryRegisterRequester.setParam(param);

        request(inquiryRegisterRequester,
                success -> {
                    CommonResult result = (CommonResult) success;
                    if (result.getCode() == 200) {
                        ToastUtil.show(InquiryActivity.this, "등록되었습니다");
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