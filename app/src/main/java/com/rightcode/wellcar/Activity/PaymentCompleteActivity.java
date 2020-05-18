package com.rightcode.wellcar.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.rightcode.wellcar.Fragment.TopFragment;
import com.rightcode.wellcar.R;
import com.rightcode.wellcar.RxJava.RxBus;
import com.rightcode.wellcar.RxJava.RxEvent.MoveTalkEvent;
import com.rightcode.wellcar.Util.FragmentUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.rightcode.wellcar.Util.ExtraData.EXTRA_PAYMENT_TYPE;

public class PaymentCompleteActivity extends BaseActivity {

    @BindView(R.id.tv_payment_complete_title)
    TextView tv_payment_complete_title;
    @BindView(R.id.tv_payment_complete_msg)
    TextView tv_payment_complete_msg;
    @BindView(R.id.tv_payment_complete)
    TextView tv_payment_complete;

    private TopFragment mTopFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_complete);

        ButterKnife.bind(this);
        initialize();
    }

    @OnClick({R.id.tv_payment_complete})
    void onClickMenu(View view) {
        switch (view.getId()) {
            case R.id.tv_payment_complete: {
                RxBus.send(new MoveTalkEvent());
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
        mTopFragment.setText(TopFragment.Menu.CENTER, "결제완료");
        if (getIntent() != null) {
            String paymentType = getIntent().getStringExtra(EXTRA_PAYMENT_TYPE);
            if (paymentType.equals("세차")) {
                tv_payment_complete_title.setText("이용권 구매 완료");
                tv_payment_complete_msg.setText("");
                tv_payment_complete.setText("확인");
            } else {
                tv_payment_complete_title.setText("견적 결제 완료");
                tv_payment_complete_msg.setText("웰톡으로 업체와 자세한 견적 상담을 해보세요.");
                tv_payment_complete.setText("웰톡으로 이동");
            }
        }
    }
}
