package ru.yogago.metronome;

import android.database.Cursor;

import androidx.lifecycle.ViewModel;

public class MainViewModel extends ViewModel {
    // TODO: Implement the ViewModel
//    private final MutableLiveData<User> userLiveData = new MutableLiveData<>();
//    private final String LOG_TAG = "metronomeLog";
    private MainFragment mainFragment;
    private DBHelper myDb;
    private Timer timer;
    private int countSecond;
    private int min;
    private int sec;
    private int sound;

    public MainViewModel() {
        super();
    }

    void setFragment(MainFragment mainFragment) {
        this.mainFragment = mainFragment;
    }

//    public LiveData<User> getUser() {
//        return userLiveData;
//    }

    void dataBaseInit(){
        myDb = new DBHelper(this.mainFragment.getContext());

        updateAllUserData();

    }

    private void updateAllUserData() {
        Cursor res = myDb.getAllData();
        if (res.getCount() == 0) {
            return;
        }
        while(res.moveToNext()){
            int id = Integer.parseInt(res.getString(0));
            this.countSecond = Integer.parseInt(res.getString(1));
            this.min = Integer.parseInt(res.getString(2));
            this.sec = Integer.parseInt(res.getString(3));
            this.sound = Integer.parseInt(res.getString(4));
//            Log.d(LOG_TAG, "getAllData: id: " + id + " countSecond: " + countSecond + " min: " + min + " sec: " + sec + " sound: " + sound);
        }
    }

    private void setDbMinSec(int min, int sec){
        myDb.updateData(1, min, sec, this.countSecond);
//        if (isUpdate) Log.d(LOG_TAG, "isUpdate: " + isUpdate);
//        else Log.d(LOG_TAG, "Error. isUpdate: " + isUpdate);
    }

    void doAction() {
        if(this.timer == null) {
            this.timer = new Timer(this, this.countSecond);
            this.timer.setMainViewModel(this);
            timer.execute();
        }
        timer.resume();
    }

    void stopAction() {
        if (this.timer != null) {
            this.timer.stop();
            this.timer = null;
        }
    }

    void restartAction(){
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
    }

    boolean setViewTimer(String time) {
    return this.mainFragment.setViewTimer(time);
    }

    void setCountSecond(int countSecond) {
        this.countSecond = countSecond;
    }

    int getCountSecond() {
        return countSecond;
    }

    int getSound() {
        return this.sound;
    }

    int getMin() {
        return min;
    }

    int getSec() {
        return sec;
    }

}
