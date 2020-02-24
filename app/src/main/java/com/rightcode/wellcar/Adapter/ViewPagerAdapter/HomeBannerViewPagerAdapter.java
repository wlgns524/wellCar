package com.rightcode.wellcar.Adapter.ViewPagerAdapter;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.rightcode.wellcar.Fragment.HomeBannerFragment;

import java.util.ArrayList;

import lombok.Setter;

import static com.rightcode.wellcar.Util.ExtraData.EXTRA_IMAGE;

public class HomeBannerViewPagerAdapter extends CommonFragmentPagerAdapter {

    @Setter
    private ArrayList<String> data;

    //----------------------------------------------------------------------------------------------
    // constructor
    //----------------------------------------------------------------------------------------------

    public HomeBannerViewPagerAdapter(FragmentManager fm, Context context) {
        super(fm, context);
    }

    //----------------------------------------------------------------------------------------------
    // Override
    //----------------------------------------------------------------------------------------------

    @Override
    public Fragment getItem(int i) {

        Fragment f = new HomeBannerFragment();
        Bundle bundle = new Bundle();
//        bundle.putString(EXTRA_IMAGE, data.get(i));
        f.setArguments(bundle);
        return f;
    }

    @Override
    public int getCount() {
        if (data != null)
            return data.size();
        else
            return 5;
    }
}
