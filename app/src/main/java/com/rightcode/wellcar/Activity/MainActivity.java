package com.rightcode.wellcar.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.rightcode.wellcar.Fragment.BottomFragment;
import com.rightcode.wellcar.Fragment.Main.CompanyFragment;
import com.rightcode.wellcar.Fragment.Main.HomeFragment;
import com.rightcode.wellcar.Fragment.Main.TalkFragment;
import com.rightcode.wellcar.Fragment.Main.UserFragment;
import com.rightcode.wellcar.MemberManager;
import com.rightcode.wellcar.R;
import com.rightcode.wellcar.Util.FragmentUtil;
import com.rightcode.wellcar.Util.Log;
import com.rightcode.wellcar.Util.PreferenceUtil;
import com.rightcode.wellcar.network.model.request.auth.Login;
import com.rightcode.wellcar.network.requester.auth.LoginRequester;
import com.rightcode.wellcar.network.requester.user.UserInfoRequester;
import com.rightcode.wellcar.network.responser.auth.LoginResponser;
import com.rightcode.wellcar.network.responser.user.UserInfoResponser;

import butterknife.ButterKnife;

import static com.rightcode.wellcar.Util.ExtraData.EXTRA_ACTIVITY_COMPLETE;

public class MainActivity extends BaseActivity implements ViewPager.OnPageChangeListener, BottomFragment.Interaction {


    //----------------------------------------------------------------------------------------------
    // field
    //----------------------------------------------------------------------------------------------

    //    @BindView(R.id.viewpager)
//    SwipeViewPager mViewPager;

    private Fragment currentFragment;
    private BottomFragment mBottomFragment;

//    private MainViewPagerAdapter mMainViewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        loginCheck();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        FragmentUtil.removeFragment(getSupportFragmentManager(), mBottomFragment);
        mBottomFragment = null;
        currentFragment = null;
    }


    //----------------------------------------------------------------------------------------------
    // Override
    //----------------------------------------------------------------------------------------------

    @Override
    public void onBackPressed() {
        if (currentFragment instanceof HomeFragment) {
            if (System.currentTimeMillis() > backKeyPressedTime + EXIT_BACK_PRESSED_TIME) {
                backKeyPressedTime = System.currentTimeMillis();
                toast = Toast.makeText(getApplicationContext(), getString(R.string.exit_message), Toast.LENGTH_SHORT);
                toast.show();
            } else {
                finishAffinity();
                toast.cancel();
            }
        } else {
            mBottomFragment.setMenu(BottomFragment.Menu.HOME);
            onChangeMenu(BottomFragment.Menu.HOME);
        }
    }

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

    @Override
    public void onChangeMenu(BottomFragment.Menu menu) {
        Fragment f = null;
        switch (menu) {
            case HOME:
                f = HomeFragment.newInstance();
                break;
            case TALK:
                f = TalkFragment.newInstance();
                break;
            case COMPANY:
                f = CompanyFragment.newInstance();
                break;
            case USER:
                f = UserFragment.newInstance();
                break;
        }
        setFragment(f);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    //----------------------------------------------------------------------------------------------
    // Private Function
    //----------------------------------------------------------------------------------------------
    private void setFragment(Fragment f) {
        if (f == null)
            return;

        FragmentUtil.startFragment(getSupportFragmentManager(), R.id.main_fl_container, f);
        FragmentUtil.removeFragment(getSupportFragmentManager(), currentFragment);
        currentFragment = f;
    }

    private void initialize() {
        mBottomFragment = (BottomFragment) FragmentUtil.findFragmentByTag(getSupportFragmentManager(), "BottomFragment");
        mBottomFragment.setMenu(BottomFragment.Menu.HOME); // default
    }

    private void loginCheck() {
        String userId = PreferenceUtil.getInstance(MainActivity.this).get(PreferenceUtil.PreferenceKey.UserId, "");
        String userPw = PreferenceUtil.getInstance(MainActivity.this).get(PreferenceUtil.PreferenceKey.UserPw, "");
        if (!userId.equals("") && !userPw.equals("")) {
            login(userId, userPw);
        } else {
            initialize();
        }
    }

    private void login(String userId, String userPw) {
        LoginRequester loginRequester = new LoginRequester(MainActivity.this);
        Login param = new Login();
        param.setLoginId(userId);
        param.setPassword(userPw);
        loginRequester.setParam(param);

        request(loginRequester,
                success -> {
                    LoginResponser result = (LoginResponser) success;
                    if (result.getCode() == 200) {
                        MemberManager.getInstance(MainActivity.this).setServiceToken(result.getToken());
                        MemberManager.getInstance(MainActivity.this).userLogin(userId, userPw);
                        userInfo();
                    } else {
                    }
                }, fail -> {
                });
    }

    private void userInfo() {
        showLoading();
        UserInfoRequester userInfoRequester = new UserInfoRequester(MainActivity.this);

        request(userInfoRequester,
                success -> {
                    UserInfoResponser result = (UserInfoResponser) success;
                    if (result.getCode() == 200) {
                        MemberManager.getInstance(MainActivity.this).updateLogInInfo(result.getUser());
                    } else {
                        MemberManager.getInstance(MainActivity.this).userLogout();
                    }
                    initialize();
                    hideLoading();
                }, fail -> {
                    hideLoading();
                    showServerErrorDialog(fail.getResultMsg());
                });
    }
}
