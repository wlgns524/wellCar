package com.rightcode.wellcar.Activity;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.Nullable;

import com.rightcode.wellcar.Dialog.CommonDialog;
import com.rightcode.wellcar.Fragment.TopFragment;
import com.rightcode.wellcar.R;
import com.rightcode.wellcar.Util.FragmentUtil;
import com.rightcode.wellcar.Util.PaymentScheme;
import com.rightcode.wellcar.network.model.CommonResult;
import com.rightcode.wellcar.network.model.request.payment.BuyCheck;
import com.rightcode.wellcar.network.requester.payment.PaymentBuyCheckRequester;

import java.net.URISyntaxException;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.rightcode.wellcar.Util.ExtraData.EXTRA_MERCHANT_UID;
import static com.rightcode.wellcar.Util.ExtraData.EXTRA_PAYMENT_URL;

public class PaymentActivity extends BaseActivity {

    @BindView(R.id.wv_payment)
    WebView wv_payment;

    private static final String APP_SCHEME = "iamporttest://";
    private TopFragment mTopFragment;
    private String merchantUid;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        ButterKnife.bind(this);
        initialize();
    }


    private void initialize() {
        mTopFragment = (TopFragment) FragmentUtil.findFragmentByTag(getSupportFragmentManager(), "TopFragment");
        mTopFragment.setText(TopFragment.Menu.CENTER, "결제하기");
        mTopFragment.setImage(TopFragment.Menu.LEFT, R.drawable.arrow_left);
        mTopFragment.setImagePadding(TopFragment.Menu.LEFT, 5);
        mTopFragment.setListener(TopFragment.Menu.LEFT, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishWithAnim();
            }
        });

        InicisWebViewClient inicisWebViewClient = new InicisWebViewClient(PaymentActivity.this);
        wv_payment.setWebViewClient(inicisWebViewClient);
        WebSettings settings = wv_payment.getSettings();
        settings.setJavaScriptEnabled(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            settings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
            CookieManager cookieManager = CookieManager.getInstance();
            cookieManager.setAcceptCookie(true);
            cookieManager.setAcceptThirdPartyCookies(wv_payment, true);
        }
        String url = null;
        if (getIntent() != null) {
            url = getIntent().getStringExtra(EXTRA_PAYMENT_URL);
            merchantUid = getIntent().getStringExtra(EXTRA_MERCHANT_UID);
        }
        wv_payment.loadUrl(url);

    }


    private void buyCheck(String impUid) {
        showLoading();
        PaymentBuyCheckRequester buyCheckRequester = new PaymentBuyCheckRequester(PaymentActivity.this);

        BuyCheck param = new BuyCheck();
        param.setImplUid(impUid);
        param.setMerchantUid(merchantUid);

        buyCheckRequester.setParam(param);

        request(buyCheckRequester,
                success -> {
                    CommonResult result = (CommonResult) success;
                    if (result.getCode() == 200) {
                        Intent intent = new Intent(PaymentActivity.this, PaymentCompleteActivity.class);
                        startActivity(intent);
                        setResult(RESULT_OK);
                        finishWithAnim();
                    } else {
                        showServerErrorDialog(result.getResultMsg());
                    }
                    hideLoading();
                }, fail -> {
                    if (!handleServerError(fail)) {
                        hideLoading();
                        showServerErrorDialog(fail.getResultMsg());
                    }
                });
    }

    public class InicisWebViewClient extends WebViewClient {

        private Activity activity;

        public InicisWebViewClient(Activity activity) {
            this.activity = activity;
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (!url.startsWith("http://") && !url.startsWith("https://") && !url.startsWith("javascript:")) {
                Intent intent = null;

                try {
                    intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME); //IntentURI처리
                    Uri uri = Uri.parse(intent.getDataString());

                    activity.startActivity(new Intent(Intent.ACTION_VIEW, uri));
                    return true;
                } catch (URISyntaxException ex) {
                    return false;
                } catch (ActivityNotFoundException e) {
                    if (intent == null) return false;

                    if (handleNotFoundPaymentScheme(intent.getScheme())) return true;

                    String packageName = intent.getPackage();
                    if (packageName != null) {
                        activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + packageName)));
                        return true;
                    }

                    return false;
                }
            } else if (url.startsWith("https://service.iamport.kr/payments/success?success=true")) {
                // 결제 성공 시
                buyCheck(url.substring(url.indexOf("imp_uid=") + 8));
            } else if (url.contains("imp_success=false")) {
                CommonDialog commonDialog = new CommonDialog(PaymentActivity.this);
                commonDialog.setMessage("결제가 취소되었습니다");
                commonDialog.setPositiveButton("확인", ok -> {
                    finishWithAnim();
                });
                commonDialog.show();

            }
            return false;
        }

        /**
         * @param scheme
         * @return 해당 scheme에 대해 처리를 직접 하는지 여부
         * <p>
         * 결제를 위한 3rd-party 앱이 아직 설치되어있지 않아 ActivityNotFoundException이 발생하는 경우 처리합니다.
         * 여기서 handler되지않은 scheme에 대해서는 intent로부터 Package정보 추출이 가능하다면 다음에서 packageName으로 market이동합니다.
         */
        protected boolean handleNotFoundPaymentScheme(String scheme) {
            //PG사에서 호출하는 url에 package정보가 없어 ActivityNotFoundException이 난 후 market 실행이 안되는 경우
            if (PaymentScheme.ISP.equalsIgnoreCase(scheme)) {
                activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + PaymentScheme.PACKAGE_ISP)));
                return true;
            } else if (PaymentScheme.BANKPAY.equalsIgnoreCase(scheme)) {
                activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + PaymentScheme.PACKAGE_BANKPAY)));
                return true;
            }

            return false;
        }
    }
}
