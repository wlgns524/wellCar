package com.rightcode.wellcar.Dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import com.rightcode.wellcar.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.functions.Action1;

public class CommonDialog extends BaseDialog implements DialogInterface.OnCancelListener{


    @BindView(R.id.tv_dialog_title)
    TextView tv_dialog_title;
    @BindView(R.id.tv_dialog_message)
    TextView tv_dialog_message;
    @BindView(R.id.btn_dialog_left)
    Button btn_dialog_left;
    @BindView(R.id.btn_dialog_right)
    Button btn_dialog_right;

    private OnCancelListener onCancelListener;

    public CommonDialog(final Context context) {
        super(context, false);
        setContentView(R.layout.dialog_common);
        ButterKnife.bind(this);

        super.setOnCancelListener(this);
    }

    public void setTitle(String text) {
        tv_dialog_title.setText(text);
        tv_dialog_title.setVisibility(View.VISIBLE);
    }

    public void setMessage(String text) {
        tv_dialog_message.setText(text);
        tv_dialog_message.setVisibility(View.VISIBLE);
    }

    public void setPositiveButton(String text,  Action1<Void> action) {
        btn_dialog_right.setVisibility(View.VISIBLE);
        btn_dialog_right.setText(text);
        btn_dialog_right.setOnClickListener(v -> {
            dismiss();
            if (action != null) {
                action.call(null);
            }
        });
    }

    public void setNegativeButton(String text,  Action1<Void> action) {
        btn_dialog_left.setVisibility(View.VISIBLE);
        btn_dialog_left.setText(text);
        btn_dialog_left.setOnClickListener(v -> {
            dismiss();
            if (action != null) {
                action.call(null);
            }
        });
    }

    @Override
    public void setOnCancelListener(final OnCancelListener listener) {
        this.onCancelListener = listener;
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        if (this.onCancelListener == null) {
            if (btn_dialog_left != null && btn_dialog_left.getVisibility() == View.VISIBLE) {
                btn_dialog_left.performClick();
            }
        } else {
            this.onCancelListener.onCancel(dialog);
        }
    }
}
