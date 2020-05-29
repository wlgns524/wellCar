package com.rightcode.wellcar.Util;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.ParseException;

public class NumberTextWatcher implements TextWatcher {

    private DecimalFormat format;
    private EditText et;
    private TextView tv;
    private long price;
    private boolean hasFractionalPart;

    public NumberTextWatcher(EditText et, TextView tv, int price) {
        this.et = et;
        this.tv = tv;
        this.price = price;
        format = new DecimalFormat("###,###");
        Log.d(price);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        et.removeTextChangedListener(this);

        try{
            int inilen, endlen;
            inilen = et.getText().length();

            String v = s.toString().replace(String.valueOf(format.getDecimalFormatSymbols().getGroupingSeparator()), "");
            Number n = format.parse(v);
            int cp = et.getSelectionStart();
            long price = this.price + (long)n;

            et.setText(format.format(n));
            tv.setText(format.format(price));
            endlen = et.getText().toString().length();

            int sel = (cp + (endlen - inilen));
            if (sel > 0 && sel <= et.getText().length()) {
                et.setSelection(sel);
            } else {
                et.setSelection(et.getText().length() - 1);
            }

        } catch (ParseException e){
            Log.e(e.getMessage());
            tv.setText(format.format(price));
        }

        et.addTextChangedListener(this);
    }
}