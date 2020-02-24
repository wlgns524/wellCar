package com.rightcode.wellcar.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.viewpager.widget.ViewPager;

import com.rightcode.wellcar.Adapter.ViewPagerAdapter.MainViewPagerAdapter;
import com.rightcode.wellcar.Component.SwipeViewPager;
import com.rightcode.wellcar.Fragment.BottomFragment;
import com.rightcode.wellcar.R;
import com.rightcode.wellcar.Util.FragmentUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.rightcode.wellcar.Util.ExtraData.EXTRA_ACTIVITY_COMPLETE;

public class MainActivity extends BaseActivity implements ViewPager.OnPageChangeListener, BottomFragment.Interaction {


    //----------------------------------------------------------------------------------------------
    // field
    //----------------------------------------------------------------------------------------------

    @BindView(R.id.viewpager)
    SwipeViewPager mViewPager;

    private BottomFragment mBottomFragment;
    private MainViewPagerAdapter mMainViewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        initialize();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        FragmentUtil.removeFragment(getSupportFragmentManager(), mBottomFragment);
        mBottomFragment = null;
    }

    //----------------------------------------------------------------------------------------------
    // Override
    //----------------------------------------------------------------------------------------------

    @Override
    public void onBackPressed() {
        if (mViewPager != null && mViewPager.getCurrentItem() == 0) {
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
        switch (menu) {
            default:
                break;
            case HOME:
                mViewPager.setCurrentItem(0);
                break;
            case TALK:
                mViewPager.setCurrentItem(1);
                break;
            case COMPANY:
                mViewPager.setCurrentItem(2);
                break;
            case USER:
                mViewPager.setCurrentItem(3);
                break;
        }
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

    private void initialize() {
        mBottomFragment = (BottomFragment) FragmentUtil.findFragmentByTag(getSupportFragmentManager(), "BottomFragment");
        mBottomFragment.setMenu(BottomFragment.Menu.HOME); // default

        mMainViewPagerAdapter = new MainViewPagerAdapter(getSupportFragmentManager());
        mViewPager.setPagingEnabled(false);
        mViewPager.setAdapter(mMainViewPagerAdapter);
        mViewPager.setOffscreenPageLimit(4);
    }
}
