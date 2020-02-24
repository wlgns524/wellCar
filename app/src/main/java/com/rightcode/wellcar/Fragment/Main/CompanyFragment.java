package com.rightcode.wellcar.Fragment.Main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rightcode.wellcar.Adapter.RecyclerViewAdapter.CompanyRecyclerViewAdapter;
import com.rightcode.wellcar.Adapter.RecyclerViewAdapter.HomeReviewRecyclerViewAdapter;
import com.rightcode.wellcar.Fragment.BaseFragment;
import com.rightcode.wellcar.R;
import com.rightcode.wellcar.RxJava.RxBus;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CompanyFragment extends BaseFragment {

    @BindView(R.id.rv_company)
    RecyclerView rv_company;

    private CompanyRecyclerViewAdapter mCompanyRecyclerViewAdapter;

    //----------------------------------------------------------------------------------------------
    // Life Cycle
    //----------------------------------------------------------------------------------------------

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_company, container, false);

        ButterKnife.bind(this, rootView);
        initialize();

        RxBus.register(this);
        return rootView;
    }


    //----------------------------------------------------------------------------------------------
    // private
    //----------------------------------------------------------------------------------------------

    private void initialize() {
        LinearLayoutManager verticalLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rv_company.setLayoutManager(verticalLayoutManager);
        mCompanyRecyclerViewAdapter = new CompanyRecyclerViewAdapter(getContext());
        rv_company.setAdapter(mCompanyRecyclerViewAdapter);
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
