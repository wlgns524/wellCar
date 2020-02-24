package com.rightcode.wellcar.Adapter.ViewPagerAdapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.rightcode.wellcar.Fragment.Main.CompanyFragment;
import com.rightcode.wellcar.Fragment.Main.HomeFragment;
import com.rightcode.wellcar.Fragment.Main.TalkFragment;
import com.rightcode.wellcar.Fragment.Main.UserFragment;


public class MainViewPagerAdapter extends FragmentPagerAdapter {
    public MainViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new HomeFragment();
            case 1:
                return new TalkFragment();
            case 2:
                return new CompanyFragment();
            case 3:
                return new UserFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        // Show 5 total pages.
        return 4;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return null;
    }


}