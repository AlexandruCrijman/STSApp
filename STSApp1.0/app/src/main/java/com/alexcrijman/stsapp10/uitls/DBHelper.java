package com.alexcrijman.stsapp10.uitls;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.alexcrijman.stsapp10.models.ProgramPlayItem;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "StsDB.db";
    public static final String TRUPE_TABLE_NAME = "trupe";
    public static final String TRUPE_COLUMN_ID = "idPlay";
    public static final String TRUPE_COLUMN_DAY = "dayPlay";
    public static final String TRUPE_COLUMN_NUME_TRUPA = "bandName";
    public static final String TRUPE_COLUMN_NUME_PIESE = "playName";
    public static final String TRUPE_COLUMN_DESCRIERE_PIESA = "description";
    public static final String TRUPE_COLUMN_ORA_PERFORM = "hourPerformence";


    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TRUPE_TABLE_NAME +
                " (" + TRUPE_COLUMN_ID + " integer primary key," +
                TRUPE_COLUMN_DAY + " integer," +
                TRUPE_COLUMN_ORA_PERFORM + " text," +
                TRUPE_COLUMN_NUME_TRUPA + " text," +
                TRUPE_COLUMN_NUME_PIESE + " text," +
                TRUPE_COLUMN_DESCRIERE_PIESA + " text" +
                ")");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {


        db.execSQL("DROP TABLE IF EXISTS trupe");
        onCreate(db);

    }

    public boolean updateTrupe(int id, int day, String ora, String numeTrupe, String numePiese, String descriere) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TRUPE_COLUMN_DAY, day);
        contentValues.put(TRUPE_COLUMN_ORA_PERFORM, ora);
        contentValues.put(TRUPE_COLUMN_NUME_TRUPA, numeTrupe);
        contentValues.put(TRUPE_COLUMN_NUME_PIESE, numePiese);
        contentValues.put(TRUPE_COLUMN_DESCRIERE_PIESA, descriere);
        db.update(TRUPE_TABLE_NAME, contentValues, "idPlay=?", new String[]{Integer.toString(id)});

        return true;
    }

    public ArrayList<ProgramPlayItem> getDataByDay(int day) {
        ArrayList<ProgramPlayItem> playItem = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = null;
        try {
            res = db.rawQuery("select * from trupe where dayPlay=" + day + " ", null);
            res.moveToFirst();
            while (res.moveToNext()) {

                ProgramPlayItem aux = new ProgramPlayItem();
                aux.setDayPlay(res.getInt(res.getColumnIndex(TRUPE_COLUMN_DAY)));
                aux.setOra(res.getString(res.getColumnIndex(TRUPE_COLUMN_ORA_PERFORM)));
                aux.setBandName(res.getString(res.getColumnIndex(TRUPE_COLUMN_NUME_TRUPA)));
                aux.setPlayName(res.getString(res.getColumnIndex(TRUPE_COLUMN_NUME_PIESE)));
                aux.setDescription(res.getString(res.getColumnIndex(TRUPE_COLUMN_DESCRIERE_PIESA)));
                playItem.add(aux);
            }
        } finally {
            res.close();
        }
        return playItem;


    }


}
