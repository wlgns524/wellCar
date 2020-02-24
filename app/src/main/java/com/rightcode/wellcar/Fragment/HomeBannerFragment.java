package com.rightcode.wellcar.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rightcode.wellcar.R;

import butterknife.ButterKnife;

public class HomeBannerFragment extends BaseFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.item_home_banner_viewpager, container, false);

        ButterKnife.bind(this, rootView);

        Bundle extra = getArguments();
//        Glide.with(rootView.getContext())
//                .load(extra.getString(EXTRA_IMAGE))
//                .into(iv_store_image);
        return rootView;
    }
}
