package ru.yogago.mtronome.ui.main;

import android.database.Cursor;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import ru.yogago.mtronome.DBHelper;

public class MainViewModel extends ViewModel {
    // TODO: Implement the ViewModel
    private final MutableLiveData<User> userLiveData = new MutableLiveData<>();
    private final String LOG_TAG = "metronomeLog";
    private MainFragment mainFragment;
    private DBHelper myDb;
    private Timer timer;
    private int countSecond;
    private int min;
    private int sec;
    private int sound;

    public MainViewModel() {
        super();
        Log.d(LOG_TAG, "MainViewModel: " + this.hashCode());
    }

    public void setFragment(MainFragment mainFragment) {
        this.mainFragment = mainFragment;
    }

    public LiveData<User> getUser() {
        return userLiveData;
    }

    public void dataBaseInit(){
        // создаем объект для создания и управления версиями БД
        myDb = new DBHelper(this.mainFragment.getContext());

        updateAllUserData();

    }

    public void updateAllUserData() {
        Log.d(LOG_TAG, "--- Rows in mytable: ---");
        Cursor res = myDb.getAllData();
        if (res.getCount() == 0) {
            Log.d(LOG_TAG, "Error. Nothing found \n");
            return;
        }
        while(res.moveToNext()){
            int id = Integer.parseInt(res.getString(0));
            this.countSecond = Integer.parseInt(res.getString(1));
            this.min = Integer.parseInt(res.getString(2));
            this.sec = Integer.parseInt(res.getString(3));
            this.sound = Integer.parseInt(res.getString(4));
            Log.d(LOG_TAG, "getAllData: id: " + id + " countSecond: " + countSecond + " min: " + min + " sec: " + sec + " sound: " + sound);
        }
    }

    public void setDbMinSec(int min, int sec){
        boolean isUpdate = myDb.updateData(1, min, sec, this.countSecond);
        if (isUpdate) Log.d(LOG_TAG, "isUpdate: " + isUpdate);
        else Log.d(LOG_TAG, "Error. isUpdate: " + isUpdate);
    }



    void doAction() {
        if(this.timer == null) {
            this.timer = new Timer(this, this.countSecond);
            this.timer.setMainViewModel(this);
            timer.execute();
        }
        timer.resume();
    }

    public void stopAction() {
        if (this.timer != null) {
            this.timer.stop();
            this.timer = null;
        }
    }

    public void restartAction(){
        if (this.timer != null) {
            this.timer.stop();
            this.timer = null;
        }
        this.min = this.mainFragment.getViewMin();
        this.sec = this.mainFragment.getViewSec();
        this.countSecond = min*60 + sec;
        setDbMinSec(this.mainFragment.getViewMin(), this.mainFragment.getViewSec());

        setViewTimer("" + this.countSecond);
    }
    @Override
    protected void onCleared() {
        super.onCleared();
        Log.d(LOG_TAG, "MainViewModel onCleared: " + this.hashCode());
    }

    public void setViewTimer(String time) {
        this.mainFragment.setViewTimer(time);

    }

    public void setCountSecond(int countSecond) {
        Log.d(LOG_TAG, "MainViewModel setCountSecond: " + countSecond);
        this.countSecond = countSecond;
    }

    public int getCountSecond() {
        return countSecond;
    }

    public int getSound() {
        return this.sound;
    }

    public int getMin() {
        return min;
    }

    public int getSec() {
        return sec;
    }

}
