package com.rightcode.wellcar.Component;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import com.rightcode.wellcar.R;


public class RelativeCornerLayout  extends RelativeLayout {
    private Bitmap maskBitmap;
    private Paint paint;
    private int backgroundColor = Color.WHITE;
    private float cornerRadius;

    public RelativeCornerLayout(Context context) {
        super(context);
        init(context, null, 0);
    }

    public RelativeCornerLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public RelativeCornerLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs, defStyle);
    }

    private void init(Context context, AttributeSet attrs, int defStyle) {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        setWillNotDraw(false);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RoundedCornerLayout);

        backgroundColor = typedArray.getColor(R.styleable.RoundedCornerLayout_backgroundColor, backgroundColor);
        cornerRadius = typedArray.getDimension(R.styleable.RoundedCornerLayout_cornerRadius, cornerRadius);

        typedArray.recycle();
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        if (maskBitmap == null) {
            // This corner radius assumes the image width == height and you want it to be circular
            // Otherwise, customize the radius as needed
            int width = canvas.getWidth();
            int height = canvas.getHeight();

            if (width <= 0) {
                width = 1;
            }

            if (height <= 0) {
                height = 1;
            }

            if (cornerRadius == 0f) {
                cornerRadius = width / 2;
            }

            maskBitmap = createMask(width, height);
        }

        canvas.drawBitmap(maskBitmap, 0f, 0f, paint);
    }

    private Bitmap createMask(int width, int height) {
        Bitmap mask = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(mask);

        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(backgroundColor);

        canvas.drawRect(0, 0, width, height, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        canvas.drawRoundRect(new RectF(0, 0, width, height), cornerRadius, cornerRadius, paint);

        return mask;
    }
}