package com.rightcode.wellcar.Fragment.Main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rightcode.wellcar.Activity.AroundActivity;
import com.rightcode.wellcar.Adapter.RecyclerViewAdapter.CompanyRecyclerViewAdapter;
import com.rightcode.wellcar.Adapter.RecyclerViewAdapter.HomeReviewRecyclerViewAdapter;
import com.rightcode.wellcar.Fragment.BaseFragment;
import com.rightcode.wellcar.Fragment.TopFragment;
import com.rightcode.wellcar.MemberManager;
import com.rightcode.wellcar.R;
import com.rightcode.wellcar.RxJava.RxBus;
import com.rightcode.wellcar.Util.FragmentUtil;
import com.rightcode.wellcar.network.requester.store.StoreListRequester;
import com.rightcode.wellcar.network.responser.store.StoreListResponser;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CompanyFragment extends BaseFragment {

    @BindView(R.id.rv_company)
    RecyclerView rv_company;

    private TopFragment mTopFragment;
    private CompanyRecyclerViewAdapter mCompanyRecyclerViewAdapter;
    private View root;

    //------------------------------------------------------------------------------------------
    // contructor
    //------------------------------------------------------------------------------------------

    public static CompanyFragment newInstance() {
        CompanyFragment f = new CompanyFragment();
        Bundle b = new Bundle();
        f.setArguments(b);
        return f;
    }

    //----------------------------------------------------------------------------------------------
    // Life Cycle
    //----------------------------------------------------------------------------------------------

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_company, container, false);

        RxBus.register(this);
        ButterKnife.bind(this, root);
        initialize();
        storeList();

        return root;
    }


    //----------------------------------------------------------------------------------------------
    // private
    //----------------------------------------------------------------------------------------------

    private void initialize() {
        mTopFragment = (TopFragment) FragmentUtil.findFragmentByTag(getChildFragmentManager(), "TopFragment");
        mTopFragment.setImage(TopFragment.Menu.CENTER, R.drawable.title_bar_logo);
        mTopFragment.setImagePadding(TopFragment.Menu.CENTER, 10);

        LinearLayoutManager verticalLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rv_company.setLayoutManager(verticalLayoutManager);
        mCompanyRecyclerViewAdapter = new CompanyRecyclerViewAdapter(getContext());
        rv_company.setAdapter(mCompanyRecyclerViewAdapter);
    }

    private void storeList() {
        showLoading();
        StoreListRequester storeListRequester = new StoreListRequester(getContext());
        storeListRequester.setLatitude(MemberManager.getInstance(getContext()).getLocation().getLatitude());
        storeListRequester.setLongitude(MemberManager.getInstance(getContext()).getLocation().getLongitude());

        request(storeListRequester,
                success -> {
                    StoreListResponser result = (StoreListResponser) success;
                    if (result.getCode() == 200) {
                        mCompanyRecyclerViewAdapter.setData(result.getList());
                        mCompanyRecyclerViewAdapter.notifyDataSetChanged();
                    } else {
                        showServerErrorDialog(result.getResultMsg());
                    }
                    hideLoading();
                }, fail -> {
                    hideLoading();
                    showServerErrorDialog(fail.getResultMsg());
                });
    }
}
