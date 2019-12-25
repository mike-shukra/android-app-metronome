package ru.yogago.mtronome;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import ru.yogago.mtronome.ui.main.MainFragment;

public class MainActivity extends AppCompatActivity {

    private final String LOG_TAG = "metronomeLog";
    private ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, MainFragment.newInstance())
                    .commitNow();
        }
        this.actionBar = getSupportActionBar();
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
            case R.id.settings:
                replaceFragment(new SettingsFragment());
                return true;
            case R.id.about:
                replaceFragment(new AboutFragment());
//                Intent intent = new Intent(this, AboutActivity.class);
//                startActivity(intent);
                return true;
            case R.id.home:
                replaceFragment(new MainFragment());
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
//        String stringId = fragment.getTitleStringId();
//        actionBar.setTitle(R.string.stringId);

    }

}
