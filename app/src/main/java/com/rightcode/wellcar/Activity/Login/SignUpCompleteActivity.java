package com.rightcode.wellcar.Activity.Login;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.rightcode.wellcar.Activity.BaseActivity;
import com.rightcode.wellcar.Activity.MainActivity;
import com.rightcode.wellcar.R;

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


    //------------------------------------------------------------------------------------------
    // private
    //------------------------------------------------------------------------------------------
    private void initialize() {
        if (getIntent() != null) {
            userId = getIntent().getStringExtra(EXTRA_USER_ID);
            userPw = getIntent().getStringExtra(EXTRA_USER_PW);
        }
    }


    @OnClick(R.id.tv_complete)
    public void onViewClicked() {
        Intent intent = new Intent(SignUpCompleteActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
