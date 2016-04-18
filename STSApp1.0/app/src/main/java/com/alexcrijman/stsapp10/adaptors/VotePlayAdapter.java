package com.alexcrijman.stsapp10.adaptors;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.alexcrijman.stsapp10.R;
import com.alexcrijman.stsapp10.models.TheatrePlays;

import java.util.ArrayList;

public class VotePlayAdapter extends BaseAdapter {

    private static final String TAG = VotePlayAdapter.class.getSimpleName();
    private Context lCntx;
    private ArrayList<TheatrePlays> lplayListItem = new ArrayList<>();

    public VotePlayAdapter(Context lCntx, ArrayList<TheatrePlays> lplayListItem) {
        this.lCntx = lCntx;
        this.lplayListItem = lplayListItem;
    }

    public void updateData(ArrayList<TheatrePlays> newValues) {
        lplayListItem = newValues;
    }

    @Override
    public int getCount() {
        return lplayListItem.size();
    }

    @Override
    public Object getItem(int position) {
        return lplayListItem.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        TheatrePlays playItem = lplayListItem.get(position);
        ViewHolder holder;
        LayoutInflater inflater;

        if (view == null) {
            holder = new ViewHolder();
            inflater = (LayoutInflater) lCntx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            view = inflater.inflate(R.layout.play_item_listv, parent, false);

            holder.bandName = (TextView) view.findViewById(R.id.vot_item_band_name_TextView);
            holder.playName = (TextView) view.findViewById(R.id.vot_item_play_name_TextView);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.bandName.setText(playItem.getBandName());
        holder.playName.setText(playItem.getPlayName());
        holder.idPlay = playItem.getIdPlay();

        return view;

    }

    private class ViewHolder {

        TextView bandName;
        TextView playName;
        int idPlay;
    }
}
