package com.rightcode.wellcar.Adapter.ViewPagerAdapter;

import android.content.Context;
import android.content.Intent;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class CommonFragmentPagerAdapter extends FragmentStatePagerAdapter {

    //----------------------------------------------------------------------------------------------
    // field
    //----------------------------------------------------------------------------------------------

    private long mLastClickTime = System.currentTimeMillis();
    private static final long CLICK_TIME_INTERVAL = 500;
    private Context mContext;

    //----------------------------------------------------------------------------------------------
    // constructor
    //----------------------------------------------------------------------------------------------

    public CommonFragmentPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        mContext = context;
    }

    //----------------------------------------------------------------------------------------------
    // Override
    //----------------------------------------------------------------------------------------------

    @Override
    public Fragment getItem(int i) {
        return null;
    }

    @Override
    public int getCount() {
        return 0;
    }

    //----------------------------------------------------------------------------------------------
    // public
    //----------------------------------------------------------------------------------------------

    public void startActivity(Intent intent) {
        // 더블 탭으로 인한 이중 액션 막기
        long now = System.currentTimeMillis();
        if (now - mLastClickTime < CLICK_TIME_INTERVAL) {
            return;
        }
        mLastClickTime = now;
        mContext.startActivity(intent);
    }
}
