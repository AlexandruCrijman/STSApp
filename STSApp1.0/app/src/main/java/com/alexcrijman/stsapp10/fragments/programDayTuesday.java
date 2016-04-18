package com.alexcrijman.stsapp10.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.alexcrijman.stsapp10.R;
import com.alexcrijman.stsapp10.adaptors.ProgramListAdaptor;
import com.alexcrijman.stsapp10.models.ProgramPlayItem;
import com.alexcrijman.stsapp10.uitls.DialogProgram;

import java.util.ArrayList;


public class programDayTuesday extends Fragment implements AdapterView.OnItemClickListener {

    private static final String TAG = programDayTuesday.class.getSimpleName();
    private ListView lPlayToVoteLV = null;
    private ProgramListAdaptor lProgramAdapter = null;
    private ArrayList<ProgramPlayItem> lListPlayProgram = new ArrayList<>();
    private boolean once = false;
    private View view;
    private FragmentActivity myContext;

    public programDayTuesday() {


    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_program_day_tuesday, container, false);

        lPlayToVoteLV = (ListView) view.findViewById(R.id.program_marti_lv);
        lProgramAdapter = new ProgramListAdaptor(getActivity().getApplicationContext(), lListPlayProgram);
         /*??? cu getApllicaton Context*/
        lPlayToVoteLV.setAdapter(lProgramAdapter);
        lPlayToVoteLV.setOnItemClickListener(this);

        if (once == false) {
            setItems();
            once = true;
        }


        return view;
    }

    @Override
    public void onAttach(Context context) {
        myContext = (FragmentActivity) context;

        super.onAttach(context);
    }


    private void setItems() {

        ProgramPlayItem aux = new ProgramPlayItem("19:00  20:00");
        aux.setBandName(getResources().getString(R.string.day2_0_band_name));
        aux.setPlayName(getResources().getString(R.string.day2_0_play_name));
        lListPlayProgram.add(aux);
        ProgramPlayItem aux1 = new ProgramPlayItem("20:30  21:30");
        aux1.setBandName(getResources().getString(R.string.day2_1_band_name));
        aux1.setPlayName(myContext.getString(R.string.day2_1_play_name));
        lListPlayProgram.add(aux1);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Bundle args = new Bundle();
        switch (position) {

            case 0:
                args.putString("bandName", getResources().getString(R.string.day2_0_band_name));
                args.putString("playName", getResources().getString(R.string.day2_0_play_name));
                args.putString("description", getResources().getString(R.string.day2_0_description));
                Log.e(TAG, "Am intrat pe  0");

                break;
            case 1:
                args.putString("bandName", getResources().getString(R.string.day2_1_band_name));
                args.putString("playName", getResources().getString(R.string.day2_1_play_name));
                args.putString("description", getResources().getString(R.string.day2_1_description));
                Log.e(TAG, "Am intrat pe  0");

                break;


        }
        FragmentManager fm = myContext.getSupportFragmentManager();
        DialogProgram mDialogFragProg = new DialogProgram();
        mDialogFragProg.setArguments(args);
        mDialogFragProg.show(fm, "dialog_program");


    }
}

