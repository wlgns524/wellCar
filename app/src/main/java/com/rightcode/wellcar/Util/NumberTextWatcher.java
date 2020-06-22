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
    private OnChangeListener listener;

    public NumberTextWatcher(EditText et, OnChangeListener listener) {
        this.et = et;
        format = new DecimalFormat("###,###");
        this.listener = listener;
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

            et.setText(format.format(n));
            endlen = et.getText().toString().length();

            int sel = (cp + (endlen - inilen));
            if (sel > 0 && sel <= et.getText().length()) {
                et.setSelection(sel);
            } else {
                et.setSelection(et.getText().length() - 1);
            }

            listener.onChange(n.intValue());

        } catch (ParseException e){
            Log.e(e.getMessage());
            listener.onChange(0);
        }

        et.addTextChangedListener(this);
    }

    public interface OnChangeListener{
        void onChange(int price);
    }
}