package com.rightcode.wellcar.Activity;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rightcode.wellcar.Adapter.RecyclerViewAdapter.ShoppingMallRecyclerViewAdapter;
import com.rightcode.wellcar.Fragment.TopFragment;
import com.rightcode.wellcar.R;
import com.rightcode.wellcar.Util.FragmentUtil;
import com.rightcode.wellcar.network.requester.shoppingMall.ShoppingMallListRequester;
import com.rightcode.wellcar.network.responser.shoppingMall.ShoppingMallListResponser;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ShoppingMallActivity extends BaseActivity {

    @BindView(R.id.rv_shopping_mall)
    RecyclerView rv_shopping_mall;

    private TopFragment mTopFragment;
    private ShoppingMallRecyclerViewAdapter mShoppingMallRecyclerViewAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_mall);

        ButterKnife.bind(this);
        initialize();
        shoppingMallList();
    }

    //------------------------------------------------------------------------------------------
    // private
    //------------------------------------------------------------------------------------------

    private void initialize() {
        mTopFragment = (TopFragment) FragmentUtil.findFragmentByTag(getSupportFragmentManager(), "TopFragment");
        mTopFragment.setText(TopFragment.Menu.CENTER, "쇼핑");
        mTopFragment.setImagePadding(TopFragment.Menu.CENTER, 10);
        mTopFragment.setImage(TopFragment.Menu.LEFT, R.drawable.arrow_left);
        mTopFragment.setImagePadding(TopFragment.Menu.LEFT, 5);
        mTopFragment.setListener(TopFragment.Menu.LEFT, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishWithAnim();
            }
        });


        LinearLayoutManager verticalLayoutManager = new LinearLayoutManager(ShoppingMallActivity.this, LinearLayoutManager.VERTICAL, false);
        rv_shopping_mall.setLayoutManager(verticalLayoutManager);
        mShoppingMallRecyclerViewAdapter = new ShoppingMallRecyclerViewAdapter(ShoppingMallActivity.this);
        rv_shopping_mall.setAdapter(mShoppingMallRecyclerViewAdapter);
    }

    private void shoppingMallList() {
        showLoading();
        ShoppingMallListRequester shoppingMallListRequester = new ShoppingMallListRequester(ShoppingMallActivity.this);

        request(shoppingMallListRequester,
                success -> {
                    ShoppingMallListResponser result = (ShoppingMallListResponser) success;
                    if (result.getCode() == 200) {
                        mShoppingMallRecyclerViewAdapter.setData(result.getList());
                        mShoppingMallRecyclerViewAdapter.notifyDataSetChanged();
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
