package com.alexcrijman.stsapp10.adaptors;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.alexcrijman.stsapp10.R;
import com.alexcrijman.stsapp10.models.ProgramPlayItem;

import java.util.ArrayList;


public class ProgramListAdaptor extends BaseAdapter {

    private static final String TAG = ProgramListAdaptor.class.getSimpleName();
    private Context lCntx;
    private ArrayList<ProgramPlayItem> lprogramPlayList = new ArrayList<>();


    public ProgramListAdaptor(Context lCntx, ArrayList<ProgramPlayItem> lprogramPlayList) {
        this.lCntx = lCntx;
        this.lprogramPlayList = lprogramPlayList;
    }


    public void updateData(ArrayList<ProgramPlayItem> newValues) {

        lprogramPlayList = newValues;
    }

    @Override
    public int getCount() {
        return lprogramPlayList.size();
    }

    @Override
    public Object getItem(int position) {
        return lprogramPlayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        ProgramPlayItem programItem = lprogramPlayList.get(position);
        ViewHolder holder;
        LayoutInflater inflater;

        if (view == null) {
            holder = new ViewHolder();
            inflater = (LayoutInflater) lCntx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            view = inflater.inflate(R.layout.program_item, parent, false);

            holder.ora = (TextView) view.findViewById(R.id.program_ora_tv_item);
            holder.bandName = (TextView) view.findViewById(R.id.program_trupa_tv_item);
            holder.playName = (TextView) view.findViewById(R.id.program_piesa_tv_item);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.bandName.setText(programItem.getBandName());
        holder.ora.setText(programItem.getOra());
        holder.playName.setText(programItem.getPlayName());

        return view;
    }

    private class ViewHolder {

        TextView ora;
        TextView bandName;
        TextView playName;


    }
}
