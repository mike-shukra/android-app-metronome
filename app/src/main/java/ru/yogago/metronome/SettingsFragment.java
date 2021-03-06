package ru.yogago.metronome;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

public class SettingsFragment extends Fragment {

    private SettingsModel settingsModel;
    private int sound;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.settings, container, false);
        this.settingsModel = ViewModelProviders.of(this).get(SettingsModel.class);
        this.settingsModel.setFragment(this);
        this.settingsModel.dataBaseInit();
        RadioGroup soundCheck = (RadioGroup) v.findViewById(R.id.soundCheck);
        RadioButton radioSound1 = (RadioButton) v.findViewById(R.id.radioSound1);
        RadioButton radioSound2 = (RadioButton) v.findViewById(R.id.radioSound2);
        RadioButton radioSound3 = (RadioButton) v.findViewById(R.id.radioSound3);
        RadioButton radioSound4 = (RadioButton) v.findViewById(R.id.radioSound4);
        RadioButton radioSound5 = (RadioButton) v.findViewById(R.id.radioSound5);

        switch (this.settingsModel.getSound()){
            case 1:
                soundCheck.check(R.id.radioSound1);
                this.sound = 1;
                break;
            case 2:
                soundCheck.check(R.id.radioSound2);
                this.sound = 2;
                break;
            case 3:
                soundCheck.check(R.id.radioSound3);
                this.sound = 3;
                break;
            case 4:
                soundCheck.check(R.id.radioSound4);
                this.sound = 4;
                break;
            case 5:
                soundCheck.check(R.id.radioSound5);
                this.sound = 5;
                break;
        }

        radioSound1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                radioSoundClicked(1);
            }
        });
        radioSound2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                radioSoundClicked(2);
            }
        });
        radioSound3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                radioSoundClicked(3);
            }
        });
        radioSound4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                radioSoundClicked(4);
            }
        });
        radioSound5.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                radioSoundClicked(5);
            }
        });

        int lastCheckedId = soundCheck.getCheckedRadioButtonId();
        switch (lastCheckedId) {
            case R.id.radioSound1:
                this.sound = 1;
                break;
            case R.id.radioSound2:
                this.sound = 2;
                break;
            case R.id.radioSound3:
                this.sound = 3;
                break;
            case R.id.radioSound4:
                this.sound = 4;
                break;
            case R.id.radioSound5:
                this.sound = 5;
                break;
        }
        return v;
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    private void radioSoundClicked(int sound){
        switch(sound) {
            case 1:
                this.sound = 1;
                    break;
            case 2:
                this.sound = 2;
                    break;
            case 3:
                this.sound = 3;
                    break;
            case 4:
                this.sound = 4;
                    break;
            case 5:
                this.sound = 5;
                    break;
        }
    }

    @Override
    public void onDestroyView() {
        this.settingsModel.setDbSound(this.sound);
        super.onDestroyView();
    }
}