package com.rightcode.wellcar.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.widget.DatePicker;
import android.widget.TextView;


import com.rightcode.wellcar.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DatePickerDialog extends Dialog {

    @BindView(R.id.date_picker)
    DatePicker datePicker;
    @BindView(R.id.tv_ok)
    TextView tvOk;

    DatePickerListener listener;
    int yyyy, mm, dd;
    public DatePickerDialog(Context context, DatePickerListener listener) {
        super(context);
        setContentView(R.layout.dialog_date_picker);
        ButterKnife.bind(this);

        datePicker.init(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth(), (view, year, monthOfYear, dayOfMonth) -> {
            yyyy = year;
            mm = monthOfYear;
            dd = dayOfMonth;
        });

        this.listener = listener;
    }
    @OnClick(R.id.tv_ok)
    public void onViewClicked() {
        if(yyyy < 2000){
            yyyy = datePicker.getYear();
            mm = datePicker.getMonth();
            dd = datePicker.getDayOfMonth();
        }
        listener.okButton(yyyy, mm, dd);
        dismiss();
    }

    public interface DatePickerListener{
        void okButton(int yyyy, int mm, int dd);
    }
}
