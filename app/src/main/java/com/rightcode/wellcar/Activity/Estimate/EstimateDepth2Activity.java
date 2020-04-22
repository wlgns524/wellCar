package com.rightcode.wellcar.Activity.Estimate;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

import com.rightcode.wellcar.Activity.BaseActivity;
import com.rightcode.wellcar.Adapter.SpinnerAdapter.ItemBrandSpinnerAdapter;
import com.rightcode.wellcar.Adapter.SpinnerAdapter.ItemSpinnerAdapter;
import com.rightcode.wellcar.Fragment.TopFragment;
import com.rightcode.wellcar.R;
import com.rightcode.wellcar.Util.DataEnums;
import com.rightcode.wellcar.Util.FragmentUtil;
import com.rightcode.wellcar.Util.Log;
import com.rightcode.wellcar.Util.ToastUtil;
import com.rightcode.wellcar.network.model.request.estimate.EstimateRegister;
import com.rightcode.wellcar.network.model.response.item.Item;
import com.rightcode.wellcar.network.model.response.itemBrand.ItemBrand;
import com.rightcode.wellcar.network.requester.item.ItemListRequester;
import com.rightcode.wellcar.network.requester.itemBrand.ItemBrandListRequester;
import com.rightcode.wellcar.network.responser.item.ItemListResponser;
import com.rightcode.wellcar.network.responser.itemBrand.ItemBrandListResponser;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import butterknife.OnTouch;

import static com.rightcode.wellcar.Util.ExtraData.EXTRA_ACTIVITY_COMPLETE;
import static com.rightcode.wellcar.Util.ExtraData.EXTRA_ESTIMATE_REGISTER;
import static com.rightcode.wellcar.Util.ExtraData.EXTRA_ITEMS;

public class EstimateDepth2Activity extends BaseActivity implements Spinner.OnItemSelectedListener {

    @BindView(R.id.tv_cost)
    TextView tv_cost;
    @BindView(R.id.rb_cost)
    RadioButton rb_cost;
    @BindView(R.id.tv_premium)
    TextView tv_premium;
    @BindView(R.id.rb_premium)
    RadioButton rb_premium;

    @BindView(R.id.tv_address_si)
    TextView tv_address_si;
    @BindView(R.id.tv_address_gu)
    TextView tv_address_gu;
    @BindView(R.id.fl_select_list)
    TagFlowLayout fl_select_list;
    @BindView(R.id.sn_sunblock_brand)
    Spinner sn_sunblock_brand;
    @BindView(R.id.sn_sunblock_kind)
    Spinner sn_sunblock_kind;
    @BindView(R.id.sn_glass_brand)
    Spinner sn_glass_brand;
    @BindView(R.id.sn_glass_kind)
    Spinner sn_glass_kind;
    @BindView(R.id.sn_blackbox_brand)
    Spinner sn_blackbox_brand;
    @BindView(R.id.sn_blackbox_kind)
    Spinner sn_blackbox_kind;
    @BindView(R.id.sn_undercoating_brand)
    Spinner sn_undercoating_brand;
    @BindView(R.id.sn_undercoating_kind)
    Spinner sn_undercoating_kind;
    @BindView(R.id.sn_ppf_brand)
    Spinner sn_ppf_brand;
    @BindView(R.id.sn_ppf_kind)
    Spinner sn_ppf_kind;
    @BindView(R.id.sn_tuning_brand)
    Spinner sn_tuning_brand;
    @BindView(R.id.sn_tuning_kind)
    Spinner sn_tuning_kind;
    @BindView(R.id.sn_tire_brand)
    Spinner sn_tire_brand;
    @BindView(R.id.sn_tire_kind)
    Spinner sn_tire_kind;


    private TopFragment mTopFragment;
    private TagAdapter adapter;
    private EstimateRegister register;
    private LayoutInflater mInflater;
    private ArrayList<Item> values = new ArrayList<>();
    private ArrayList<Item> packageValues = new ArrayList<>();

    private Integer sunblockId;
    private Integer glassId;
    private Integer blackBoxId;
    private Integer underCoatingId;
    private Integer ppfId;
    private Integer tuningId;
    private Integer tireId;
    private ItemBrandSpinnerAdapter sunblockBrandSpinnerAdapter;
    private ItemSpinnerAdapter sunblockKindSpinnerAdapter;
    private ItemBrandSpinnerAdapter glassBrandSpinnerAdapter;
    private ItemSpinnerAdapter glassKindSpinnerAdapter;
    private ItemBrandSpinnerAdapter blackboxBrandSpinnerAdapter;
    private ItemSpinnerAdapter blackboxKindSpinnerAdapter;
    private ItemBrandSpinnerAdapter undercoatingBrandSpinnerAdapter;
    private ItemSpinnerAdapter undercoatingKindSpinnerAdapter;
    private ItemBrandSpinnerAdapter ppfBrandSpinnerAdapter;
    private ItemSpinnerAdapter ppfKindSpinnerAdapter;
    private ItemBrandSpinnerAdapter tuningBrandSpinnerAdapter;
    private ItemSpinnerAdapter tuningKindSpinnerAdapter;
    private ItemBrandSpinnerAdapter tireBrandSpinnerAdapter;
    private ItemSpinnerAdapter tireKindSpinnerAdapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estimate_depth2);

        ButterKnife.bind(this);
        initialize();
        itemPackageList();
    }

    //------------------------------------------------------------------------------------------
    // Override
    //------------------------------------------------------------------------------------------

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

    //------------------------------------------------------------------------------------------
    // private
    //------------------------------------------------------------------------------------------

    private void initialize() {
        if (getIntent() != null) {
            register = (EstimateRegister) getIntent().getSerializableExtra(EXTRA_ESTIMATE_REGISTER);
            tv_address_si.setText(register.getSi());
            tv_address_gu.setText(register.getGu());
        }
        mTopFragment = (TopFragment) FragmentUtil.findFragmentByTag(getSupportFragmentManager(), "TopFragment");
        mTopFragment.setText(TopFragment.Menu.CENTER, "시공견적");
        mTopFragment.setImagePadding(TopFragment.Menu.CENTER, 10);
        mTopFragment.setImage(TopFragment.Menu.LEFT, R.drawable.arrow_left);
        mTopFragment.setImagePadding(TopFragment.Menu.LEFT, 5);
        mTopFragment.setListener(TopFragment.Menu.LEFT, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishWithAnim();
            }
        });

        // Flow Layout
        mInflater = LayoutInflater.from(EstimateDepth2Activity.this);
        fl_select_list.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                String value = String.format("%s(%s)", values.get(position).getName(), values.get(position).getItemBrand().getName());
                if (value.equals("가성비패키지(썬팅(레이노 s9) + 블랙박스(아이나비) + 유리막)")) {
                    rb_cost.setChecked(false);
                }
                if (value.equals("프리미엄 패키지(썬팅(레인보우 v90) + 블랙박스(아이나비) + 유리막 + 언더코팅)")) {
                    rb_premium.setChecked(false);
                }
                values.remove(position);
                selectListRefresh();
                return true;
            }
        });

        //Spinner Adapter
        sunblockBrandSpinnerAdapter = new ItemBrandSpinnerAdapter(EstimateDepth2Activity.this);
        sunblockKindSpinnerAdapter = new ItemSpinnerAdapter(EstimateDepth2Activity.this);
        glassBrandSpinnerAdapter = new ItemBrandSpinnerAdapter(EstimateDepth2Activity.this);
        glassKindSpinnerAdapter = new ItemSpinnerAdapter(EstimateDepth2Activity.this);
        blackboxBrandSpinnerAdapter = new ItemBrandSpinnerAdapter(EstimateDepth2Activity.this);
        blackboxKindSpinnerAdapter = new ItemSpinnerAdapter(EstimateDepth2Activity.this);
        undercoatingBrandSpinnerAdapter = new ItemBrandSpinnerAdapter(EstimateDepth2Activity.this);
        undercoatingKindSpinnerAdapter = new ItemSpinnerAdapter(EstimateDepth2Activity.this);
        ppfBrandSpinnerAdapter = new ItemBrandSpinnerAdapter(EstimateDepth2Activity.this);
        ppfKindSpinnerAdapter = new ItemSpinnerAdapter(EstimateDepth2Activity.this);
        tuningBrandSpinnerAdapter = new ItemBrandSpinnerAdapter(EstimateDepth2Activity.this);
        tuningKindSpinnerAdapter = new ItemSpinnerAdapter(EstimateDepth2Activity.this);
        tireBrandSpinnerAdapter = new ItemBrandSpinnerAdapter(EstimateDepth2Activity.this);
        tireKindSpinnerAdapter = new ItemSpinnerAdapter(EstimateDepth2Activity.this);
        //Adapter 적용
        sn_sunblock_brand.setAdapter(sunblockBrandSpinnerAdapter);
        sn_sunblock_brand.setOnItemSelectedListener(this);
        sn_sunblock_kind.setAdapter(sunblockKindSpinnerAdapter);
        sn_sunblock_kind.setOnItemSelectedListener(this);
        sn_glass_brand.setAdapter(glassBrandSpinnerAdapter);
        sn_glass_brand.setOnItemSelectedListener(this);
        sn_glass_kind.setAdapter(glassKindSpinnerAdapter);
        sn_glass_kind.setOnItemSelectedListener(this);
        sn_blackbox_brand.setAdapter(blackboxBrandSpinnerAdapter);
        sn_blackbox_brand.setOnItemSelectedListener(this);
        sn_blackbox_kind.setAdapter(blackboxKindSpinnerAdapter);
        sn_blackbox_kind.setOnItemSelectedListener(this);
        sn_undercoating_brand.setAdapter(undercoatingBrandSpinnerAdapter);
        sn_undercoating_brand.setOnItemSelectedListener(this);
        sn_undercoating_kind.setAdapter(undercoatingKindSpinnerAdapter);
        sn_undercoating_kind.setOnItemSelectedListener(this);
        sn_ppf_brand.setAdapter(ppfBrandSpinnerAdapter);
        sn_ppf_brand.setOnItemSelectedListener(this);
        sn_ppf_kind.setAdapter(ppfKindSpinnerAdapter);
        sn_ppf_kind.setOnItemSelectedListener(this);
        sn_tuning_brand.setAdapter(tuningBrandSpinnerAdapter);
        sn_tuning_brand.setOnItemSelectedListener(this);
        sn_tuning_kind.setAdapter(tuningKindSpinnerAdapter);
        sn_tuning_kind.setOnItemSelectedListener(this);
        sn_tire_brand.setAdapter(tireBrandSpinnerAdapter);
        sn_tire_brand.setOnItemSelectedListener(this);
        sn_tire_kind.setAdapter(tireKindSpinnerAdapter);
        sn_tire_kind.setOnItemSelectedListener(this);
    }

    @OnCheckedChanged({R.id.rb_cost, R.id.rb_premium})
    void onGenderSelected(CompoundButton button, boolean checked) {
        if (checked) {
            switch (button.getId()) {
                case R.id.rb_cost: {
                    rb_cost.setChecked(checked);
                    rb_premium.setChecked(!checked);
                    values.clear();
                    values.add(packageValues.get(0));
                    break;
                }
                case R.id.rb_premium: {
                    rb_cost.setChecked(!checked);
                    rb_premium.setChecked(checked);
                    values.clear();
                    values.add(packageValues.get(1));
                    break;
                }
            }
            selectListRefresh();
        }
    }

    @OnTouch({R.id.rl_sunblock_brand, R.id.rl_sunblock_kind, R.id.rl_glass_brand, R.id.rl_glass_kind,
            R.id.rl_blackbox_brand, R.id.rl_blackbox_kind, R.id.rl_undercoating_brand, R.id.rl_undercoating_kind,
            R.id.rl_ppf_brand, R.id.rl_ppf_kind, R.id.rl_tuning_brand, R.id.rl_tuning_kind, R.id.rl_tire_brand, R.id.rl_tire_kind})
    void onTouchMenu(View view) {
        switch (view.getId()) {
            case R.id.rl_sunblock_brand: {
                itemBrandList(DataEnums.ItemDiffType.SUNBLOCK);
                break;
            }
            case R.id.rl_sunblock_kind: {
                if (sunblockId != null)
                    itemList(DataEnums.ItemDiffType.SUNBLOCK, sunblockId);
                break;
            }
            case R.id.rl_glass_brand: {
                itemBrandList(DataEnums.ItemDiffType.GLASS);
                break;
            }
            case R.id.rl_glass_kind: {
                if (glassId != null)
                    itemList(DataEnums.ItemDiffType.GLASS, glassId);
                break;
            }
            case R.id.rl_blackbox_brand: {
                itemBrandList(DataEnums.ItemDiffType.BLACKBOX);
                break;
            }
            case R.id.rl_blackbox_kind: {
                if (blackBoxId != null)
                    itemList(DataEnums.ItemDiffType.BLACKBOX, blackBoxId);
                break;
            }
            case R.id.rl_undercoating_brand: {
                itemBrandList(DataEnums.ItemDiffType.UNDERCOATING);
                break;
            }
            case R.id.rl_undercoating_kind: {
                if (underCoatingId != null)
                    itemList(DataEnums.ItemDiffType.UNDERCOATING, underCoatingId);
                break;
            }
            case R.id.rl_ppf_brand: {
                itemBrandList(DataEnums.ItemDiffType.PPF);
                break;
            }
            case R.id.rl_ppf_kind: {
                if (ppfId != null)
                    itemList(DataEnums.ItemDiffType.PPF, ppfId);
                break;
            }
            case R.id.rl_tuning_brand: {
                itemBrandList(DataEnums.ItemDiffType.TUNING);
                break;
            }
            case R.id.rl_tuning_kind: {
                if (tuningId != null)
                    itemList(DataEnums.ItemDiffType.TUNING, tuningId);
                break;
            }
            case R.id.rl_tire_brand: {
                itemBrandList(DataEnums.ItemDiffType.TIRE);
                break;
            }
            case R.id.rl_tire_kind: {
                if (tireId != null)
                    itemList(DataEnums.ItemDiffType.TIRE, tireId);
                break;
            }
        }
    }

    @OnClick({R.id.tv_estimate})
    void onClickMenu(View view) {
        switch (view.getId()) {
            case R.id.tv_estimate: {
                Intent intent = new Intent(EstimateDepth2Activity.this, EstimateDepth3Activity.class);
                if (rb_cost.isChecked()) {
                    register.setItemIds(new ArrayList<Integer>(
                            Arrays.asList(packageValues.get(0).getId())));
                } else if (rb_premium.isChecked()) {
                    register.setItemIds(new ArrayList<Integer>(
                            Arrays.asList(packageValues.get(1).getId())));
                } else {
                    values.remove(packageValues.get(0));
                    values.remove(packageValues.get(1));
                    ArrayList<Integer> itemIds = new ArrayList<>();
                    for (Item item : values) {
                        itemIds.add(item.getId());
                    }
                    register.setItemIds(itemIds);
                }
                if (values.size() < 1) {
                    ToastUtil.show(EstimateDepth2Activity.this, "옵션을 선택해주세요");
                    break;
                }
                intent.putExtra(EXTRA_ESTIMATE_REGISTER, register);
                intent.putExtra(EXTRA_ITEMS, values);
                startActivityForResult(intent, EXTRA_ACTIVITY_COMPLETE);
                break;
            }

        }
    }

    private void selectListRefresh() {
        ArrayList<String> overlapList = new ArrayList();
        int size = 0;
        //중복 제거 반복문
        for (Item item : values) {
            if (!overlapList.contains(String.format("%s(%s)", item.getName(), item.getItemBrand().getName())))
                overlapList.add(String.format("%s(%s)", item.getName(), item.getItemBrand().getName()));
        }

        // 체크되어있지 않을 시 제거
        if (!rb_cost.isChecked()) {
            String cost = String.format("%s(%s)", packageValues.get(0).getName(), packageValues.get(0).getItemBrand().getName());
            overlapList.remove(cost);
        }

        if (!rb_premium.isChecked()) {
            String premium = String.format("%s(%s)", packageValues.get(1).getName(), packageValues.get(1).getItemBrand().getName());
            overlapList.remove(premium);
        }

        // 데이터 추가
        String[] mVals = new String[overlapList.size()];
        for (String str : overlapList) {
            mVals[size++] = str;
        }

        adapter = new TagAdapter<String>(mVals) {
            @Override
            public View getView(FlowLayout parent, int position, String s) {
                AppCompatTextView tv = (AppCompatTextView) mInflater.inflate(R.layout.item_category, fl_select_list, false);
                tv.setText(s);
                return tv;
            }
        };
        fl_select_list.setAdapter(adapter);
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) throws NullPointerException {
        switch (parent.getId()) {
            case R.id.sn_sunblock_brand:
                if (sunblockBrandSpinnerAdapter.getItem(position) != null)
                    sunblockId = sunblockBrandSpinnerAdapter.getItem(position).getId();
                break;
            case R.id.sn_sunblock_kind:
                values.add(sunblockKindSpinnerAdapter.getItem(position));
                rb_cost.setChecked(false);
                rb_premium.setChecked(false);
                selectListRefresh();
                break;
            case R.id.sn_glass_brand:
                if (sunblockBrandSpinnerAdapter.getItem(position) != null)
                    glassId = sunblockBrandSpinnerAdapter.getItem(position).getId();
                break;
            case R.id.sn_glass_kind:
                values.add(glassKindSpinnerAdapter.getItem(position));
                rb_cost.setChecked(false);
                rb_premium.setChecked(false);
                selectListRefresh();
                break;
            case R.id.sn_blackbox_brand:
                if (sunblockBrandSpinnerAdapter.getItem(position) != null)
                    blackBoxId = sunblockBrandSpinnerAdapter.getItem(position).getId();
                break;
            case R.id.sn_blackbox_kind:
                values.add(blackboxKindSpinnerAdapter.getItem(position));
                rb_cost.setChecked(false);
                rb_premium.setChecked(false);
                selectListRefresh();
                break;
            case R.id.sn_undercoating_brand:
                if (sunblockBrandSpinnerAdapter.getItem(position) != null)
                    underCoatingId = sunblockBrandSpinnerAdapter.getItem(position).getId();
                break;
            case R.id.sn_undercoating_kind: {
                values.add(undercoatingKindSpinnerAdapter.getItem(position));
                rb_cost.setChecked(false);
                rb_premium.setChecked(false);
                selectListRefresh();
                break;
            }
            case R.id.sn_ppf_brand:
                if (ppfBrandSpinnerAdapter.getItem(position) != null)
                    ppfId = ppfBrandSpinnerAdapter.getItem(position).getId();
                break;
            case R.id.sn_ppf_kind: {
                values.add(ppfKindSpinnerAdapter.getItem(position));
                rb_cost.setChecked(false);
                rb_premium.setChecked(false);
                selectListRefresh();
                break;
            }
            case R.id.sn_tuning_brand:
                if (tuningBrandSpinnerAdapter.getItem(position) != null)
                    tuningId = tuningBrandSpinnerAdapter.getItem(position).getId();
                break;
            case R.id.sn_tuning_kind: {
                values.add(tuningKindSpinnerAdapter.getItem(position));
                rb_cost.setChecked(false);
                rb_premium.setChecked(false);
                selectListRefresh();
                break;
            }
            case R.id.sn_tire_brand:
                if (tireBrandSpinnerAdapter.getItem(position) != null)
                    tireId = tireBrandSpinnerAdapter.getItem(position).getId();
                break;
            case R.id.sn_tire_kind: {
                values.add(tireKindSpinnerAdapter.getItem(position));
                rb_cost.setChecked(false);
                rb_premium.setChecked(false);
                selectListRefresh();
                break;
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void itemList(DataEnums.ItemDiffType itemDiffType, Integer brandId) {
        showLoading();
        ItemListRequester itemListRequester = new ItemListRequester(EstimateDepth2Activity.this);
        itemListRequester.setDiff(itemDiffType);
        itemListRequester.setItemBrandId(brandId);

        request(itemListRequester,
                success -> {
                    ItemListResponser result = (ItemListResponser) success;
                    if (result.getCode() == 200) {
                        switch (itemDiffType) {
                            case SUNBLOCK:
                                sn_sunblock_kind.setOnItemSelectedListener(null);
                                sunblockKindSpinnerAdapter.setData(result.getList().toArray(new Item[result.getList().size()]));
                                sunblockKindSpinnerAdapter.notifyDataSetChanged();
                                sn_sunblock_kind.setOnItemSelectedListener(this);
                                break;
                            case GLASS:
                                sn_glass_kind.setOnItemSelectedListener(null);
                                glassKindSpinnerAdapter.setData(result.getList().toArray(new Item[result.getList().size()]));
                                glassKindSpinnerAdapter.notifyDataSetChanged();
                                sn_glass_kind.setOnItemSelectedListener(this);
                                break;
                            case BLACKBOX:
                                sn_blackbox_kind.setOnItemSelectedListener(null);
                                blackboxKindSpinnerAdapter.setData(result.getList().toArray(new Item[result.getList().size()]));
                                blackboxKindSpinnerAdapter.notifyDataSetChanged();
                                sn_blackbox_kind.setOnItemSelectedListener(this);
                                break;
                            case UNDERCOATING:
                                sn_undercoating_kind.setOnItemSelectedListener(null);
                                undercoatingKindSpinnerAdapter.setData(result.getList().toArray(new Item[result.getList().size()]));
                                undercoatingKindSpinnerAdapter.notifyDataSetChanged();
                                sn_undercoating_kind.setOnItemSelectedListener(this);
                                break;
                            case PPF:
                                sn_ppf_kind.setOnItemSelectedListener(null);
                                ppfKindSpinnerAdapter.setData(result.getList().toArray(new Item[result.getList().size()]));
                                ppfKindSpinnerAdapter.notifyDataSetChanged();
                                sn_ppf_kind.setOnItemSelectedListener(this);
                                break;
                            case TUNING:
                                sn_tuning_kind.setOnItemSelectedListener(null);
                                tuningKindSpinnerAdapter.setData(result.getList().toArray(new Item[result.getList().size()]));
                                tuningKindSpinnerAdapter.notifyDataSetChanged();
                                sn_tuning_kind.setOnItemSelectedListener(this);
                                break;
                            case TIRE:
                                sn_tire_kind.setOnItemSelectedListener(null);
                                tireKindSpinnerAdapter.setData(result.getList().toArray(new Item[result.getList().size()]));
                                tireKindSpinnerAdapter.notifyDataSetChanged();
                                sn_tire_kind.setOnItemSelectedListener(this);
                                break;
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

    private void itemBrandList(DataEnums.ItemDiffType itemDiffType) {
        showLoading();
        ItemBrandListRequester itemBrandListRequester = new ItemBrandListRequester(EstimateDepth2Activity.this);
        itemBrandListRequester.setDiff(itemDiffType);

        request(itemBrandListRequester,
                success -> {
                    ItemBrandListResponser result = (ItemBrandListResponser) success;
                    if (result.getCode() == 200) {
                        switch (itemDiffType) {
                            case SUNBLOCK:
                                sunblockBrandSpinnerAdapter.setData(result.getList().toArray(new ItemBrand[result.getList().size()]));
                                sunblockBrandSpinnerAdapter.notifyDataSetChanged();
                                break;
                            case GLASS:
                                glassBrandSpinnerAdapter.setData(result.getList().toArray(new ItemBrand[result.getList().size()]));
                                glassBrandSpinnerAdapter.notifyDataSetChanged();
                                break;
                            case BLACKBOX:
                                blackboxBrandSpinnerAdapter.setData(result.getList().toArray(new ItemBrand[result.getList().size()]));
                                blackboxBrandSpinnerAdapter.notifyDataSetChanged();
                                break;
                            case UNDERCOATING:
                                undercoatingBrandSpinnerAdapter.setData(result.getList().toArray(new ItemBrand[result.getList().size()]));
                                undercoatingBrandSpinnerAdapter.notifyDataSetChanged();
                                break;
                            case PPF:
                                ppfBrandSpinnerAdapter.setData(result.getList().toArray(new ItemBrand[result.getList().size()]));
                                ppfBrandSpinnerAdapter.notifyDataSetChanged();
                                break;
                            case TUNING:
                                tuningBrandSpinnerAdapter.setData(result.getList().toArray(new ItemBrand[result.getList().size()]));
                                tuningBrandSpinnerAdapter.notifyDataSetChanged();
                                break;
                            case TIRE:
                                tireBrandSpinnerAdapter.setData(result.getList().toArray(new ItemBrand[result.getList().size()]));
                                tireBrandSpinnerAdapter.notifyDataSetChanged();
                                break;
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

    private void itemPackageList() {
        ItemListRequester itemListRequester = new ItemListRequester(EstimateDepth2Activity.this);
        itemListRequester.setDiff(DataEnums.ItemDiffType.PACKAGE);

        request(itemListRequester,
                success -> {
                    ItemListResponser result = (ItemListResponser) success;
                    if (result.getCode() == 200) {
                        packageValues = result.getList();
                        tv_cost.setText(packageValues.get(0).getItemBrand().getName());
                        rb_cost.setText(packageValues.get(0).getName());
                        tv_premium.setText(packageValues.get(1).getItemBrand().getName());
                        rb_premium.setText(packageValues.get(1).getName());

                    } else {
                        showServerErrorDialog(result.getResultMsg());
                    }
                }, fail -> {
                    showServerErrorDialog(fail.getResultMsg());
                });
    }
}