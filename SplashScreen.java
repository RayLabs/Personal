package app.rayyan.com.bcma.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import app.rayyan.com.bcma.myapplication.Utils.URLHelper;
import io.fabric.sdk.android.Fabric;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SplashScreen extends AppCompatActivity {

    private static final String MY_PREFS_NAME = "MyPref1";
    public static final String MAKKAH_LIVE_KEY = "makkah_live";
    public static final String MADINAH_LIVE_KEY = "madinah_live";
    private TextView mQuranVerseTv;
    private String mCurrentQuranVerse;
    private String mFormattedString = "";
    private FirebaseRemoteConfig mFirebaseRemoteConfig;
    private long cacheExpiration = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_splash_screen);

        if(isFirstTime()){
            //Todo: User is a new User!!!!!
            Intent inten2 = new Intent(SplashScreen.this, DefSelectActivity.class);
            startActivity(inten2);

        }else {

          setUpQuranVerse();

            if(!isNetworkAvailable()){
                //Todo: User does not have a internet connection

                Intent intent = new Intent(SplashScreen.this, Main_Activity.class);
                startActivity(intent);

            } else {

                mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
                mFirebaseRemoteConfig.setDefaults(R.xml.remote_config_defaults);

                mFirebaseRemoteConfig.fetch(cacheExpiration)
                        .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {

                                    // After config data is successfully fetched, it must be activated before newly fetched
                                    // values are returned.
                                    mFirebaseRemoteConfig.activateFetched();
                                } else {
                                }
                                displayWelcomeMessage();
                            }
                        });

                if (mFormattedString.contains("/")) {

                    SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
                    int userCurrentBranchID = prefs.getInt("userCurrentLocationID", 2);


                    new StartServerRequest(0).execute(URLHelper.prayerServerUrlGen(mFormattedString, userCurrentBranchID));

                } else {
                    Date currentTime = Calendar.getInstance().getTime();
                    String mFormattedDate = String.valueOf(DateFormat.format("yyyy/MM/dd", currentTime));


                    SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
                    int userCurrentBranchID = prefs.getInt("userCurrentLocationID", 2);


                    Log.d("Rayyan", userCurrentBranchID + "  ~~ Current Branch User Location");
                    Log.d("Rayyan", mFormattedDate + "  ~~ Current User Formatted Date");

                    new StartServerRequest(0).execute(URLHelper.prayerServerUrlGen(mFormattedDate, userCurrentBranchID));

                }
            }
        }

    }

    private void displayWelcomeMessage() {
       String mMakkahLiveUrl = mFirebaseRemoteConfig.getString(MAKKAH_LIVE_KEY);
       String mMadinahLiveUrl = mFirebaseRemoteConfig.getString(MADINAH_LIVE_KEY);
        SharedPreferences.Editor editor2 = SplashScreen.this.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();

        editor2.putString(MAKKAH_LIVE_KEY, mMakkahLiveUrl);
        editor2.putString(MADINAH_LIVE_KEY, mMadinahLiveUrl);

        editor2.apply();
        Log.d("LIVEEEE", mMakkahLiveUrl);
    }


    private void setUpQuranVerse() {
        mQuranVerseTv = findViewById(R.id.quran_verse_tv);
        // [0 - 40]

        Random r = new Random();
        int i1 = r.nextInt(40 - 1) + 1;
        mCurrentQuranVerse = getResources().getStringArray(R.array.quran_verses)[i1 - 1];


        mQuranVerseTv.setText(mCurrentQuranVerse);
    }


    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private Boolean firstTime = null;
    /**
     * Checks if the user is opening the app for the first time.
     * Note that this method should be placed inside an activity and it can be called multiple times.
     * @return boolean
     */
    public boolean isFirstTime() {
        if (firstTime == null) {
            SharedPreferences mPreferences = SplashScreen.this.getSharedPreferences("first_time", Context.MODE_PRIVATE);
            firstTime = mPreferences.getBoolean("firstTime", true);
            if (firstTime) {
                SharedPreferences.Editor editor = mPreferences.edit();
                editor.putBoolean("firstTime", false);
                editor.apply();
            }
        }
        return firstTime;
    }

    public class StartServerRequest extends AsyncTask<String, Void, String> {

        int mWhereCalled = 101;

        public StartServerRequest(int whereAMIbringCalledFrom) {
            mWhereCalled = whereAMIbringCalledFrom;
        }


        @Override
        protected String doInBackground(String... params) {
            OkHttpClient client = new OkHttpClient();

            String url = params[0];

            Request request = new Request.Builder()
                    .url(url)
                    .build();

            Response response = null;
            try {
                response = client.newCall(request).execute();
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
            try {
                return response.body().string();
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if (s == null) {
                //Launch Bug Error Activity
            } else {
                //TODO : Launch the new Activity and pass the response JSONNNN!!!!
                try {
                    PackageInfo pInfo = SplashScreen.this.getPackageManager().getPackageInfo(SplashScreen.this.getPackageName(), 0);
                    String version = pInfo.versionName;

                    try {
                        JSONObject root = new JSONObject(s);

                        // 5 Daily Prayers
                        String fajrTime = root.getString("fajr");
                        String fajrIqamaTime = root.getString("fajrIqama");

                        String sunriseTime = root.getString("sunrise");

                        String duhrTime = root.getString("duhr");
                        String duhrIqamaTime = root.getString("duhrIqama");

                        String asrTime = root.getString("asr");
                        String asrIqamaTime = root.getString("asrIqama");

                        Log.d("Rayyan", "ASR TIME : " + asrTime);

                        String maghrebTime = root.getString("maghreb");
                        String maghrebIqamaTime = root.getString("maghrebIqama");

                        String ishaTime = root.getString("isha");
                        //Right After Azan
                        String ishaIqamaTime = root.getString("ishaIqama");


                        String firstJummaTime = root.getString("firstJumma");
                        String secondJummahTime = root.getString("secondJumma");


                        SharedPreferences.Editor editor2 = SplashScreen.this.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                        editor2.putString("fajrTime", fajrTime);
                        editor2.putString("fajrIqamaTime", fajrIqamaTime);

                        editor2.putString("sunriseTime", sunriseTime);

                        editor2.putString("duhrTime", duhrTime);
                        editor2.putString("duhrIqamaTime", duhrIqamaTime);

                        editor2.putString("asrTime", asrTime);
                        editor2.putString("asrIqamaTime", asrIqamaTime);

                        editor2.putString("maghrebTime", maghrebTime);
                        editor2.putString("maghrebIqamaTime", maghrebIqamaTime);

                        editor2.putString("ishaTime", ishaTime);
                        editor2.putString("ishaIqamaTime", ishaIqamaTime);

                        editor2.putString("firstJummaTime", firstJummaTime);
                        editor2.putString("secondJummahTime", secondJummahTime);
                        editor2.apply();


                        if (mWhereCalled == 0) {

                            final Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    // Do something after 5s = 5000ms
                                    Intent intent = new Intent(SplashScreen.this, Main_Activity.class);
                                    startActivity(intent);
                                }
                            }, 5000);
                        }else{
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }


            }
        }
    }

}
