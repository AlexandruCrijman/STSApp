package com.alexcrijman.stsapp10.uitls;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.widget.EditText;

public class EditTextModif extends EditText {


    public EditTextModif(Context context) {
        super(context);
    }

    public EditTextModif(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public EditTextModif(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    public boolean onKeyPreIme(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {

            clearFocus();


        }
        return super.onKeyPreIme(keyCode, event);
    }

}
