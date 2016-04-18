package com.alexcrijman.stsapp10.uitls;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alexcrijman.stsapp10.R;


public class DialogProgram extends DialogFragment {


    private TextView mBandNameTV;
    private TextView mPlayNameTV;
    private TextView mDescriptionTV;
    private Button mDialogBtn;

    public DialogProgram() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.dialog_program, container, false);
        setCancelable(true);
        mBandNameTV = (TextView) view.findViewById(R.id.dialog_bandName);
        mPlayNameTV = (TextView) view.findViewById(R.id.dialog_playName);
        mDescriptionTV = (TextView) view.findViewById(R.id.dialog_description);
        mDialogBtn = (Button) view.findViewById(R.id.dialog_confirm_btn);
        mDialogBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        Bundle mArgs = getArguments();
        String bandName = mArgs.getString("bandName");
        String playName = mArgs.getString("playName");
        String description = mArgs.getString("description");
        mBandNameTV.setText(bandName);
        mPlayNameTV.setText(playName);
        mDescriptionTV.setText(description);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {


    }


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LinearLayout root = new LinearLayout(getActivity());
        root.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(root);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        return dialog;
    }
}
