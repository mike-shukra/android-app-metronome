package ru.yogago.metronome;


import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import ru.yogago.mtronome.R;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainFragment extends Fragment implements SoundPool.OnLoadCompleteListener {

    private final String LOG_TAG = "metronomeLog";
    private MainViewModel mViewModel;
    private TextView timerView;
    private EditText countMinView;
    private EditText countSecView;

    private int sound;
    private SoundPool sp;
    private int soundIdShot;
    private final int MAX_STREAMS = 5;

    public void onStart() {
        super.onStart();
//        MainViewModel mainViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
//        Log.d(LOG_TAG, "MainFragment mViewModel: " + mViewModel.getUser());
    }

    public static MainFragment newInstance() {
        return new MainFragment();
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(LOG_TAG, "onCreateView: " + this.hashCode());
        View v = inflater.inflate(R.layout.main_fragment, container, false);

        this.timerView = (TextView) v.findViewById(R.id.timer);
        this.countMinView = (EditText) v.findViewById(R.id.min);
        this.countSecView = (EditText) v.findViewById(R.id.sec);
        Button buttonStart = (Button) v.findViewById(R.id.buttonStart);
        buttonStart.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d(LOG_TAG, "buttonStart: " + this.hashCode());
                mViewModel.doAction();
            }
        });
        Button buttonStop = (Button) v.findViewById(R.id.buttonStop);
        buttonStop.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d(LOG_TAG, "buttonStop: " + this.hashCode());
                mViewModel.stopAction();
            }
        });
        Button buttonRestart = (Button) v.findViewById(R.id.buttonRestart);
        buttonRestart.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d(LOG_TAG, "buttonRestart: " + this.hashCode());
                mViewModel.restartAction();
            }
        });
        return v;
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        // TODO: Use the ViewModel
        Log.d(LOG_TAG, "MainFragment onActivityCreated: " + this.hashCode());
        this.mViewModel.setFragment(this);
        this.mViewModel.dataBaseInit();
        this.sound = this.mViewModel.getSound();
        switch (sound) {
            case 1:
                this.sound = R.raw.metronomsound01;
                break;
            case 2:
                this.sound = R.raw.metronomsound02;
                break;
            case 3:
                this.sound = R.raw.metronomsound03;
                break;
            case 4:
                this.sound = R.raw.metronomsound04;
                break;
            case 5:
                this.sound = R.raw.metronomsound05;
                break;
        }
        sp = new SoundPool(MAX_STREAMS, AudioManager.STREAM_MUSIC, 0);
        sp.setOnLoadCompleteListener(this);
        this.soundIdShot = sp.load(this.getContext(), this.sound, 1);

        setViewTimer("" + this.mViewModel.getCountSecond());
        countMinView.setText(String.valueOf(this.mViewModel.getMin()));
        countSecView.setText(String.valueOf(this.mViewModel.getSec()));
    }

    public void setViewTimer(String time) {
        this.timerView.setText(time);
        this.sp.play(this.soundIdShot, 1, 1, 0, 0, 1);
        Log.d(LOG_TAG, "sp: " + sp.hashCode());


    }
    @Override
    public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
//        Log.d(LOG_TAG, "onLoadComplete, sampleId = " + sampleId + ", status = " + status);
    }

    public int getViewMin(){
        return Integer.parseInt(countMinView.getText().toString());
    }
    public int getViewSec(){
        return Integer.parseInt(countSecView.getText().toString());
    }
}
