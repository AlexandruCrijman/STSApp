package com.alexcrijman.stsapp10.uitls;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

public class Utils {

    public static boolean isInternetON(Context cntx) {
        ConnectivityManager conManager = (ConnectivityManager) cntx.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = conManager.getActiveNetworkInfo();
        return networkInfo == null ? false : networkInfo.isConnected();


    }

    public static boolean emailValidation(final EditText emailEt) {
        final boolean[] bool = {false};
        final String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        final String email = emailEt.getText().toString();
        emailEt.addTextChangedListener(new TextWatcher() {


            public void afterTextChanged(Editable s) {

                bool[0] = email.matches(emailPattern) && s.length() > 0;
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // other stuffs
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // other stuffs
            }
        });
        return bool[0];
    }

}
