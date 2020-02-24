package com.rightcode.wellcar.Component;


import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RatingBar;


import com.rightcode.wellcar.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SmallRatingStarLayout extends LinearLayout {

    @BindView(R.id.review_write_evaluation_item_rb_star)
    RatingBar review_write_evaluation_item_rb_star;

    private RatingBar.OnRatingBarChangeListener listener = null;

    public SmallRatingStarLayout(Context context) {
        this(context, null);
    }

    public SmallRatingStarLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SmallRatingStarLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View view = inflate(getContext(), R.layout.view_rating_small_star, null);
        initialize(view);
        ButterKnife.bind(this);
    }

    private void initialize(View v) {
        setOrientation(LinearLayout.VERTICAL);
        setBackgroundResource(R.color.white);
        setPadding(0, 0, 0, 0);

        addView(v);
    }

    public void setRating(float rating) {
        review_write_evaluation_item_rb_star.setRating(rating);
    }

    public void setRatingListener(RatingBar.OnRatingBarChangeListener listener) {
        review_write_evaluation_item_rb_star.setIsIndicator(false);
        review_write_evaluation_item_rb_star.setOnRatingBarChangeListener(listener);
    }

    public float getRating() {
        return review_write_evaluation_item_rb_star.getRating();
    }
}
