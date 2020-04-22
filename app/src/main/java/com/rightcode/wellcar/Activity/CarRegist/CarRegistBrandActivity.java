package com.rightcode.wellcar.Activity.CarRegist;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rightcode.wellcar.Activity.BaseActivity;
import com.rightcode.wellcar.Adapter.RecyclerViewAdapter.CarRegistBrandDomesticRecyclerViewAdapter;
import com.rightcode.wellcar.Adapter.RecyclerViewAdapter.CarRegistBrandImportedRecyclerViewAdapter;
import com.rightcode.wellcar.Fragment.TopFragment;
import com.rightcode.wellcar.R;
import com.rightcode.wellcar.Util.DataEnums;
import com.rightcode.wellcar.Util.FragmentUtil;
import com.rightcode.wellcar.network.model.response.brand.Brand;
import com.rightcode.wellcar.network.requester.brand.BrandListRequester;
import com.rightcode.wellcar.network.responser.brand.BrandListResponser;
import com.zhy.view.flowlayout.TagAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CarRegistBrandActivity extends BaseActivity {

    @BindView(R.id.rv_domestic_list)
    RecyclerView rv_domestic_list;
    @BindView(R.id.rv_imported_list)
    RecyclerView rv_imported_list;

//    @BindView(R.id.fl_domestic_list)
//    TagFlowLayout fl_domestic_list;
//    @BindView(R.id.fl_imported_list)
//    TagFlowLayout fl_imported_list;
//private LayoutInflater domesticInflater;
//    private LayoutInflater importedInflater;
//    private ArrayList<Brand> domesticList;
//    private ArrayList<Brand> importedList;
//    private TagAdapter domesticAdapter;
//    private TagAdapter importedAdapter;

    private TopFragment mTopFragment;


    private CarRegistBrandDomesticRecyclerViewAdapter mCarRegistBrandRecyclerViewAdapter;
    private CarRegistBrandImportedRecyclerViewAdapter mCarRegistBrandImportedRecyclerViewAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_regist_brand);

        ButterKnife.bind(this);
        initialize();
        brandList(DataEnums.BrandType.DOMESTIC);
        brandList(DataEnums.BrandType.IMPORTED);
    }

    //------------------------------------------------------------------------------------------
    // private
    //------------------------------------------------------------------------------------------
    private void initialize() {
        mTopFragment = (TopFragment) FragmentUtil.findFragmentByTag(getSupportFragmentManager(), "TopFragment");
        mTopFragment.setText(TopFragment.Menu.CENTER, "차량 등록하기");
        mTopFragment.setImagePadding(TopFragment.Menu.CENTER, 10);
        mTopFragment.setImage(TopFragment.Menu.LEFT, R.drawable.arrow_left);
        mTopFragment.setImagePadding(TopFragment.Menu.LEFT, 5);
        mTopFragment.setListener(TopFragment.Menu.LEFT, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishWithAnim();
            }
        });

        GridLayoutManager gridLayoutManager1 = new GridLayoutManager(CarRegistBrandActivity.this, 3);
        rv_domestic_list.setLayoutManager(gridLayoutManager1);
        mCarRegistBrandRecyclerViewAdapter = new CarRegistBrandDomesticRecyclerViewAdapter(CarRegistBrandActivity.this);
        rv_domestic_list.setAdapter(mCarRegistBrandRecyclerViewAdapter);

        GridLayoutManager gridLayoutManager2 = new GridLayoutManager(CarRegistBrandActivity.this, 3);
        rv_imported_list.setLayoutManager(gridLayoutManager2);
        mCarRegistBrandImportedRecyclerViewAdapter = new CarRegistBrandImportedRecyclerViewAdapter(CarRegistBrandActivity.this);
        rv_imported_list.setAdapter(mCarRegistBrandImportedRecyclerViewAdapter);


        // flowLayout 에서 recyclerView로 전환
//        domesticInflater = LayoutInflater.from(CarRegistBrandActivity.this);
//        importedInflater = LayoutInflater.from(CarRegistBrandActivity.this);
//        fl_domestic_list.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
//            @Override
//            public boolean onTagClick(View view, int position, FlowLayout parent) {
//                Intent resultIntent = new Intent();
//                resultIntent.putExtra("brand", domesticList.get(position).getName());
//                resultIntent.putExtra("brandId", domesticList.get(position).getId());
//                setResult(RESULT_OK, resultIntent);
//                finish();
//                return true;
//            }
//        });
//
//        fl_imported_list.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
//            @Override
//            public boolean onTagClick(View view, int position, FlowLayout parent) {
//                Intent resultIntent = new Intent();
//                resultIntent.putExtra("brand", importedList.get(position).getName());
//                resultIntent.putExtra("brandId", importedList.get(position).getId());
//                setResult(RESULT_OK, resultIntent);
//                finish();
//                return true;
//            }
//        });
    }

    private void brandList(DataEnums.BrandType origin) {
        showLoading();
        BrandListRequester brandListRequester = new BrandListRequester(CarRegistBrandActivity.this);
        brandListRequester.setOrigin(origin);
        request(brandListRequester,
                success -> {
                    BrandListResponser result = (BrandListResponser) success;
                    if (result.getCode() == 200) {
                        if (origin.equals(DataEnums.BrandType.DOMESTIC)) {
                            mCarRegistBrandRecyclerViewAdapter.setData(result.getList());
                            mCarRegistBrandRecyclerViewAdapter.notifyDataSetChanged();
                        } else if (origin.equals(DataEnums.BrandType.IMPORTED)) {
                            mCarRegistBrandImportedRecyclerViewAdapter.setData(result.getList());
                            mCarRegistBrandImportedRecyclerViewAdapter.notifyDataSetChanged();
                        }
                    } else {
                        showServerErrorDialog(result.getResultMsg());
                    }
                    hideLoading();
                }, fail -> {
                    hideLoading();
                    showServerErrorDialog(fail.getResultMsg());
                });
    }

//    private void initDomesticLayout(ArrayList<Brand> brandList) {
//        domesticList = brandList;
//        // 데이터 추가
//        int size = 0;
//        Brand[] mVals = new Brand[domesticList.size()];
//        for (Brand brand : domesticList) {
//            mVals[size++] = brand;
//        }
//        domesticAdapter = new TagAdapter<Brand>(mVals) {
//            @Override
//            public View getView(FlowLayout parent, int position, Brand brand) {
//                final View view = domesticInflater.inflate(R.layout.item_car_regist_brand, fl_domestic_list, false);
//                ImageView iv_brand = (ImageView) view.findViewById(R.id.iv_brand);
//                TextView tv_brand = (TextView) view.findViewById(R.id.tv_brand);
//
//                try {
//                    Glide.with(CarRegistBrandActivity.this).load(brand.getImage().getName())
//                            .diskCacheStrategy(DiskCacheStrategy.NONE)
//                            .skipMemoryCache(true)
//                            .centerCrop()
//                            .into(iv_brand);
//                } catch (NullPointerException e) {
//
//                }
//                tv_brand.setText(brand.getName());
//                Log.d(brand);
//                return view;
//            }
//
//        };
//        fl_domestic_list.setAdapter(domesticAdapter);
//    }
//
//    private void initImportedLayout(ArrayList<Brand> brandList) {
//        importedList = brandList;
//
//        // 데이터 추가
//        int size = 0;
//        Brand[] mVals = new Brand[importedList.size()];
//        for (Brand brand : importedList) {
//            mVals[size++] = brand;
//        }
//
//        importedAdapter = new TagAdapter<Brand>(mVals) {
//            @Override
//            public View getView(FlowLayout parent, int position, Brand brand) {
//                final View view = importedInflater.inflate(R.layout.item_car_regist_brand, fl_imported_list, false);
//                ImageView iv_brand = (ImageView) view.findViewById(R.id.iv_brand);
//                TextView tv_brand = (TextView) view.findViewById(R.id.tv_brand);
//                try {
//                    Glide.with(CarRegistBrandActivity.this).load(brand.getImage().getName())
//                            .diskCacheStrategy(DiskCacheStrategy.NONE)
//                            .skipMemoryCache(true)
//                            .centerCrop()
//                            .into(iv_brand);
//                } catch (NullPointerException e) {
//
//                }
//                tv_brand.setText(brand.getName());
//                return view;
//            }
//
//        };
//        fl_imported_list.setAdapter(importedAdapter);
//    }
}
