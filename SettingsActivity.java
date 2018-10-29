package com.myanime.rayyan.raylab.myanime;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.myanime.rayyan.raylab.myanime.Utils.MenuActions;
import com.rayyan.como.ran.R;

public class SettingsActivity extends AppCompatActivity {


    private static final String MY_PREFS_NAME = "MyPref";
    private Switch mSwitch;
    private TextView mSwitchStatus;
    private LinearLayout mClearCache;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        getSupportActionBar().setTitle("Settings");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mSwitch = findViewById(R.id.lite_mode_switch);
        mClearCache = findViewById(R.id.clear_cache);
        mSwitchStatus = findViewById(R.id.lite_mode_status_tv);


        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        boolean mModeOn = prefs.getBoolean("lite_mode", false);

        Log.d("Orange", mModeOn + "  banana");


        if (mModeOn){
            mSwitchStatus.setText("Mode : On");
        }else{
            mSwitchStatus.setText("Mode: Off");
        }

        mSwitch.setChecked(mModeOn);



        mClearCache.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MenuActions.deleteCache(SettingsActivity.this);
                Toast.makeText(SettingsActivity.this, "Cache Cleared", Toast.LENGTH_SHORT).show();
            }
        });

        mSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                SharedPreferences.Editor editor = SettingsActivity.this.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                editor.putBoolean("lite_mode", b);
                editor.apply();

                if (b){
                    mSwitchStatus.setText("Mode : On");
                }else{
                    mSwitchStatus.setText("Mode: Off");
                }

            }
        });
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case android.R.id.home :
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
