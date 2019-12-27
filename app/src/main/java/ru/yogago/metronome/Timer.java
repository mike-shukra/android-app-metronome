package ru.yogago.metronome;

import android.os.AsyncTask;
import android.util.Log;

import java.util.concurrent.TimeUnit;

class Timer extends AsyncTask<String, Integer, Void> {

    private final String LOG_TAG = "metronomeLogTimer";
    private MainViewModel mainViewModel;
    private boolean isStop;
    private int countSecond;

    Timer(MainViewModel mainViewModel, int countSecond) {
        this.mainViewModel = mainViewModel;
        this.countSecond = countSecond;
        this.isStop = true;

    }
    @Override
    protected Void doInBackground(String... strings) {
        try {
            for (int i = this.countSecond; i >= 0; i--) {
                TimeUnit.SECONDS.sleep(1);
                if (isStop) break;
                publishProgress(i);
                this.mainViewModel.setCountSecond(i);
                Log.d(LOG_TAG, "i = " + i + ", Timer: " + this.hashCode() + ", mainViewModel: " + mainViewModel.hashCode());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        if (!this.mainViewModel.setViewTimer(values[0].toString())) this.isStop = true;
    }
    @Override
    protected void onPostExecute(Void result) {
        super.onPostExecute(result);
    }
    @Override
    protected void onCancelled() {
        super.onCancelled();
    }
    void resume() {
        this.isStop = false;
    }

    void stop() {
        this.cancel(false);
        this.isStop = true;
    }

    void setMainViewModel(MainViewModel mainViewModel) {
        this.mainViewModel = mainViewModel;
    }



}

