package com.rightcode.wellcar.Activity.Estimate;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.viewpager.widget.ViewPager;

import com.rd.PageIndicatorView;
import com.rightcode.wellcar.Activity.BaseActivity;
import com.rightcode.wellcar.Adapter.SpinnerAdapter.ItemBrandSpinnerAdapter;
import com.rightcode.wellcar.Adapter.SpinnerAdapter.ItemSpinnerAdapter;
import com.rightcode.wellcar.Adapter.ViewPagerAdapter.HomeBannerViewPagerAdapter;
import com.rightcode.wellcar.Component.CustomViewPager;
import com.rightcode.wellcar.Fragment.TopFragment;
import com.rightcode.wellcar.MemberManager;
import com.rightcode.wellcar.R;
import com.rightcode.wellcar.Util.DataEnums;
import com.rightcode.wellcar.Util.FragmentUtil;
import com.rightcode.wellcar.Util.Log;
import com.rightcode.wellcar.Util.ToastUtil;
import com.rightcode.wellcar.network.model.request.estimate.EstimateRegister;
import com.rightcode.wellcar.network.model.response.item.Item;
import com.rightcode.wellcar.network.model.response.itemBrand.ItemBrand;
import com.rightcode.wellcar.network.requester.event.EventListRequester;
import com.rightcode.wellcar.network.requester.item.ItemListRequester;
import com.rightcode.wellcar.network.requester.itemBrand.ItemBrandListRequester;
import com.rightcode.wellcar.network.responser.event.EventListResponser;
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
import butterknife.OnItemClick;
import butterknife.OnItemSelected;
import butterknife.OnTouch;

import static com.rightcode.wellcar.Util.DataEnums.ItemDiffType.BLACKBOX;
import static com.rightcode.wellcar.Util.DataEnums.ItemDiffType.COST_PACKAGE;
import static com.rightcode.wellcar.Util.DataEnums.ItemDiffType.GLASS;
import static com.rightcode.wellcar.Util.DataEnums.ItemDiffType.PPF;
import static com.rightcode.wellcar.Util.DataEnums.ItemDiffType.PREMIUM_PACKAGE;
import static com.rightcode.wellcar.Util.DataEnums.ItemDiffType.SUNBLOCK;
import static com.rightcode.wellcar.Util.DataEnums.ItemDiffType.TIRE;
import static com.rightcode.wellcar.Util.DataEnums.ItemDiffType.TUNING;
import static com.rightcode.wellcar.Util.DataEnums.ItemDiffType.UNDERCOATING;
import static com.rightcode.wellcar.Util.ExtraData.EXTRA_ACTIVITY_COMPLETE;
import static com.rightcode.wellcar.Util.ExtraData.EXTRA_ESTIMATE_REGISTER;
import static com.rightcode.wellcar.Util.ExtraData.EXTRA_ITEMS;

public class EstimateDepth2Activity extends BaseActivity implements Spinner.OnItemSelectedListener, ViewPager.OnPageChangeListener {


    @BindView(R.id.ll_cost)
    LinearLayout ll_cost;
    @BindView(R.id.ll_premium)
    LinearLayout ll_premium;
    @BindView(R.id.tv_cost)
    TextView tv_cost;
    @BindView(R.id.rb_cost)
    CheckBox rb_cost;
    @BindView(R.id.tv_premium)
    TextView tv_premium;
    @BindView(R.id.rb_premium)
    CheckBox rb_premium;
    @BindView(R.id.cv_event)
    CustomViewPager cv_event;
    @BindView(R.id.pageindicator)
    PageIndicatorView pageindicator;
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
    @BindView(R.id.et_request)
    EditText et_request;


    @BindView(R.id.ll_sunblock)
    LinearLayout ll_sunblock;
    @BindView(R.id.rl_sunblock_brand)
    RelativeLayout rl_sunblock_brand;
    @BindView(R.id.rl_sunblock_kind)
    RelativeLayout rl_sunblock_kind;
    @BindView(R.id.ll_glass)
    LinearLayout ll_glass;
    @BindView(R.id.rl_glass_brand)
    RelativeLayout rl_glass_brand;
    @BindView(R.id.rl_glass_kind)
    RelativeLayout rl_glass_kind;
    @BindView(R.id.ll_blackbox)
    LinearLayout ll_blackbox;
    @BindView(R.id.rl_blackbox_brand)
    RelativeLayout rl_blackbox_brand;
    @BindView(R.id.rl_blackbox_kind)
    RelativeLayout rl_blackbox_kind;
    @BindView(R.id.ll_undercoating)
    LinearLayout ll_undercoating;
    @BindView(R.id.rl_undercoating_brand)
    RelativeLayout rl_undercoating_brand;
    @BindView(R.id.rl_undercoating_kind)
    RelativeLayout rl_undercoating_kind;
    @BindView(R.id.ll_ppf)
    LinearLayout ll_ppf;
    @BindView(R.id.rl_ppf_brand)
    RelativeLayout rl_ppf_brand;
    @BindView(R.id.rl_ppf_kind)
    RelativeLayout rl_ppf_kind;
    @BindView(R.id.ll_tuning)
    LinearLayout ll_tuning;
    @BindView(R.id.rl_tuning_brand)
    RelativeLayout rl_tuning_brand;
    @BindView(R.id.rl_tuning_kind)
    RelativeLayout rl_tuning_kind;
    @BindView(R.id.ll_tire)
    LinearLayout ll_tire;
    @BindView(R.id.rl_tire_brand)
    RelativeLayout rl_tire_brand;
    @BindView(R.id.rl_tire_kind)
    RelativeLayout rl_tire_kind;
    @BindView(R.id.sn_cost_package_kind)
    Spinner sn_cost_package_kind;
    @BindView(R.id.rl_cost_package_kind)
    RelativeLayout rl_cost_package_kind;
    @BindView(R.id.sn_premium_package_kind)
    Spinner sn_premium_package_kind;
    @BindView(R.id.rl_premium_package_kind)
    RelativeLayout rl_premium_package_kind;


    private TopFragment mTopFragment;
    private TagAdapter adapter;
    private EstimateRegister register;
    private LayoutInflater mInflater;
    private ArrayList<Item> values = new ArrayList<>();
    private ArrayList<Item> packageValues = new ArrayList<>();
    private ArrayList<Item> costPackageValues = new ArrayList<>();
    private ArrayList<Item> premiumPackageValues = new ArrayList<>();

    private HomeBannerViewPagerAdapter mHomeBannerViewPagerAdapter;
    private Boolean sublockFlag = false;
    private Boolean glassFlag = false;
    private Boolean blackBoxFlag = false;
    private Boolean underCoatingFlag = false;
    private Boolean ppfFlag = false;
    private Boolean tuningFlag = false;
    private Boolean tireFlag = false;
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
    private ItemSpinnerAdapter costPackageSpinnerAdapter;
    private ItemSpinnerAdapter premiumPackageSpinnerAdapter;
    private Toast mToast;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estimate_depth2);

        ButterKnife.bind(this);
        initialize();
//        eventList();
        costPackageList();
        premiumPackageList();
        itemList(SUNBLOCK);
        itemList(GLASS);
        itemList(BLACKBOX);
        itemList(UNDERCOATING);
        itemList(PPF);
        itemList(TUNING);
        itemList(TIRE);
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

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        pageindicator.setSelected(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

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

        //event Adapter
        mHomeBannerViewPagerAdapter = new HomeBannerViewPagerAdapter(getSupportFragmentManager(), EstimateDepth2Activity.this);
        cv_event.setAdapter(mHomeBannerViewPagerAdapter);
        cv_event.addOnPageChangeListener(this);

        // Flow Layout
        mInflater = LayoutInflater.from(EstimateDepth2Activity.this);
        fl_select_list.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                String value = String.format("%s(%s)", values.get(position).getName(), values.get(position).getItemBrand().getName());
                Log.d(value);
                if (value.equals("LX+블랙박스(아이나비)+PPF3종+유리막(썬팅+블랙박스+유리막+PPF)")) {
                    sn_cost_package_kind.setSelection(0);
                    rb_cost.setChecked(false);
                }
                if (value.equals("LX+블랙박스(아이나비)+PPF5종+언더코팅+유리막(썬팅+블랙박스+유리막+언더코팅+PPF)")) {
                    sn_premium_package_kind.setSelection(0);
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
        costPackageSpinnerAdapter = new ItemSpinnerAdapter(EstimateDepth2Activity.this);
        premiumPackageSpinnerAdapter = new ItemSpinnerAdapter(EstimateDepth2Activity.this);
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
        //패키지
        sn_cost_package_kind.setOnItemSelectedListener(this);
        sn_cost_package_kind.setAdapter(costPackageSpinnerAdapter);
        sn_premium_package_kind.setOnItemSelectedListener(this);
        sn_premium_package_kind.setAdapter(premiumPackageSpinnerAdapter);

    }


    @OnCheckedChanged({R.id.rb_cost, R.id.rb_premium})
    void onGenderSelected(CompoundButton button, boolean checked) {
        if (checked) {
            switch (button.getId()) {
                case R.id.rb_cost: {
                    rb_cost.setChecked(true);
                    rb_premium.setChecked(false);
                    costPackageSpinnerAdapter.setData(costPackageValues.toArray(new Item[costPackageValues.size()]));
                    costPackageSpinnerAdapter.notifyDataSetChanged();
                    break;
                }
                case R.id.rb_premium: {
                    rb_cost.setChecked(false);
                    rb_premium.setChecked(true);
                    premiumPackageSpinnerAdapter.setData(premiumPackageValues.toArray(new Item[premiumPackageValues.size()]));
                    premiumPackageSpinnerAdapter.notifyDataSetChanged();
                    break;
                }
            }
            selectListRefresh();
        }
    }

    View.OnTouchListener touchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            return false;
        }
    };


    @OnTouch({R.id.rl_sunblock_brand, R.id.rl_sunblock_kind, R.id.rl_glass_brand, R.id.rl_glass_kind,
            R.id.rl_blackbox_brand, R.id.rl_blackbox_kind, R.id.rl_undercoating_brand, R.id.rl_undercoating_kind,
            R.id.rl_ppf_brand, R.id.rl_ppf_kind, R.id.rl_tuning_brand, R.id.rl_tuning_kind, R.id.rl_tire_brand, R.id.rl_tire_kind,
            R.id.sn_sunblock_kind, R.id.sn_glass_kind, R.id.sn_blackbox_kind, R.id.sn_undercoating_kind, R.id.sn_ppf_kind,
            R.id.sn_tuning_kind, R.id.sn_tire_kind, R.id.sn_cost_package_kind, R.id.sn_premium_package_kind})
    void onTouchMenu(View view) {
        switch (view.getId()) {
            case R.id.rl_sunblock_brand: {
                if (rb_cost.isChecked() || rb_premium.isChecked()) {
                    if(mToast == null){
                        mToast = ToastUtil.show(EstimateDepth2Activity.this, "패키지 상품 포함 시 선택하실 수 없습니다.");
                    } else {
                        mToast.setText("패키지 상품 포함 시 선택하실 수 없습니다.");
                        mToast.show();
                    }
                    break;
                }
                itemBrandList(SUNBLOCK);
                break;
            }
            case R.id.rl_sunblock_kind:
            case R.id.sn_sunblock_kind: {
                if (rb_cost.isChecked() || rb_premium.isChecked()) {
                    if(mToast == null){
                        mToast = ToastUtil.show(EstimateDepth2Activity.this, "패키지 상품 포함 시 선택하실 수 없습니다.");
                    } else {
                        mToast.setText("패키지 상품 포함 시 선택하실 수 없습니다.");
                        mToast.show();
                    }
                    break;
                }
                if (sunblockId != null)
                    itemList(SUNBLOCK, sunblockId);
                break;
            }
            case R.id.rl_glass_brand: {
                itemBrandList(GLASS);
                break;
            }
            case R.id.rl_glass_kind:
            case R.id.sn_glass_kind: {
                if (glassId != null)
                    itemList(GLASS, glassId);
                break;
            }
            case R.id.rl_blackbox_brand: {
                if (rb_cost.isChecked() || rb_premium.isChecked()) {
                    if(mToast == null){
                        mToast = ToastUtil.show(EstimateDepth2Activity.this, "패키지 상품 포함 시 선택하실 수 없습니다.");
                    } else {
                        mToast.setText("패키지 상품 포함 시 선택하실 수 없습니다.");
                        mToast.show();
                    }
                    break;
                }
                itemBrandList(BLACKBOX);
                break;
            }
            case R.id.rl_blackbox_kind:
            case R.id.sn_blackbox_kind: {
                if (rb_cost.isChecked() || rb_premium.isChecked()) {
                    if(mToast == null){
                        mToast = ToastUtil.show(EstimateDepth2Activity.this, "패키지 상품 포함 시 선택하실 수 없습니다.");
                    } else {
                        mToast.setText("패키지 상품 포함 시 선택하실 수 없습니다.");
                        mToast.show();
                    }
                    break;
                }
                if (blackBoxId != null)
                    itemList(BLACKBOX, blackBoxId);
                break;
            }
            case R.id.rl_undercoating_brand: {
                itemBrandList(UNDERCOATING);
                break;
            }
            case R.id.rl_undercoating_kind:
            case R.id.sn_undercoating_kind: {
                if (underCoatingId != null)
                    itemList(UNDERCOATING, underCoatingId);
                break;
            }
            case R.id.rl_ppf_brand: {
                itemBrandList(PPF);
                break;
            }
            case R.id.rl_ppf_kind:
            case R.id.sn_ppf_kind: {
                if (ppfId != null)
                    itemList(PPF, ppfId);
                break;
            }
            case R.id.rl_tuning_brand: {
                itemBrandList(TUNING);
                break;
            }
            case R.id.rl_tuning_kind:
            case R.id.sn_tuning_kind: {
                if (tuningId != null)
                    itemList(TUNING, tuningId);
                break;
            }
            case R.id.rl_tire_brand: {
                itemBrandList(TIRE);
                break;
            }
            case R.id.rl_tire_kind:
            case R.id.sn_tire_kind: {
                if (tireId != null)
                    itemList(TIRE, tireId);
                break;
            }

            case R.id.sn_cost_package_kind:{
                sn_cost_package_kind.performClick();
                break;
            }

            case R.id.sn_premium_package_kind: {
                sn_premium_package_kind.performClick();
                break;
            }


        }
    }

    @OnClick({R.id.tv_estimate})
    void onClickMenu(View view) {
        switch (view.getId()) {
            case R.id.tv_estimate: {
                Intent intent = new Intent(EstimateDepth2Activity.this, EstimateDepth3Activity.class);
                if (packageValues != null && packageValues.size() > 0 && rb_cost.isChecked()) {
                    register.setItemIds(new ArrayList<Integer>(
                            Arrays.asList(packageValues.get(0).getId())));
                } else if (packageValues != null && packageValues.size() > 1 && rb_premium.isChecked()) {
                    register.setItemIds(new ArrayList<Integer>(
                            Arrays.asList(packageValues.get(1).getId())));
                } else {
                    if (packageValues != null && packageValues.size() > 0)
                        values.remove(packageValues.get(0));
                    if (packageValues != null && packageValues.size() > 1)
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
                register.setRequest(et_request.getText().toString());
                intent.putExtra(EXTRA_ESTIMATE_REGISTER, register);
                intent.putExtra(EXTRA_ITEMS, values);
                startActivityForResult(intent, EXTRA_ACTIVITY_COMPLETE);
                break;
            }
        }
    }

    private void selectListRefresh() {
        ArrayList<String> overlapList = new ArrayList<>();
        int size = 0;
        //중복 제거 반복문
        for (Item item : values) {
            if (!overlapList.contains(String.format("%s(%s) X", item.getName(), item.getItemBrand().getName())))
                if(item.getDiff() != COST_PACKAGE && item.getDiff() != PREMIUM_PACKAGE) {
                    overlapList.add(String.format("%s(%s) X", item.getName(), item.getItemBrand().getName()));
                } else {
                    overlapList.add(String.format("%s(%s) X", item.getName(), item.getDiff().toString()));
                }
        }

        // 체크되어있지 않을 시 제거
        if (costPackageValues != null && costPackageValues.size() > 0 && !rb_cost.isChecked()) {
            String cost = String.format("%s(%s) X", costPackageValues.get(0).getName(), COST_PACKAGE.toString());
            overlapList.remove(cost);
        }

        if (premiumPackageValues != null && premiumPackageValues.size() > 1 && !rb_premium.isChecked()) {
            String premium = String.format("%s(%s) X", premiumPackageValues.get(1).getName(), PREMIUM_PACKAGE.toString());
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
                    if (!sunblockBrandSpinnerAdapter.getItem(position).getName().equals("선택없음(브랜드)")) {
                        sunblockId = sunblockBrandSpinnerAdapter.getItem(position).getId();
                    } else {
                        sunblockKindSpinnerAdapter.setData(null);
                        sunblockKindSpinnerAdapter.notifyDataSetChanged();
                    }
                break;
            case R.id.sn_sunblock_kind:
                if (sublockFlag) {
                    if (!sunblockKindSpinnerAdapter.getItem(position).getName().equals("선택없음(종류)")) {
                        deduplication(sunblockKindSpinnerAdapter.getItem(position));
                        values.add(sunblockKindSpinnerAdapter.getItem(position));
                        selectListRefresh();
                    }
                } else {
                    sublockFlag = true;
                }
                break;
            case R.id.sn_glass_brand:
                if (glassBrandSpinnerAdapter.getItem(position) != null)
                    if (!glassBrandSpinnerAdapter.getItem(position).getName().equals("선택없음(브랜드)")) {
                        glassId = glassBrandSpinnerAdapter.getItem(position).getId();
                    } else {
                        glassKindSpinnerAdapter.setData(null);
                        glassKindSpinnerAdapter.notifyDataSetChanged();
                    }
                break;
            case R.id.sn_glass_kind:
                if (glassFlag) {
                    if (!glassKindSpinnerAdapter.getItem(position).getName().equals("선택없음(종류)")) {
                        deduplication(glassKindSpinnerAdapter.getItem(position));
                        values.add(glassKindSpinnerAdapter.getItem(position));
                        selectListRefresh();
                    }
                } else {
                    glassFlag = true;
                }
                break;
            case R.id.sn_blackbox_brand:
                if (blackboxBrandSpinnerAdapter.getItem(position) != null)
                    if (!blackboxBrandSpinnerAdapter.getItem(position).getName().equals("선택없음(브랜드)")) {
                        blackBoxId = blackboxBrandSpinnerAdapter.getItem(position).getId();
                    } else {
                        blackboxKindSpinnerAdapter.setData(null);
                        blackboxKindSpinnerAdapter.notifyDataSetChanged();
                    }
                break;
            case R.id.sn_blackbox_kind:
                if (blackBoxFlag) {
                    if (!blackboxKindSpinnerAdapter.getItem(position).getName().equals("선택없음(종류)")) {
                        deduplication(blackboxKindSpinnerAdapter.getItem(position));
                        values.add(blackboxKindSpinnerAdapter.getItem(position));
                        selectListRefresh();
                    }
                } else {
                    blackBoxFlag = true;
                }
                break;
            case R.id.sn_undercoating_brand:
                if (undercoatingBrandSpinnerAdapter.getItem(position) != null)
                    if (!undercoatingBrandSpinnerAdapter.getItem(position).getName().equals("선택없음(브랜드)")) {
                        underCoatingId = undercoatingBrandSpinnerAdapter.getItem(position).getId();
                    } else {
                        undercoatingKindSpinnerAdapter.setData(null);
                        undercoatingKindSpinnerAdapter.notifyDataSetChanged();
                    }
                break;
            case R.id.sn_undercoating_kind: {
                if (underCoatingFlag) {
                    if (!undercoatingKindSpinnerAdapter.getItem(position).getName().equals("선택없음(종류)")) {
                        values.add(undercoatingKindSpinnerAdapter.getItem(position));
                        selectListRefresh();
                    }
                } else {
                    underCoatingFlag = true;
                }
                break;
            }
            case R.id.sn_ppf_brand:
                if (ppfBrandSpinnerAdapter.getItem(position) != null)
                    if (!ppfBrandSpinnerAdapter.getItem(position).getName().equals("선택없음(브랜드)")) {
                        ppfId = ppfBrandSpinnerAdapter.getItem(position).getId();
                    } else {
                        ppfKindSpinnerAdapter.setData(null);
                        ppfKindSpinnerAdapter.notifyDataSetChanged();
                    }
                break;
            case R.id.sn_ppf_kind: {
                if (ppfFlag) {
                    if (!ppfKindSpinnerAdapter.getItem(position).getName().equals("선택없음(종류)")) {
                        values.add(ppfKindSpinnerAdapter.getItem(position));
                        rb_cost.setChecked(false);
                        rb_premium.setChecked(false);
                        selectListRefresh();
                    }
                } else {
                    ppfFlag = true;
                }
                break;
            }
            case R.id.sn_tuning_brand:
                if (tuningBrandSpinnerAdapter.getItem(position) != null)
                    if (!tuningBrandSpinnerAdapter.getItem(position).getName().equals("선택없음(브랜드)")) {
                        tuningId = tuningBrandSpinnerAdapter.getItem(position).getId();
                    } else {
                        tuningKindSpinnerAdapter.setData(null);
                        tuningKindSpinnerAdapter.notifyDataSetChanged();
                    }
                break;
            case R.id.sn_tuning_kind: {
                if (tuningFlag) {
                    if (!tuningKindSpinnerAdapter.getItem(position).getName().equals("선택없음(종류)")) {
                        deduplication(tuningKindSpinnerAdapter.getItem(position));
                        values.add(tuningKindSpinnerAdapter.getItem(position));
                        selectListRefresh();
                    }
                } else {
                    tuningFlag = true;
                }
                break;
            }
            case R.id.sn_tire_brand:
                if (tireBrandSpinnerAdapter.getItem(position) != null)
                    if (!tireBrandSpinnerAdapter.getItem(position).getName().equals("선택없음(브랜드)")) {
                        tireId = tireBrandSpinnerAdapter.getItem(position).getId();
                    } else {
                        tireKindSpinnerAdapter.setData(null);
                        tireKindSpinnerAdapter.notifyDataSetChanged();
                    }
                break;
            case R.id.sn_tire_kind: {
                if (tireFlag) {
                    if (!tireKindSpinnerAdapter.getItem(position).getName().equals("선택없음(종류)")) {
                        deduplication(tireKindSpinnerAdapter.getItem(position));
                        values.add(tireKindSpinnerAdapter.getItem(position));
                        selectListRefresh();
                    }
                } else {
                    tireFlag = true;
                }
                break;
            }

            case R.id.sn_cost_package_kind: {
                if(!costPackageSpinnerAdapter.getItem(position).getName().equals("선택없음(세부옵션)")){
                    if(!rb_cost.isChecked()){
                        rb_cost.setChecked(true);
                    }
                    values.add(costPackageSpinnerAdapter.getItem(position));
                    removeBlackBoxAndSunblockOfItemList();
                    selectListRefresh();
                }
                break;
            }

            case R.id.sn_premium_package_kind: {
                if(!premiumPackageSpinnerAdapter.getItem(position).getName().equals("선택없음(세부옵션)")){
                    if(!rb_premium.isChecked()){
                        rb_premium.setChecked(false);
                    }
                    values.add(premiumPackageValues.get(position));
                    removeBlackBoxAndSunblockOfItemList();
                    selectListRefresh();
                }
                break;
            }
        }
    }

    private void deduplication(Item item) {
        Item removeItem = null;
        for (Item i : values) {
            if (item.getDiff() == i.getDiff()) {
                removeItem = i;
            }
        }

        values.remove(removeItem);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void removeBlackBoxAndSunblockOfItemList(){
        ArrayList<Item> listRemove = new ArrayList<>();

        for(Item item : values){
            if(item.getDiff() == BLACKBOX || item.getDiff() == SUNBLOCK){
                listRemove.add(item);
            }
        }

        if(listRemove.size() > 0) {
            for (Item itemRemove : listRemove) {
                values.remove(itemRemove);
            }
        }

        selectListRefresh();
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
                                result.getList().add(0, new Item("선택없음(종류)"));
                                sunblockKindSpinnerAdapter.setData(result.getList().toArray(new Item[result.getList().size()]));
                                sunblockKindSpinnerAdapter.notifyDataSetChanged();
                                sn_sunblock_kind.performClick();
                                break;
                            case GLASS:
                                result.getList().add(0, new Item("선택없음(종류)"));
                                glassKindSpinnerAdapter.setData(result.getList().toArray(new Item[result.getList().size()]));
                                glassKindSpinnerAdapter.notifyDataSetChanged();
                                sn_glass_kind.performClick();
                                break;
                            case BLACKBOX:
                                result.getList().add(0, new Item("선택없음(종류)"));
                                blackboxKindSpinnerAdapter.setData(result.getList().toArray(new Item[result.getList().size()]));
                                blackboxKindSpinnerAdapter.notifyDataSetChanged();
                                sn_blackbox_kind.performClick();
                                break;
                            case UNDERCOATING:
                                result.getList().add(0, new Item("선택없음(종류)"));
                                undercoatingKindSpinnerAdapter.setData(result.getList().toArray(new Item[result.getList().size()]));
                                undercoatingKindSpinnerAdapter.notifyDataSetChanged();
                                break;
                            case PPF:
                                result.getList().add(0, new Item("선택없음(종류)"));
                                ppfKindSpinnerAdapter.setData(result.getList().toArray(new Item[result.getList().size()]));
                                ppfKindSpinnerAdapter.notifyDataSetChanged();
                                sn_ppf_kind.performClick();
                                break;
                            case TUNING:
                                result.getList().add(0, new Item("선택없음(종류)"));
                                tuningKindSpinnerAdapter.setData(result.getList().toArray(new Item[result.getList().size()]));
                                tuningKindSpinnerAdapter.notifyDataSetChanged();
                                sn_tuning_kind.performClick();
                                break;
                            case TIRE:
                                result.getList().add(0, new Item("선택없음(종류)"));
                                tireKindSpinnerAdapter.setData(result.getList().toArray(new Item[result.getList().size()]));
                                tireKindSpinnerAdapter.notifyDataSetChanged();
                                sn_tire_kind.performClick();
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

    private void itemList(DataEnums.ItemDiffType itemDiffType) {
        showLoading();
        ItemBrandListRequester itemBrandListRequester = new ItemBrandListRequester(EstimateDepth2Activity.this);
        itemBrandListRequester.setDiff(itemDiffType);
        request(itemBrandListRequester,
                success -> {
                    ItemBrandListResponser result = (ItemBrandListResponser) success;
                    if (result.getCode() == 200) {
                        switch (itemDiffType) {
                            case PPF: {
                                if (result.getList() != null && result.getList().size() > 0) {
                                    ll_ppf.setVisibility(View.VISIBLE);
                                    rl_ppf_brand.setVisibility(View.VISIBLE);
                                    rl_ppf_kind.setVisibility(View.VISIBLE);
                                } else {
                                    ll_ppf.setVisibility(View.GONE);
                                    rl_ppf_brand.setVisibility(View.GONE);
                                    rl_ppf_kind.setVisibility(View.GONE);
                                }
                                break;
                            }
                            case TIRE: {
                                if (result.getList() != null && result.getList().size() > 0) {
                                    ll_tire.setVisibility(View.VISIBLE);
                                    rl_tire_brand.setVisibility(View.VISIBLE);
                                    rl_tire_kind.setVisibility(View.VISIBLE);
                                } else {
                                    ll_tire.setVisibility(View.GONE);
                                    rl_tire_brand.setVisibility(View.GONE);
                                    rl_tire_kind.setVisibility(View.GONE);
                                }
                                break;
                            }
                            case GLASS: {
                                if (result.getList() != null && result.getList().size() > 0) {
                                    ll_glass.setVisibility(View.VISIBLE);
                                    rl_glass_brand.setVisibility(View.VISIBLE);
                                    rl_glass_kind.setVisibility(View.VISIBLE);
                                } else {
                                    ll_glass.setVisibility(View.GONE);
                                    rl_glass_brand.setVisibility(View.GONE);
                                    rl_glass_kind.setVisibility(View.GONE);
                                }
                                break;
                            }
                            case TUNING: {
                                if (result.getList() != null && result.getList().size() > 0) {
                                    ll_tuning.setVisibility(View.VISIBLE);
                                    rl_tuning_brand.setVisibility(View.VISIBLE);
                                    rl_tuning_kind.setVisibility(View.VISIBLE);
                                } else {
                                    ll_tuning.setVisibility(View.GONE);
                                    rl_tuning_brand.setVisibility(View.GONE);
                                    rl_tuning_kind.setVisibility(View.GONE);
                                }
                                break;
                            }
                            case BLACKBOX: {
                                if (result.getList() != null && result.getList().size() > 0) {
                                    ll_blackbox.setVisibility(View.VISIBLE);
                                    rl_blackbox_brand.setVisibility(View.VISIBLE);
                                    rl_blackbox_kind.setVisibility(View.VISIBLE);
                                } else {
                                    ll_blackbox.setVisibility(View.GONE);
                                    rl_blackbox_brand.setVisibility(View.GONE);
                                    rl_blackbox_kind.setVisibility(View.GONE);
                                }
                                break;
                            }
                            case SUNBLOCK: {
                                if (result.getList() != null && result.getList().size() > 0) {
                                    ll_sunblock.setVisibility(View.VISIBLE);
                                    rl_sunblock_brand.setVisibility(View.VISIBLE);
                                    rl_sunblock_kind.setVisibility(View.VISIBLE);
                                } else {
                                    ll_sunblock.setVisibility(View.GONE);
                                    rl_sunblock_brand.setVisibility(View.GONE);
                                    rl_sunblock_kind.setVisibility(View.GONE);
                                }
                                break;
                            }
                            case UNDERCOATING: {
                                if (result.getList() != null && result.getList().size() > 0) {
                                    ll_undercoating.setVisibility(View.VISIBLE);
                                    rl_undercoating_brand.setVisibility(View.VISIBLE);
                                    rl_undercoating_kind.setVisibility(View.VISIBLE);
                                } else {
                                    ll_undercoating.setVisibility(View.GONE);
                                    rl_undercoating_brand.setVisibility(View.GONE);
                                    rl_undercoating_kind.setVisibility(View.GONE);
                                }
                                break;
                            }
                        }
                    }
                    hideLoading();
                }, fail -> {
                    hideLoading();
                });
    }

    private void itemBrandList(DataEnums.ItemDiffType itemDiffType) {
        showLoading();
        ItemBrandListRequester itemBrandListRequester = new ItemBrandListRequester(EstimateDepth2Activity.this);
        itemBrandListRequester.setDiff(itemDiffType);
        itemBrandListRequester.setRandom(false);

        request(itemBrandListRequester,
                success -> {
                    ItemBrandListResponser result = (ItemBrandListResponser) success;
                    if (result.getCode() == 200) {
                        switch (itemDiffType) {
                            case SUNBLOCK:
                                sublockFlag = false;
                                result.getList().add(0, new ItemBrand("선택없음(브랜드)"));
                                sunblockBrandSpinnerAdapter.setData(result.getList().toArray(new ItemBrand[result.getList().size()]));
                                sunblockBrandSpinnerAdapter.notifyDataSetChanged();
                                break;
                            case GLASS:
                                glassFlag = false;
                                result.getList().add(0, new ItemBrand("선택없음(브랜드)"));
                                glassBrandSpinnerAdapter.setData(result.getList().toArray(new ItemBrand[result.getList().size()]));
                                glassBrandSpinnerAdapter.notifyDataSetChanged();
                                break;
                            case BLACKBOX:
                                blackBoxFlag = false;
                                result.getList().add(0, new ItemBrand("선택없음(브랜드)"));
                                blackboxBrandSpinnerAdapter.setData(result.getList().toArray(new ItemBrand[result.getList().size()]));
                                blackboxBrandSpinnerAdapter.notifyDataSetChanged();
                                break;
                            case UNDERCOATING:
                                underCoatingFlag = false;
                                result.getList().add(0, new ItemBrand("선택없음(브랜드)"));
                                undercoatingBrandSpinnerAdapter.setData(result.getList().toArray(new ItemBrand[result.getList().size()]));
                                undercoatingBrandSpinnerAdapter.notifyDataSetChanged();
                                break;
                            case PPF:
                                ppfFlag = false;
                                result.getList().add(0, new ItemBrand("선택없음(브랜드)"));
                                ppfBrandSpinnerAdapter.setData(result.getList().toArray(new ItemBrand[result.getList().size()]));
                                ppfBrandSpinnerAdapter.notifyDataSetChanged();
                                break;
                            case TUNING:
                                tuningFlag = false;
                                result.getList().add(0, new ItemBrand("선택없음(브랜드)"));
                                tuningBrandSpinnerAdapter.setData(result.getList().toArray(new ItemBrand[result.getList().size()]));
                                tuningBrandSpinnerAdapter.notifyDataSetChanged();
                                break;
                            case TIRE:
                                tireFlag = false;
                                result.getList().add(0, new ItemBrand("선택없음(브랜드)"));
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

    private void costPackageList(){
        ItemListRequester itemListRequester = new ItemListRequester(EstimateDepth2Activity.this);
        itemListRequester.setDiff(COST_PACKAGE);

        request(itemListRequester,
                success -> {
                    ItemListResponser result = (ItemListResponser) success;
                    if (result.getCode() == 200) {
                        Item item = result.getList().get(0);
                        costPackageValues.add(item);
                        costPackageValues.add(0, new Item("선택없음(세부옵션)"));
                        ll_cost.setVisibility(View.VISIBLE);
//                        tv_premium.setText(item.getItemBrand().getName());
                        rb_cost.setText(item.getItemBrand().getName());
                    } else {
                        showServerErrorDialog(result.getResultMsg());
                    }
                }, fail -> {
                    showServerErrorDialog(fail.getResultMsg());
                });
    }

    private void premiumPackageList(){
        ItemListRequester itemListRequester = new ItemListRequester(EstimateDepth2Activity.this);
        itemListRequester.setDiff(PREMIUM_PACKAGE);

        request(itemListRequester,
                success -> {
                    ItemListResponser result = (ItemListResponser) success;
                    if (result.getCode() == 200) {
                        Item item = result.getList().get(0);
                        premiumPackageValues.add(item);
                        premiumPackageValues.add(0, new Item("선택없음(세부옵션)"));
                        ll_premium.setVisibility(View.VISIBLE);
//                        tv_premium.setText(item.getItemBrand().getName());
                        rb_premium.setText(item.getItemBrand().getName());
                    } else {
                        showServerErrorDialog(result.getResultMsg());
                    }
                }, fail -> {
                    showServerErrorDialog(fail.getResultMsg());
                });
    }

    private void eventList() {
        showLoading();
        EventListRequester eventListRequester = new EventListRequester(EstimateDepth2Activity.this);
        eventListRequester.setLatitude(MemberManager.getInstance(EstimateDepth2Activity.this).getLocation().getLatitude());
        eventListRequester.setLongitude(MemberManager.getInstance(EstimateDepth2Activity.this).getLocation().getLongitude());

        request(eventListRequester,
                success -> {
                    EventListResponser result = (EventListResponser) success;
                    if (result.getCode() == 200) {
                        pageindicator.setCount(result.getList().size());
                        mHomeBannerViewPagerAdapter.setData(result.getList());
                        mHomeBannerViewPagerAdapter.notifyDataSetChanged();
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