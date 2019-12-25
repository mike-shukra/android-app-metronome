package ru.yogago.metronome;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    private final String LOG_TAG = "metronomeLog";
    public final static String DATABASE_NAME = "userValue.db";
    public final static String TABLE_NAME = "data_table";
    public final static String COL_1 = "ID";
    public final static String COL_2 = "countSecond";
    public final static String COL_3 = "min";
    public final static String COL_4 = "sec";
    public final static String COL_5 = "sound";

    public DBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(LOG_TAG, "--- onCreate database ---");
        db.execSQL("create table " + TABLE_NAME + " (ID integer primary key autoincrement, countSecond integer, min integer, sec integer, sound integer)");
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, 300);
        contentValues.put(COL_3, 5);
        contentValues.put(COL_4, 0);
        contentValues.put(COL_5, 2);
        db.insert(TABLE_NAME, null, contentValues);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(int countSecond, int min, int sec, int sound){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, countSecond);
        contentValues.put(COL_3, min);
        contentValues.put(COL_4, sec);
        contentValues.put(COL_5, sound);
        long result = db.insert(TABLE_NAME, null, contentValues);
        if (result == -1) return false;
        else return true;
    }

    public Cursor getAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
    }

    public boolean updateData(int id, int countSecond, int min, int sec, int sound) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1, id);
        contentValues.put(COL_2, countSecond);
        contentValues.put(COL_3, min);
        contentValues.put(COL_4, sec);
        contentValues.put(COL_5, sound);
        db.update(TABLE_NAME, contentValues, "ID = ?", new String[] {String.valueOf(id)});

        return true;
    }

    public boolean updateData(int id, int min, int sec, int countSecond) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1, id);
        contentValues.put(COL_2, countSecond);
        contentValues.put(COL_3, min);
        contentValues.put(COL_4, sec);
        db.update(TABLE_NAME, contentValues, "ID = ?", new String[] {String.valueOf(id)});

        return true;
    }

    public boolean updateData(int id, int sound) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1, id);
        contentValues.put(COL_5, sound);
        db.update(TABLE_NAME, contentValues, "ID = ?", new String[] {String.valueOf(id)});

        return true;
    }

    public Integer deleteData(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "ID = ?", new String[] {String.valueOf(id)});
    }
}
