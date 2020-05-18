package com.rightcode.wellcar.Activity.Setting;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.rightcode.wellcar.Activity.BaseActivity;
import com.rightcode.wellcar.Activity.Estimate.EstimateDepth1Activity;
import com.rightcode.wellcar.Activity.Estimate.EstimateDepth2Activity;
import com.rightcode.wellcar.Adapter.SpinnerAdapter.SpinnerAdapter;
import com.rightcode.wellcar.Fragment.TopFragment;
import com.rightcode.wellcar.R;
import com.rightcode.wellcar.Util.FragmentUtil;
import com.rightcode.wellcar.Util.Log;
import com.rightcode.wellcar.Util.ToastUtil;
import com.rightcode.wellcar.network.model.request.estimate.EstimateRegister;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.rightcode.wellcar.Util.ExtraData.EXTRA_ACTIVITY_COMPLETE;
import static com.rightcode.wellcar.Util.ExtraData.EXTRA_ADDRESS_GU;
import static com.rightcode.wellcar.Util.ExtraData.EXTRA_ADDRESS_SI;
import static com.rightcode.wellcar.Util.ExtraData.EXTRA_ESTIMATE_REGISTER;

public class CarWashActivity extends BaseActivity implements Spinner.OnItemSelectedListener {

    @BindView(R.id.tv_address_si)
    TextView tv_address_si;
    @BindView(R.id.tv_address_gu)
    TextView tv_address_gu;
    @BindView(R.id.sn_address_si)
    Spinner sn_address_si;
    @BindView(R.id.sn_address_gu)
    Spinner sn_address_gu;

    private TopFragment mTopFragment;

    private SpinnerAdapter siSpinnerAdapter;
    private SpinnerAdapter guSpinnerAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_wash);

        ButterKnife.bind(this);
        initialize();
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
    // OnClick
    //------------------------------------------------------------------------------------------

    @OnClick({R.id.tv_car_wash})
    void onClickMenu(View view) {
        switch (view.getId()) {
            case R.id.tv_car_wash: {
                if (TextUtils.isEmpty(tv_address_si.getText().toString())) {
                    ToastUtil.show(CarWashActivity.this, "시를 선택해주세요");
                    break;
                }

                if (TextUtils.isEmpty(tv_address_gu.getText().toString())) {
                    ToastUtil.show(CarWashActivity.this, "구를 선택해주세요");
                    break;
                }

                Intent intent = new Intent(CarWashActivity.this, CarWashStoreListActivity.class);
                intent.putExtra(EXTRA_ADDRESS_SI, tv_address_si.getText().toString());
                intent.putExtra(EXTRA_ADDRESS_GU, tv_address_gu.getText().toString());
                startActivityForResult(intent, EXTRA_ACTIVITY_COMPLETE);
                break;
            }
        }
    }

    //------------------------------------------------------------------------------------------
    // private
    //------------------------------------------------------------------------------------------

    private void initialize() {
        mTopFragment = (TopFragment) FragmentUtil.findFragmentByTag(getSupportFragmentManager(), "TopFragment");
        mTopFragment.setText(TopFragment.Menu.CENTER, "셀프 세차장");
        mTopFragment.setImagePadding(TopFragment.Menu.CENTER, 10);
        mTopFragment.setImage(TopFragment.Menu.LEFT, R.drawable.arrow_left);
        mTopFragment.setImagePadding(TopFragment.Menu.LEFT, 5);
        mTopFragment.setListener(TopFragment.Menu.LEFT, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishWithAnim();
            }
        });

        //Spinner Adapter
        siSpinnerAdapter = new SpinnerAdapter(CarWashActivity.this);
        guSpinnerAdapter = new SpinnerAdapter(CarWashActivity.this);
        String[] area = getResources().getStringArray(R.array.AREA);
        siSpinnerAdapter.setData(area);
        //Adapter 적용
        sn_address_si.setAdapter(siSpinnerAdapter);
        sn_address_si.setOnItemSelectedListener(this);
        sn_address_gu.setAdapter(guSpinnerAdapter);
        sn_address_gu.setOnItemSelectedListener(this);
    }

    private void address(String area) {
        String[] areaDetail = null;
        switch (area) {
            case "서울특별시":
                areaDetail = getResources().getStringArray(R.array.SEOUL);
                break;
            case "경기도":
                areaDetail = getResources().getStringArray(R.array.GYEONGGI);
                break;
            case "부산광역시":
                areaDetail = getResources().getStringArray(R.array.BUSAN);
                break;
            case "대구광역시":
                areaDetail = getResources().getStringArray(R.array.DAEGU);
                break;
            case "인천광역시":
                areaDetail = getResources().getStringArray(R.array.INCHEON);
                break;
            case "광주광역시":
                areaDetail = getResources().getStringArray(R.array.GWANGJU);
                break;
            case "대전광역시":
                areaDetail = getResources().getStringArray(R.array.DAEJEON);
                break;
            case "울산광역시":
                areaDetail = getResources().getStringArray(R.array.ULSAN);
                break;
            case "강원도":
                areaDetail = getResources().getStringArray(R.array.GANGWON);
                break;
            case "경상남도":
                areaDetail = getResources().getStringArray(R.array.GYEONGNAM);
                break;
            case "경상북도":
                areaDetail = getResources().getStringArray(R.array.GYEONGBUK);
                break;
            case "전라남도":
                areaDetail = getResources().getStringArray(R.array.JEONNAM);
                break;
            case "전라북도":
                areaDetail = getResources().getStringArray(R.array.JEONBUK);
                break;
            case "충청남도":
                areaDetail = getResources().getStringArray(R.array.CHUNGNAM);
                break;
            case "충청북도":
                areaDetail = getResources().getStringArray(R.array.CHUNGBUK);
                break;
            case "제주특별자치도":
                areaDetail = getResources().getStringArray(R.array.JEJU);
                break;
        }

        guSpinnerAdapter.setData(areaDetail);
        guSpinnerAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.sn_address_si: {
                if (siSpinnerAdapter.getItem(position).toString().equals("시 선택")) {
                    tv_address_si.setText("");
                    tv_address_si.setVisibility(View.GONE);
                    tv_address_gu.setText("");
                    tv_address_gu.setVisibility(View.GONE);
                    break;
                }
                tv_address_si.setText(siSpinnerAdapter.getItem(position).toString());
                tv_address_si.setVisibility(View.VISIBLE);
                address(siSpinnerAdapter.getItem(position).toString());
                break;
            }
            case R.id.sn_address_gu: {
                if (guSpinnerAdapter.getItem(position).toString().equals("구 선택")) {
                    tv_address_gu.setText("");
                    tv_address_gu.setVisibility(View.GONE);
                    break;
                }
                tv_address_gu.setText(guSpinnerAdapter.getItem(position).toString());
                tv_address_gu.setVisibility(View.VISIBLE);
                break;
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }
}