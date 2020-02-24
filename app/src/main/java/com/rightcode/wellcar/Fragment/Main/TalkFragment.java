package com.rightcode.wellcar.Fragment.Main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rightcode.wellcar.Fragment.BaseFragment;
import com.rightcode.wellcar.R;
import com.rightcode.wellcar.RxJava.RxBus;

import butterknife.ButterKnife;

public class TalkFragment extends BaseFragment {


    //----------------------------------------------------------------------------------------------
    // Life Cycle
    //----------------------------------------------------------------------------------------------

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_talk, container, false);

        ButterKnife.bind(this, rootView);
        initialize();

        RxBus.register(this);
        return rootView;
    }


    //----------------------------------------------------------------------------------------------
    // private
    //----------------------------------------------------------------------------------------------

    private void initialize() {
//        mTopFragment = (TopFragment) FragmentUtil.findFragmentByTag(getChildFragmentManager(), "TopFragment");
//        mTopFragment.setImage(TopFragment.Menu.CENTER, R.drawable.title_logo);
//        mTopFragment.setImagePadding(TopFragment.Menu.CENTER, 10);
//        mTopFragment.setImage(TopFragment.Menu.RIGHT, R.drawable.search);
//        mTopFragment.setImagePadding(TopFragment.Menu.RIGHT, 5);
//        mTopFragment.setListener(TopFragment.Menu.RIGHT, new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (MemberManager.getInstance(getContext()).isLogin()) {
//                    Intent intent = new Intent(getContext(), SearchActivity.class);
//                    startActivity(intent);
//                } else {
//                    Intent intent = new Intent(getContext(), LoginActivity.class);
//                    startActivity(intent);
//                }
//            }
//        });
    }
}
