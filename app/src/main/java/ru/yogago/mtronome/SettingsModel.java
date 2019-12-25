package ru.yogago.mtronome;

import android.database.Cursor;
import android.util.Log;

import androidx.lifecycle.ViewModel;

public class SettingsModel extends ViewModel {
    private final String LOG_TAG = "metronomeLog";
    private SettingsFragment settingsFragment;
    private DBHelper myDb;
    private int sound;

    public SettingsModel() {
        super();
    }

    public void setFragment(SettingsFragment settingsFragment) {
        this.settingsFragment = settingsFragment;
    }

    public void dataBaseInit(){
        // создаем объект для создания и управления версиями БД
        myDb = new DBHelper(this.settingsFragment.getContext());

        updateData();

    }

    private void updateData() {
        Cursor res = myDb.getAllData();
        if (res.getCount() == 0) {
            Log.d(LOG_TAG, "Error. Nothing found \n");
            return;
        }
        while(res.moveToNext()){
            int id = Integer.parseInt(res.getString(0));
            this.sound = Integer.parseInt(res.getString(4));
            Log.d(LOG_TAG, "updateData: id: " + id + " sound: " + sound);
        }
    }

    public int getSound() {
        return this.sound;
    }

    public void setDbSound(int sound){
        boolean isUpdate = myDb.updateData(1, sound);
        Log.d(LOG_TAG, "isUpdate sound: " + isUpdate);
    }

}
