package ru.yogago.metronome;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import ru.yogago.mtronome.R;

public class MainActivity extends AppCompatActivity {

    private final String LOG_TAG = "metronomeLog";
    private ActionBar actionBar;
    private MainFragment mainFragment;
    private AboutFragment aboutFragment;
    private SettingsFragment settingsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        if (savedInstanceState == null) {
            this.mainFragment = MainFragment.newInstance();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, this.mainFragment)
                    .commitNow();
        }
        this.actionBar = getSupportActionBar();
        this.aboutFragment = new AboutFragment();
        this.settingsFragment = new SettingsFragment();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.home:
                replaceFragment(this.mainFragment);
                return true;
            case R.id.about:
                replaceFragment(this.aboutFragment);
//                Intent intent = new Intent(this, AboutActivity.class);
//                startActivity(intent);
                return true;
            case R.id.settings:
                replaceFragment(this.settingsFragment);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void replaceFragment(Fragment fragment) {
        FragmentTransaction  transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

}
