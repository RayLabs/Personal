package app.rayyan.com.bcma.myapplication.MainPages;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baoyz.widget.PullRefreshLayout;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.github.eltohamy.materialhijricalendarview.MaterialHijriCalendarView;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import app.rayyan.com.bcma.myapplication.Adapters.DonationAdapter;
import app.rayyan.com.bcma.myapplication.Adapters.FeatureAdapter;
import app.rayyan.com.bcma.myapplication.Alarm.AlarmReceiver;
import app.rayyan.com.bcma.myapplication.Alarm.JobScheduler;
import app.rayyan.com.bcma.myapplication.Alarm.NotificationScheduler;
import app.rayyan.com.bcma.myapplication.ErrorActivity;
import app.rayyan.com.bcma.myapplication.MainPages.MorePages.Names_of_Allah;
import app.rayyan.com.bcma.myapplication.Models.Donation;
import app.rayyan.com.bcma.myapplication.Models.Feature;
import app.rayyan.com.bcma.myapplication.Models.Prayer;
import app.rayyan.com.bcma.myapplication.R;
import app.rayyan.com.bcma.myapplication.SettingsActivity;
import app.rayyan.com.bcma.myapplication.SplashScreen;
import app.rayyan.com.bcma.myapplication.Utils.Names_Of_Allah;
import app.rayyan.com.bcma.myapplication.Utils.PrayersTimeManager;
import douglasspgyn.com.github.circularcountdown.CircularCascadeCountdown;
import douglasspgyn.com.github.circularcountdown.CircularCountdown;
import douglasspgyn.com.github.circularcountdown.listener.CascadeListener;
import douglasspgyn.com.github.circularcountdown.listener.CircularListener;
import kotlin.jvm.internal.Intrinsics;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static android.content.Context.MODE_PRIVATE;


public class HomeFragment extends Fragment{



    private static final String MY_PREFS_NAME = "MyPref1";
    private Context mContext;

    private MaterialHijriCalendarView mCalenderView;
    private TextView mFajrTimeTv, mFajrIqamaTimeTv, mSunriseTimeTv, mDuhrTimeTv,
            mDuhrIqamaTimeTv, mAsrTimeTv, mAsrIqamaTimeTv, mMagTimeTv, mMagIqamaTimeTv,
            mIshaTimeTv, mIshaIqamaTimeTv, mJummahTimeTv, mJummahIqamaTimeTv, mDailyReminderTv;

    private static String DEF_VALUE = "NaN";
    private TextView mNextPrayerTV;
    private TextView mUserBranchTV;
    private PullRefreshLayout refesh_layout;
    private ImageView mPrayerImageView;
    private TextView mPrayerTimeTV;
    private com.alexfu.countdownview.CountDownView mPrayerTimerTv;
    private RecyclerView features_rv;
    private ArrayList<Feature> feature_list = new ArrayList<>();
    private FeatureAdapter featureAdapter;
    private RecyclerView donations_rv;
    private DonationAdapter donationAdapter;
    private WebView mWebViewHadith;
    public static final String mURL_200_HADITHS = "http://www.thebcma.com/upload/Media/200GoldenHadith/index.html";
    private ArrayList<String> mDonationsUrl;
    private CardView mDonationsView;
    private LinearLayoutManager layoutManager;
    private ImageView mLeftBtnDonations;
    private ImageView mRightBtnDonations;
    private ImageView mAllahNameIv;
    private LinearLayout mAllahNameMore;

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        // Inflate the layout for this fragment

        mFajrTimeTv = view.findViewById(R.id.fajr_prayer_time_tv);
        mFajrIqamaTimeTv = view.findViewById(R.id.fajr_prayer_iqama_tv);

        mSunriseTimeTv = view.findViewById(R.id.sunrise_time_tv);

        mDuhrTimeTv = view.findViewById(R.id.duhr_prayer_time_tv);
        mDuhrIqamaTimeTv = view.findViewById(R.id.duhr_prayer_iqama_tv);

        mAsrTimeTv = view.findViewById(R.id.asr_prayer_time_tv);
        mAsrIqamaTimeTv = view.findViewById(R.id.asr_prayer_iqama_tv);

        mMagTimeTv = view.findViewById(R.id.maghrib_prayer_time_tv);
        mMagIqamaTimeTv = view.findViewById(R.id.maghrib_prayer_iqama_tv);

        mIshaTimeTv = view.findViewById(R.id.isha_prayer_time_tv);
        mIshaIqamaTimeTv = view.findViewById(R.id.isha_prayer_iqama_tv);

        mJummahTimeTv = view.findViewById(R.id.friday_prayer_time_tv);
        mJummahIqamaTimeTv = view.findViewById(R.id.friday_prayer_iqama_tv);

        mNextPrayerTV = view.findViewById(R.id.next_prayer_tv);
        mUserBranchTV = view.findViewById(R.id.home_user_branch_tv);

        mPrayerImageView = view.findViewById(R.id.prayer_image_view);
        mPrayerTimeTV = view.findViewById(R.id.next_prayer_name_tv);

        mPrayerTimerTv = view.findViewById(R.id.count_down);

        refesh_layout = view.findViewById(R.id.swipeRefreshLayout);
        features_rv = view.findViewById(R.id.features_rv);
        donations_rv = view.findViewById(R.id.donation_rv);

        mWebViewHadith = view.findViewById(R.id.webview);
        mDailyReminderTv = view.findViewById(R.id.daily_reminder_tv);

        mDonationsView = view.findViewById(R.id.donations_view);

        mLeftBtnDonations = view.findViewById(R.id.left_btn_dona);
        mRightBtnDonations = view.findViewById(R.id.right_btn_dona);

        mAllahNameIv = view.findViewById(R.id.allah_name_iv);

        mAllahNameMore = view.findViewById(R.id.view_more_names);

        mDonationsView.setVisibility(View.GONE);
//
//        var10003 = (CircularCountdown) view.findViewById(R.id.circularCountdownSeconds);
//        var10003M = (CircularCountdown) view.findViewById(R.id.circularCountdownMinutes);
//        var10003H = (CircularCountdown) view.findViewById(R.id.circularCountdownHours);



        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    private void tryConnection() {
        boolean isNetworkON = isNetworkAvailable(mContext);

        if (isNetworkON) {
            mWebViewHadith.setVisibility(View.VISIBLE);
            loadHadith200();
        } else {
            mWebViewHadith.setVisibility(View.GONE);

        }
    }

    @Override
    public void onStart() {
        super.onStart();

        if(mContext == null){
            mContext = getActivity();
        }

        SharedPreferences prefs = mContext.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);

        tryConnection();

        // listen refresh event
        refesh_layout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // start refresh
                tryConnection();
                refesh_layout.setRefreshing(false);
            }
        });

        setUpQuranVerse();

        ArrayList<Integer> mNamesOfAllah = new ArrayList<>();
        setUpNamesOfAllah(mNamesOfAllah);


        mAllahNameMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(mContext, Names_of_Allah.class));
            }
        });

        mAllahNameIv.setImageResource(getRandomImage(mNamesOfAllah));

        mFajrTimeTv.setText(prefs.getString("fajrTime", DEF_VALUE));
        mFajrIqamaTimeTv.setText(prefs.getString("fajrIqamaTime", DEF_VALUE));

        mSunriseTimeTv.setText(prefs.getString("sunriseTime", DEF_VALUE));

        mDuhrTimeTv.setText(prefs.getString("duhrTime", DEF_VALUE));
        mDuhrIqamaTimeTv.setText(prefs.getString("duhrIqamaTime", DEF_VALUE));

        mAsrTimeTv.setText(prefs.getString("asrTime", DEF_VALUE));
        mAsrIqamaTimeTv.setText(prefs.getString("asrIqamaTime", DEF_VALUE));

        mMagTimeTv.setText(prefs.getString("maghrebTime", DEF_VALUE));
        mMagIqamaTimeTv.setText(prefs.getString("maghrebIqamaTime", DEF_VALUE));

        mIshaTimeTv.setText(prefs.getString("ishaTime", DEF_VALUE));
        mIshaIqamaTimeTv.setText(prefs.getString("ishaIqamaTime", DEF_VALUE));

        mJummahTimeTv.setText(prefs.getString("firstJummaTime", DEF_VALUE));
        mJummahIqamaTimeTv.setText(prefs.getString("secondJummahTime", DEF_VALUE));

        int userCurrentBranchID = prefs.getInt("userCurrentLocationID", 2);

        mLeftBtnDonations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveLeft(view);
            }
        });

        mRightBtnDonations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveRight(view);
            }
        });

       String mBranchNamea = getResources().getStringArray(R.array.branches_def)[userCurrentBranchID];

       setUpDonation(mBranchNamea);

        mUserBranchTV.setText(String.format(getString(R.string.branch), mBranchNamea));

        Date currentTime = Calendar.getInstance().getTime();

        ArrayList<String> allPrayerNames = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.prayer_names)));
        ArrayList<String> allPrayerTimes = new ArrayList<String>();

        allPrayerTimes.add(prefs.getString("fajrTime", DEF_VALUE));
        allPrayerTimes.add(prefs.getString("duhrTime", DEF_VALUE));
        allPrayerTimes.add(prefs.getString("asrTime", DEF_VALUE));
        allPrayerTimes.add(prefs.getString("maghrebTime", DEF_VALUE));
        allPrayerTimes.add(prefs.getString("ishaTime", DEF_VALUE));


        GridLayoutManager manager = new GridLayoutManager(getContext(), 2, GridLayoutManager.HORIZONTAL, false);
        manager.setOrientation(GridLayoutManager.HORIZONTAL);

        featureAdapter = new FeatureAdapter(populatefeatureList(), mContext);

        features_rv.setLayoutManager(manager);
        features_rv.setAdapter(featureAdapter);


        if (mDonationsUrl.size() != 0) {

            layoutManager
                    = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);

            donationAdapter = new DonationAdapter(mContext, populateDonationList());

            donations_rv.setLayoutManager(layoutManager);
            donations_rv.setAdapter(donationAdapter);
        }


        Prayer currentPrayer = PrayersTimeManager.getNextUserPrayer(currentTime, allPrayerNames, allPrayerTimes);
//        if(currentPrayer.getmIsPrayerNextDay() == Codes.PRAYER_NEXT_DAY){
//            //Todo: Previous Isha Time Swith to next Day!!!\
//            Log.v("Rayyan ~", "Isha Old Prayer!! Switch to next day!" );
//
//
//            String mFormattedDate = String.valueOf(android.text.format.DateFormat.format("yyyy/MM/dd", currentTime));
//
//
//            int userCurrentBranchID = prefs.getInt("userCurrentLocationID", 2);
//
//
//            new StartServerRequest2().execute(URLHelper.prayerServerUrlGen(mFormattedDate, userCurrentBranchID));
//
//            currentPrayer = PrayersTimeManager.getNextUserPrayer(currentTime, allPrayerNames, allPrayerTimes);
//
//            long currentPrayerMargin = currentPrayer.getmPrayerMargin();
//            String currentPrayerName = currentPrayer.getmPrayerName();
//
//            long diffence_in_minute = TimeUnit.MINUTES.convert(currentPrayerMargin, TimeUnit.MILLISECONDS);
//
//            Log.v("Rayyan ~", "Next Prayer : " + currentPrayerName + " & Minutes Remaining : " + diffence_in_minute);
//
//
//        }else {
        if (currentPrayer == null){
            //Probs mamybe Isha time old get the new time

//            Date currentTime2 = Calendar.getInstance().getTime();
//            Calendar c = Calendar.getInstance();
//            c.setTime(currentTime2);
//            c.add(Calendar.DATE, 1);
//            currentTime2 = c.getTime();
//            String mFormattedDate = String.valueOf(DateFormat.format("yyyy/MM/dd", currentTime2));
//
//            Intent intent2 = new Intent(mContext, SplashScreen.class);
//            intent2.putExtra("formattedDate", mFormattedDate);
//            intent2.putExtra("Uniqid", "From_Fragment_Home");
//            mContext.startActivity(intent2);
//            Log.d("Rayyan8ball", mFormattedDate);

            startActivity(new Intent(mContext, ErrorActivity.class));


        }else {


            long currentPrayerMargin = currentPrayer.getmPrayerMargin();
            String currentPrayerName = currentPrayer.getmPrayerName();
            String currentPrayerTime = currentPrayer.getmPrayerOriTIme();

            Log.v("Rayyan ~", "Next Prayer : " + currentPrayerName + " & Minutes Remainingdsfa : " + currentPrayerTime);


            //Todo: Prayer TODODODO!!!
            NotificationScheduler.setReminder(mContext, AlarmReceiver.class, currentPrayerTime);
            JobScheduler.setReminder(mContext, AlarmReceiver.class);

//            Intrinsics.checkExpressionValueIsNotNull(var10003, "circularCountdownSeconds");
//            (new CircularCascadeCountdown(currentPrayerMargin, var10003, var10003M, var10003H)).listener((CascadeListener) (new CascadeListener() {
//                public void onFinish() {
//                    Log.d("Cascade", "Finish");
//                }
//            })).start();


            mPrayerTimerTv.setStartDuration(currentPrayerMargin);
            mPrayerTimerTv.start();


//            new CountDownTimer(currentPrayerMargin, 1000) {
//                @Override
//                public void onTick(long millisUntilFinished) {
//                    long second = (millisUntilFinished / 1000) % 60;
//                    long minute = (millisUntilFinished / (1000 * 60)) % 60;
//                    long hour = (millisUntilFinished / (1000 * 60 * 60)) % 24;
//
//                    mPrayerTimerTv.setText(String.format(getString(R.string.prayer_timer_format), String.valueOf(hour), String.valueOf(minute), String.valueOf(second)));
//                }
//
//                @Override
//                public void onFinish() {
//
//                }
//            }.start();


            mPrayerTimeTV.setText(currentPrayerTime);
            mNextPrayerTV.setText(currentPrayerName);

            Random r = new Random();
            int i1 = r.nextInt(4 - 1 + 1) + 1;
            Log.d("Rayyan Random  sdf", i1 + " h sdjhfljashj");
            ArrayList<Integer> mImagesPrayer = new ArrayList<>();

            if (currentPrayerName.equals(getResources().getStringArray(R.array.prayer_names)[0])) {
                //Fajr
                mImagesPrayer.add(R.drawable.madinah_fajr);
                mImagesPrayer.add(R.drawable.uk_mosque_fajr);
                mImagesPrayer.add(R.drawable.mosque_fajr_bea);
                mImagesPrayer.add(R.drawable.perdana_mosque_fajr);

                Glide.with(mContext)
                        .load(getRandomImage(mImagesPrayer))
                        .listener(new RequestListener<Drawable>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                Toast.makeText(mContext, "Image Failed To Load!", Toast.LENGTH_SHORT).show();
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                return false;
                            }
                        })
                        .into(mPrayerImageView);


            } else if (currentPrayerName.equals(getResources().getStringArray(R.array.prayer_names)[1])) {
                //Duhr
                mImagesPrayer.add(R.drawable.blue_mouque_duhr);
                mImagesPrayer.add(R.drawable.makkah_mosque_duhr);
                mImagesPrayer.add(R.drawable.uk_mosque_duhr);
                mImagesPrayer.add(R.drawable.nusrat_mosque_zuhr);

                Glide.with(mContext)
                        .load(getRandomImage(mImagesPrayer))
                        .listener(new RequestListener<Drawable>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                Toast.makeText(mContext, "Image Failed To Load!", Toast.LENGTH_SHORT).show();
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                return false;
                            }
                        })
                        .into(mPrayerImageView);

            } else if (currentPrayerName.equals(getResources().getStringArray(R.array.prayer_names)[2])) {
                //Asr
                mImagesPrayer.add(R.drawable.bea_mosque_asr);
                mImagesPrayer.add(R.drawable.blue_mosque_asr);
                mImagesPrayer.add(R.drawable.morroco_mosque_asr);

                Glide.with(mContext)
                        .load(getRandomImage(mImagesPrayer))
                        .listener(new RequestListener<Drawable>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                Toast.makeText(mContext, "Image Failed To Load!", Toast.LENGTH_SHORT).show();
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                return false;
                            }
                        })
                        .into(mPrayerImageView);

            } else if (currentPrayerName.equals(getResources().getStringArray(R.array.prayer_names)[3])) {
                //Maghreb

                mImagesPrayer.add(R.drawable.makkah_maghreb);
                mImagesPrayer.add(R.drawable.madinah_maghreb);
                mImagesPrayer.add(R.drawable.badshahi_mosque_maghreb);
                mImagesPrayer.add(R.drawable.india_mosque_maghreb);
                mImagesPrayer.add(R.drawable.uk__mosque_maghreb);

                Glide.with(mContext)
                        .load(getRandomImage(mImagesPrayer))
                        .listener(new RequestListener<Drawable>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                Toast.makeText(mContext, "Image Failed To Load!", Toast.LENGTH_SHORT).show();
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                return false;
                            }
                        })
                        .into(mPrayerImageView);
            } else {
                //Isha

                mImagesPrayer.add(R.drawable.faisal_mosque_isha);
                mImagesPrayer.add(R.drawable.makkah_isha);
                mImagesPrayer.add(R.drawable.mosque_isha);
                mImagesPrayer.add(R.drawable.bea_mosque_ishaa);

                Glide.with(mContext)
                        .load(getRandomImage(mImagesPrayer))
                        .listener(new RequestListener<Drawable>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                Toast.makeText(mContext, "Image Failed To Load!", Toast.LENGTH_SHORT).show();
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                return false;
                            }
                        })
                        .into(mPrayerImageView);

            }

        }

//


//        long asrTimeTimemilli = stringToDate(asrTime.toLowerCase()).getTime() - currentUserTime;
//
//        long diffence_in_minute = TimeUnit.MINUTES.convert(marginBetTimeandPrayer,TimeUnit.MILLISECONDS);
//
//        Log.d("Rayyan mindiffree", diffence_in_minute  + "  -  fasg");
//
//        long millis = marginBetTimeandPrayer % 1000;
//        long second = (marginBetTimeandPrayer / 1000) % 60;
//        long minute = (marginBetTimeandPrayer / (1000 * 60)) % 60;
//        long hour = (marginBetTimeandPrayer / (1000 * 60 * 60)) % 24;

//        String time = String.format("%02d:%02d:%02d.%d", hour, minute, second, millis);
//
//        Log.d("Rayyan : ", "Diff     : " + time);


    }

    private void setUpNamesOfAllah(ArrayList<Integer> mNamesOfAllah) {
        mNamesOfAllah.add(R.drawable.allah_names_1);

        mNamesOfAllah.add(R.drawable.allah_names_2);

        mNamesOfAllah.add(R.drawable.allah_names_3);

        mNamesOfAllah.add(R.drawable.allah_names_4);

        mNamesOfAllah.add(R.drawable.allah_names_5);

        mNamesOfAllah.add(R.drawable.allah_names_6);

        mNamesOfAllah.add(R.drawable.allah_names_7);

        mNamesOfAllah.add(R.drawable.allah_names_8);

        mNamesOfAllah.add(R.drawable.allah_names_9);

        mNamesOfAllah.add(R.drawable.allah_names_10);

        mNamesOfAllah.add(R.drawable.allah_names_11);

        mNamesOfAllah.add(R.drawable.allah_names_12);

        mNamesOfAllah.add(R.drawable.allah_names_13);

        mNamesOfAllah.add(R.drawable.allah_names_14);

        mNamesOfAllah.add(R.drawable.allah_names_15);

        mNamesOfAllah.add(R.drawable.allah_names_16);

        mNamesOfAllah.add(R.drawable.allah_names_17);

        mNamesOfAllah.add(R.drawable.allah_names_18);

        mNamesOfAllah.add(R.drawable.allah_names_19);

        mNamesOfAllah.add(R.drawable.allah_names_20);

        mNamesOfAllah.add(R.drawable.allah_names_21);

        mNamesOfAllah.add(R.drawable.allah_names_23);

        mNamesOfAllah.add(R.drawable.allah_names_24);

        mNamesOfAllah.add(R.drawable.allah_names_25);

        mNamesOfAllah.add(R.drawable.allah_names_26);

        mNamesOfAllah.add(R.drawable.allah_names_27);

        mNamesOfAllah.add(R.drawable.allah_names_28);

        mNamesOfAllah.add(R.drawable.allah_names_29);

        mNamesOfAllah.add(R.drawable.allah_names_30);

        mNamesOfAllah.add(R.drawable.allah_names_31);

        mNamesOfAllah.add(R.drawable.allah_names_32);

        mNamesOfAllah.add(R.drawable.allah_names_33);

        mNamesOfAllah.add(R.drawable.allah_names_34);

        mNamesOfAllah.add(R.drawable.allah_names_35);

        mNamesOfAllah.add(R.drawable.allah_names_36);

        mNamesOfAllah.add(R.drawable.allah_names_37);

        mNamesOfAllah.add(R.drawable.allah_names_38);

        mNamesOfAllah.add(R.drawable.allah_names_39);

        mNamesOfAllah.add(R.drawable.allah_names_40);

        mNamesOfAllah.add(R.drawable.allah_names_41);

        mNamesOfAllah.add(R.drawable.allah_names_42);

        mNamesOfAllah.add(R.drawable.allah_names_43);

        mNamesOfAllah.add(R.drawable.allah_names_44);

        mNamesOfAllah.add(R.drawable.allah_names_45);

        mNamesOfAllah.add(R.drawable.allah_names_46);

        mNamesOfAllah.add(R.drawable.allah_names_47);

        mNamesOfAllah.add(R.drawable.allah_names_48);

        mNamesOfAllah.add(R.drawable.allah_names_49);

        mNamesOfAllah.add(R.drawable.allah_names_50);

        mNamesOfAllah.add(R.drawable.allah_names_51);

        mNamesOfAllah.add(R.drawable.allah_names_52);

        mNamesOfAllah.add(R.drawable.allah_names_53);

        mNamesOfAllah.add(R.drawable.allah_names_54);

        mNamesOfAllah.add(R.drawable.allah_names_55);

        mNamesOfAllah.add(R.drawable.allah_names_56);

        mNamesOfAllah.add(R.drawable.allah_names_57);

        mNamesOfAllah.add(R.drawable.allah_names_58);

        mNamesOfAllah.add(R.drawable.allah_names_59);

        mNamesOfAllah.add(R.drawable.allah_names_60);

        mNamesOfAllah.add(R.drawable.allah_names_61);

        mNamesOfAllah.add(R.drawable.allah_names_62);

        mNamesOfAllah.add(R.drawable.allah_names_63);

        mNamesOfAllah.add(R.drawable.allah_names_64);

        mNamesOfAllah.add(R.drawable.allah_names_65);

        mNamesOfAllah.add(R.drawable.allah_names_66);

        mNamesOfAllah.add(R.drawable.allah_names_67);

        mNamesOfAllah.add(R.drawable.allah_names_68);

        mNamesOfAllah.add(R.drawable.allah_names_69);

        mNamesOfAllah.add(R.drawable.allah_names_70);



    }

    private void setUpDonation(String mBranchNamea) {

        mDonationsUrl = new ArrayList<>();

        switch (mBranchNamea){


            case "Head Office":
                mDonationsView.setVisibility(View.VISIBLE);

                mDonationsUrl.add("https://www.paypal.com/cgi-bin/webscr?cmd=_s-xclick&hosted_button_id=CSRWS7M9SC65W");
                mDonationsUrl.add("https://www.paypal.com/cgi-bin/webscr?cmd=_s-xclick&hosted_button_id=EQZ32R5UN3QJS");
                mDonationsUrl.add("https://www.paypal.com/cgi-bin/webscr?cmd=_s-xclick&hosted_button_id=Z9FLQ26EHB24G");
                return;

            case "Richmond Branch":
                mDonationsView.setVisibility(View.VISIBLE);


                mDonationsUrl.add("https://www.paypal.com/cgi-bin/webscr?cmd=_s-xclick&hosted_button_id=DL9UZMTTYQBKU");
                mDonationsUrl.add("https://www.paypal.com/cgi-bin/webscr?cmd=_s-xclick&hosted_button_id=V68TU73MHZSG2");
                mDonationsUrl.add("https://www.paypal.com/cgi-bin/webscr?cmd=_s-xclick&hosted_button_id=V9X5WQ2QZNYRU");
                return;

            case "Burnaby Branch":
                mDonationsView.setVisibility(View.VISIBLE);


                mDonationsUrl.add("https://www.paypal.com/cgi-bin/webscr?cmd=_s-xclick&hosted_button_id=JAB2SKDLFPQ74");
                mDonationsUrl.add("https://www.paypal.com/cgi-bin/webscr?cmd=_s-xclick&hosted_button_id=9UNW8ZR9UGKRJ");
                mDonationsUrl.add("https://www.paypal.com/cgi-bin/webscr?cmd=_s-xclick&hosted_button_id=EJ66ZEV9N5Y52");
                return;

            case "Surrey Delta Branch":
                mDonationsView.setVisibility(View.VISIBLE);


                mDonationsUrl.add("https://www.paypal.com/cgi-bin/webscr?cmd=_s-xclick&hosted_button_id=GRUSV3FPSP6VJ");
                mDonationsUrl.add("https://www.paypal.com/cgi-bin/webscr?cmd=_s-xclick&hosted_button_id=LBB7FTGC454BL");
                mDonationsUrl.add("https://www.paypal.com/cgi-bin/webscr?cmd=_s-xclick&hosted_button_id=AK7BCNNLF3N4Q");
                return;

            case "Vancouver Branch":
                mDonationsView.setVisibility(View.VISIBLE);


                mDonationsUrl.add("https://www.paypal.com/cgi-bin/webscr?cmd=_s-xclick&hosted_button_id=Q5H76YMWE8E6A");
                mDonationsUrl.add("https://www.paypal.com/cgi-bin/webscr?cmd=_s-xclick&hosted_button_id=W3CR479A63GYJ");
                mDonationsUrl.add("https://www.paypal.com/cgi-bin/webscr?cmd=_s-xclick&hosted_button_id=4RN3AP79V8E5U");
                return;

            case "Surrey East Branch":
                mDonationsView.setVisibility(View.VISIBLE);


                mDonationsUrl.add("https://www.paypal.com/cgi-bin/webscr?cmd=_s-xclick&hosted_button_id=H5QT2F922FL2A");
                mDonationsUrl.add("https://www.paypal.com/cgi-bin/webscr?cmd=_s-xclick&hosted_button_id=EYDNRNDFD9YWN");
                mDonationsUrl.add("https://www.paypal.com/cgi-bin/webscr?cmd=_s-xclick&hosted_button_id=5PTJJXKK99NL4");
                return;

            case "Kelowna Branch":
                mDonationsView.setVisibility(View.VISIBLE);


                mDonationsUrl.add("https://www.paypal.com/cgi-bin/webscr?cmd=_s-xclick&hosted_button_id=HQ7NGQZAU56PC");
                mDonationsUrl.add("https://www.paypal.com/cgi-bin/webscr?cmd=_s-xclick&hosted_button_id=R27T9MKWF3RUE");
                mDonationsUrl.add("https://www.paypal.com/cgi-bin/webscr?cmd=_s-xclick&hosted_button_id=UHEXLFHF3TK8L");
                return;

            case "Victoria Branch":
                mDonationsView.setVisibility(View.VISIBLE);


                mDonationsUrl.add("https://www.paypal.com/cgi-bin/webscr?cmd=_s-xclick&hosted_button_id=MWMN64BSBSLCN");
                mDonationsUrl.add("https://www.paypal.com/cgi-bin/webscr?cmd=_s-xclick&hosted_button_id=EAEGNRYMA3XLG");
                mDonationsUrl.add("https://www.paypal.com/cgi-bin/webscr?cmd=_s-xclick&hosted_button_id=ZVF4Y2EFK8L5N");
                return;

            case "Abbotsford Branch":
                mDonationsView.setVisibility(View.VISIBLE);


                mDonationsUrl.add("https://www.paypal.com/cgi-bin/webscr?cmd=_s-xclick&hosted_button_id=MLUEMRBK5VEM4");
                mDonationsUrl.add("https://www.paypal.com/cgi-bin/webscr?cmd=_s-xclick&hosted_button_id=WLSNMV3MJXBQQ");
                mDonationsUrl.add("https://www.paypal.com/cgi-bin/webscr?cmd=_s-xclick&hosted_button_id=CM7DJP4ZRQS5Q");
                return;

            case "Chilliwack Chapter":
                mDonationsView.setVisibility(View.VISIBLE);


                mDonationsUrl.add("https://www.paypal.com/cgi-bin/webscr?cmd=_s-xclick&hosted_button_id=495ALLQCPRTZA");
                mDonationsUrl.add("https://www.paypal.com/cgi-bin/webscr?cmd=_s-xclick&hosted_button_id=XUTVT8ZU6MTC4");
                mDonationsUrl.add("https://www.paypal.com/cgi-bin/webscr?cmd=_s-xclick&hosted_button_id=2D2F9BKTJYWWQ");
                return;

            case "Women Surrey Delta":
                mDonationsView.setVisibility(View.VISIBLE);


                mDonationsUrl.add("https://www.paypal.com/cgi-bin/webscr?cmd=_s-xclick&hosted_button_id=GRUSV3FPSP6VJ");
                mDonationsUrl.add("https://www.paypal.com/cgi-bin/webscr?cmd=_s-xclick&hosted_button_id=LBB7FTGC454BL");
                mDonationsUrl.add("https://www.paypal.com/cgi-bin/webscr?cmd=_s-xclick&hosted_button_id=4RN3AP79V8E5U");
                return;

            case "Penticton":
                mDonationsView.setVisibility(View.VISIBLE);


                mDonationsUrl.add("https://www.paypal.com/cgi-bin/webscr?cmd=_s-xclick&hosted_button_id=NXD2M89D9VQRS");
                mDonationsUrl.add("https://www.paypal.com/cgi-bin/webscr?cmd=_s-xclick&hosted_button_id=F6E74B5588D22");
                mDonationsUrl.add("https://www.paypal.com/cgi-bin/webscr?cmd=_s-xclick&hosted_button_id=ZJ54KSXLLAMAW");
                return;


        }



    }


    public void moveLeft(View v) {
        if (layoutManager.findFirstVisibleItemPosition() > 0) {
            donations_rv.smoothScrollToPosition(layoutManager.findFirstVisibleItemPosition() - 1);
        } else {
            donations_rv.smoothScrollToPosition(0);
        }

    }

    public void moveRight(View v) {
        donations_rv.smoothScrollToPosition(layoutManager.findLastVisibleItemPosition() + 1);

    }

    private void loadHadith200() {

        WebSettings webSettings = mWebViewHadith.getSettings();
        webSettings.setJavaScriptEnabled(true);
        mWebViewHadith.setWebViewClient(new WebViewClient());


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            CookieManager.getInstance().setAcceptThirdPartyCookies(mWebViewHadith, true);
        }

        webSettings.setBuiltInZoomControls(false);
        mWebViewHadith.loadUrl(mURL_200_HADITHS);

    }

    private ArrayList<Feature> populatefeatureList() {
        ArrayList<Feature> mFeatureList = new ArrayList<>();
        mFeatureList.add(new Feature(getString(R.string.prayer_times_spaced), R.drawable.more_prayer));
        mFeatureList.add(new Feature(getString(R.string.ninety_names_spaced), R.drawable.allah_names_more));
        mFeatureList.add(new Feature(getString(R.string.qiblah_spaced), R.drawable.qiblah_direction_more));
        mFeatureList.add(new Feature(getString(R.string.branches_spaced), R.drawable.branches_more));
        mFeatureList.add(new Feature(getString(R.string.live_makkah_spaced), R.drawable.live));
        mFeatureList.add(new Feature(getString(R.string.live_madina_spaced), R.drawable.live_dark));
        mFeatureList.add(new Feature(getString(R.string.duas_spaced), R.drawable.duas_more));
        mFeatureList.add(new Feature(getString(R.string.news_spaced), R.drawable.news_more));
        mFeatureList.add(new Feature(getString(R.string.nearby_mosques_spaced), R.drawable.nearby_mosques_more));
        mFeatureList.add(new Feature(getString(R.string.golden_hadtih_spaced), R.drawable.two_golden_hadith_more));
        mFeatureList.add(new Feature(getString(R.string.islamic_calender_spaced), R.drawable.calender_more));
        mFeatureList.add(new Feature(getString(R.string.tasbih_spaced), R.drawable.tasbih_more));
        mFeatureList.add(new Feature(getString(R.string.zakat_calculator_spaced), R.drawable.zakat_cal_more));
        mFeatureList.add(new Feature(getString(R.string.halal_resturants_spaced), R.drawable.halal_resturants_more));
        mFeatureList.add(new Feature(getString(R.string.settings_spaced), R.drawable.settings_more));
        mFeatureList.add(new Feature(getString(R.string.fasting_tracker), R.drawable.fasting_tracker));

        return mFeatureList;
    }


    private ArrayList<Donation> populateDonationList() {
        ArrayList<Donation> mDonationList = new ArrayList<>();



        mDonationList.add(new Donation(R.drawable.zakat_iamge,
                "Give in Honour Donation",
                "A Honour donation is a meaningful way for family and relatives to show sympathy for the loss of loved ones. such as parents, spouse, children",
                "\u2022 $10,000 = 40 Shares = 10 Prayer Spaces\n" +
                "\u2022 $5,000 = 20 Shares = 5 Prayer Spaces\n" +
                "\u2022 $2,000 = 8 Shares = 2 Prayer Spaces",
                mDonationsUrl.get(0)));


        mDonationList.add(new Donation(R.drawable.masjid_image,
                "Give For Family Members",
                "Reward each member of your family by dedicating a share to them. Reserve your family spot in Jannah. Insha'Allah",
                "\u2022 $1000 = 4 Shares = 1 Prayer Spaces\n" +
                "\u2022 $500 = 2 Shares = 1/2 Prayer Spaces\n" +
                "\u2022 $250 = 1 Shares = 1 Square Foot",
                mDonationsUrl.get(1)));


        mDonationList.add(new Donation(R.drawable.sadaka_image,
                "Ways to Give",
                "Payments can be made in several ways. Pick one that suit your needs",
                "\u2022 Cash\n" +
                "\u2022 Cheque - BC Muslim Association - Richmond\n" +
                "\u2022 Paypal\n" +
                "\u2022 Credit Card or Pre-Authorized Payments",
                mDonationsUrl.get(2)));
        return mDonationList;
    }




    private void setUpQuranVerse() {
        // [0 - 40]

        Random r = new Random();
        int i1 = r.nextInt(40 - 1) + 1;
        String mCurrentReminder = getResources().getStringArray(R.array.quran_verses)[i1 - 1];


        mDailyReminderTv.setText(mCurrentReminder);
    }


    private boolean isNetworkAvailable(Context mContext) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.home_page_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (mContext == null){
            mContext = getActivity();
        }

        switch (item.getItemId()) {
            case R.id.home_page_settings_menu:
                startActivity(new Intent(mContext, SettingsActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    public static int getRandomImage(ArrayList<Integer> iamges) {
        int rnd = new Random().nextInt(iamges.size());
        return iamges.get(rnd);
    }

}
