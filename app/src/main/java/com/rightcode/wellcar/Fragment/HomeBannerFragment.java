package com.rightcode.wellcar.Fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.rightcode.wellcar.Activity.EventDetailActivity;
import com.rightcode.wellcar.R;
import com.rightcode.wellcar.Util.Log;
import com.rightcode.wellcar.network.model.response.event.Event;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.rightcode.wellcar.Util.ExtraData.EXTRA_BANNER_DATA;
import static com.rightcode.wellcar.Util.ExtraData.EXTRA_EVENT_ID;
import static com.rightcode.wellcar.Util.ExtraData.EXTRA_IMAGE;

public class HomeBannerFragment extends BaseFragment implements View.OnClickListener {

    @BindView(R.id.iv_home_banner)
    ImageView iv_home_banner;

    private Event data;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.item_home_banner_viewpager, container, false);

        ButterKnife.bind(this, rootView);

        Bundle extra = getArguments();
        data = (Event) extra.getSerializable(EXTRA_BANNER_DATA);
        Glide.with(rootView.getContext())
                .load(data.getThumbnail())
                .into(iv_home_banner);

        rootView.setOnClickListener(this);
        return rootView;
    }

    @Override
    public void onClick(View v) {
        if (TextUtils.isEmpty(data.getUrl())) {
            Intent intent = new Intent(getContext(), EventDetailActivity.class);
            intent.putExtra(EXTRA_EVENT_ID, data.getId());
            startActivity(intent);
        } else {
            if (data.getUrl().startsWith("http://") || data.getUrl().startsWith("https://")) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(data.getUrl()));
                startActivity(intent);
            } else {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://" + data.getUrl()));
                startActivity(intent);
            }
        }
    }
}
