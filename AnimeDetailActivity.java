package com.myanime.rayyan.raylab.myanime;

import android.app.SearchManager;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.myanime.rayyan.raylab.myanime.Controllers_Adapter.AnimeAdapter;
import com.myanime.rayyan.raylab.myanime.Controllers_Adapter.EpisodeAdapter;
import com.myanime.rayyan.raylab.myanime.Favourites.FavoriteSQLHelper;
import com.myanime.rayyan.raylab.myanime.Favourites.FavouriteContract;
import com.myanime.rayyan.raylab.myanime.Models.Anime;
import com.myanime.rayyan.raylab.myanime.Models.Episode;
import com.myanime.rayyan.raylab.myanime.Support.ReportIssueActivity;
import com.myanime.rayyan.raylab.myanime.Support.RequestNewAnimeActivity;
import com.myanime.rayyan.raylab.myanime.Utils.MenuActions;
import com.rayyan.como.ran.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.myanime.rayyan.raylab.myanime.Favourites.FavouriteContract.FavouriteEntry.TABLE_NAME;

public class AnimeDetailActivity extends AppCompatActivity {

    private static final String MY_PREFS_NAME = "MyPref";
    private static final String MY_PREFS_NAME2 = "MyPref2";
    private String mAnimeName;
    private String mAnimeStatus;
    private String mAnimeGenres;
    private String mAnimeRating;
    private String mAnimeFirstAiringDate;
    private String mAnimeFeaturedImage;
    private String mAnimeRatingScale;
    private String mAnimeDesc;
    private TextView mAnimeNameTv;
    private TextView mAnimeRatingTv;
    private TextView mAnimeGenresTv;
    private TextView mAnimeStatusTv;
    private TextView mAnimeFirstAiringDateTv;
    private TextView mAnimeRatingScaleTv;
    private ImageView mAnimeFeaturedImageImageView;
    private TextView mAnimeDescTv;
    private RecyclerView mAnimeEpisodesRecyclerView;
    private EpisodeAdapter mEpisodeAdapter;
    private ArrayList<Episode> mAnimeEps;
    private AdView mAdViewDetail1;
    private AdView mAdViewDetail2;
    private InterstitialAd mInterstitialAd;
    private FloatingActionButton mAnimeFavoriteFabutton;
    private FavoriteSQLHelper mDbHelper;
    private String mAnime_ad;
    private String mBanner_ad_unit_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anime_detail);


        Intent intent = getIntent();

        mAnimeName = intent.getExtras().getString(getString(R.string.animename_json));


        mAnimeNameTv = (TextView) findViewById(R.id.currentAnimeNameTv);
        mAnimeStatusTv = (TextView) findViewById(R.id.currentAnimeStatusTv);
        mAnimeGenresTv = (TextView) findViewById(R.id.currentAnimeGenresTv);
        mAnimeRatingTv = (TextView) findViewById(R.id.currentAnimeRatingTv);
        mAnimeFirstAiringDateTv = (TextView) findViewById(R.id.currentAnimeFirstAiredTv);
        mAnimeFeaturedImageImageView = (ImageView) findViewById(R.id.currentAnimeImageView);
        mAnimeRatingScaleTv = (TextView) findViewById(R.id.currentAnimeRatingScaleTv);
        mAnimeDescTv = (TextView) findViewById(R.id.currentAnimeDescTv);
        mAnimeEpisodesRecyclerView = (RecyclerView) findViewById(R.id.animeEpRecyclerview);
        mAnimeFavoriteFabutton = (FloatingActionButton) findViewById(R.id.fabFavorite);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(mAnimeName);


        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        String restoredText = prefs.getString("data", "");
        mAnime_ad = prefs.getString("anime_ad", null);
        mBanner_ad_unit_id = prefs.getString("banner_ad_unit_id", null);

        Random r = new Random();
        int i1 = r.nextInt(3 - 1) + 1;

        if (i1 == 3) {
            mInterstitialAd = new InterstitialAd(this);
            mInterstitialAd.setAdUnitId(getString(R.string.anime_ad));
            mInterstitialAd.loadAd(new AdRequest.Builder().build());
            mInterstitialAd.setAdListener(new AdListener() {
                @Override
                public void onAdLoaded() {
                    // Code to be executed when an ad finishes loading.
                    mInterstitialAd.show();
                }

                @Override
                public void onAdFailedToLoad(int errorCode) {
                    // Code to be executed when an ad request fails.
                }

                @Override
                public void onAdOpened() {
                    // Code to be executed when the ad is displayed.
                }

                @Override
                public void onAdLeftApplication() {
                    // Code to be executed when the user has left the app.
                }

                @Override
                public void onAdClosed() {
                    // Code to be executed when when the interstitial ad is closed.
                }
            });
        }

        mDbHelper = new FavoriteSQLHelper(this);

//        mAdViewDetail1 = findViewById(R.id.adViewAnimeDetail1);
//
//        AdRequest adRequest1 = new AdRequest.Builder().build();
//        mAdViewDetail1.loadAd(adRequest1);


        mAdViewDetail2 = findViewById(R.id.adViewAnimeDetail2);

        AdRequest adRequest = new AdRequest.Builder().build();
        mAdViewDetail2.loadAd(adRequest);




        boolean isCurrentAnimeFavorite = isCurrentAnimeFavorite();

        if(isCurrentAnimeFavorite){
            //The Current Anime Is Fasvorite!!!

            mAnimeFavoriteFabutton.setImageResource(R.drawable.ic_favorite_black_24dp);
            mAnimeFavoriteFabutton.setContentDescription(getString(R.string.remove_from_favorites));


        }else if (isCurrentAnimeFavorite == false){
            //The Current Place Is NOT Bookmarked!!!

            mAnimeFavoriteFabutton.setImageResource(R.drawable.ic_favorite_border);
            mAnimeFavoriteFabutton.setContentDescription(getString(R.string.add_to_favorites));
        }


        mAnimeFavoriteFabutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean isCurrentAnimeFavorite = isCurrentAnimeFavorite();

                SQLiteDatabase db = mDbHelper.getWritableDatabase();


                if(isCurrentAnimeFavorite){
                    //The Current Place Is Bookmarked!!!

                    //Execute sql query to remove from database
                    //NOTE: When removing by String in SQL, value must be enclosed with ''
                    db.execSQL("DELETE FROM " + FavouriteContract.FavouriteEntry.TABLE_NAME + " WHERE " + FavouriteContract.FavouriteEntry.COLUMN_ANIME_NAME + "= '" + mAnimeName + "'");

                    //Close the database
                    db.close();

                    Snackbar.make(v, "Removed From Favorite(s): " + mAnimeName, Snackbar.LENGTH_SHORT).show();


                    mAnimeFavoriteFabutton.setImageResource(R.drawable.ic_favorite_border);
                    mAnimeFavoriteFabutton.setContentDescription(getString(R.string.add_to_favorites));

                }else if (isCurrentAnimeFavorite == false){
                    //The Current Place Is NOT Bookmarked!!!

                    ContentValues values = new ContentValues();
                    values.put(FavouriteContract.FavouriteEntry.COLUMN_ANIME_NAME, mAnimeName);

                    Uri newUri = getContentResolver().insert(FavouriteContract.BASE_CONTENT_URI, values);

                    Snackbar.make(v, "Favorite(s) Added: " + mAnimeName, Snackbar.LENGTH_SHORT).show();

                    mAnimeFavoriteFabutton.setImageResource(R.drawable.ic_favorite_black_24dp);
                    mAnimeFavoriteFabutton.setContentDescription(getString(R.string.remove_from_favorites));
                }
            }
        });




        try {
            JSONObject base = new JSONObject(restoredText);

            JSONArray mAllAnimesArray = base.getJSONArray(getString(R.string.allanime_json));


            mAnimeEps = new ArrayList<>();

            for (int f = 0; f < mAllAnimesArray.length(); f++) {
                JSONObject mAnime = mAllAnimesArray.getJSONObject(f);

                String mNam = mAnime.getString(getString(R.string.animename_json));
                if (mNam.equals(mAnimeName)) {
                    mAnimeStatus = mAnime.getString(getString(R.string.animeStatus_json));
                    mAnimeGenres = mAnime.getString(getString(R.string.animeGenres_json));
                    mAnimeRating = mAnime.getString(getString(R.string.animeRating_json));
                    mAnimeFirstAiringDate = mAnime.getString(getString(R.string.animeFirstAired_json));
                    mAnimeFeaturedImage = mAnime.getString(getString(R.string.animeFeaImage_json));
                    mAnimeRatingScale = mAnime.getString(getString(R.string.animeRatingScale_json));
                    mAnimeDesc = mAnime.getString(getString(R.string.animeDesc_json));


                    mAnimeNameTv.setText(mAnimeName);
                    mAnimeStatusTv.setText(String.format(getString(R.string.status), mAnimeStatus));
                    mAnimeGenresTv.setText(mAnimeGenres);
                    mAnimeRatingTv.setText(String.format(getString(R.string.rating), mAnimeRating));
                    mAnimeFirstAiringDateTv.setText(String.format(getString(R.string.firstAireddate), mAnimeFirstAiringDate));
                    mAnimeRatingScaleTv.setText(String.format(getString(R.string.ratingScale), mAnimeRatingScale));
                    mAnimeDescTv.setText(mAnimeDesc);

                    Glide.with(this).load(mAnimeFeaturedImage).into(mAnimeFeaturedImageImageView);


                    JSONArray mAnimeEpisodes = mAnime.getJSONArray(getString(R.string.animeEpisodes_json));

                    for (int n = 0; n < mAnimeEpisodes.length(); n++) {
                        JSONObject mEpisode = mAnimeEpisodes.getJSONObject(n);

                        String mEpisodeName = mEpisode.getString(getString(R.string.episodename_json));
                        String mEpisodeSypnosis = mEpisode.getString(getString(R.string.episodesypnosis_json));
                        String mEpisodeDesc = mEpisode.getString(getString(R.string.episodedesc_json));
                        String mEpisodeUrl = mEpisode.getString(getString(R.string.episodeurl_json));


                        mAnimeEps.add(new Episode(mEpisodeName, mEpisodeSypnosis, mEpisodeDesc, mEpisodeUrl));

                    }

                    mEpisodeAdapter = new EpisodeAdapter(this, R.layout.item_episode, mAnimeEps);

                    LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true);
                    mAnimeEpisodesRecyclerView.setNestedScrollingEnabled(false);
                    mAnimeEpisodesRecyclerView.setLayoutManager(manager);
                    mAnimeEpisodesRecyclerView.setAdapter(mEpisodeAdapter);


                    SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME2, MODE_PRIVATE).edit();
                    editor.putString("image", mAnimeFeaturedImage);
                    editor.apply();


                    return;

                }

            }


        } catch (JSONException e) {
            e.printStackTrace();

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.all_anime_list_menu, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) this.getSystemService(Context.SEARCH_SERVICE);

        SearchView searchView = (SearchView) menu.findItem(R.id.action_search_all_anime_menu).getActionView();

        searchView.setQueryHint(getString(R.string.search_by_epiosde_number));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String quer) {
                String query = quer.toLowerCase();

                final List<Episode> filteredList = new ArrayList<>();

                for (int i = 0; i < mAnimeEps.size(); i++) {

                    final String text = mAnimeEps.get(i).getmEpisodeName().toLowerCase();
                    if (text.contains(query)) {

                        filteredList.add(mAnimeEps.get(i));
                    }
                }

                mAnimeEpisodesRecyclerView.setLayoutManager(new LinearLayoutManager(AnimeDetailActivity.this));
                mEpisodeAdapter = new EpisodeAdapter(AnimeDetailActivity.this, R.layout.item_anime, filteredList);
                mAnimeEpisodesRecyclerView.setAdapter(mEpisodeAdapter);

                LinearLayoutManager manager = new LinearLayoutManager(AnimeDetailActivity.this, LinearLayoutManager.VERTICAL, true);
                mAnimeEpisodesRecyclerView.setLayoutManager(manager);
                mEpisodeAdapter.notifyDataSetChanged();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                String query = newText.toLowerCase();

                final List<Episode> filteredList = new ArrayList<>();

                for (int i = 0; i < mAnimeEps.size(); i++) {

                    final String text = mAnimeEps.get(i).getmEpisodeName().toLowerCase();
                    if (text.contains(query)) {

                        filteredList.add(mAnimeEps.get(i));
                    }
                }

                mAnimeEpisodesRecyclerView.setLayoutManager(new LinearLayoutManager(AnimeDetailActivity.this));
                mEpisodeAdapter = new EpisodeAdapter(AnimeDetailActivity.this, R.layout.item_anime, filteredList);
                mAnimeEpisodesRecyclerView.setAdapter(mEpisodeAdapter);

                LinearLayoutManager manager = new LinearLayoutManager(AnimeDetailActivity.this, LinearLayoutManager.VERTICAL, true);
                mAnimeEpisodesRecyclerView.setLayoutManager(manager);
                mEpisodeAdapter.notifyDataSetChanged();
                return false;
            }


        });
        return true;
    }



    @Override
    public void onLowMemory() {
        super.onLowMemory();
        MenuActions.deleteCache(AnimeDetailActivity.this );
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        String mUrl = prefs.getString("updateurl", null);

        switch (item.getItemId()) {


            case R.id.action_rate_all_anime_menu:

                //Todo: NEED TO FINISH THIS!!!!!!!!
                MenuActions.actionRate(AnimeDetailActivity.this);
                return true;

            case R.id.action_donate_all_anime_menu:
                MenuActions.actionDonate(AnimeDetailActivity.this);
                return true;

            case R.id.action_contactUs_all_anime_menu:
                MenuActions.actionContact(AnimeDetailActivity.this);
                return true;

            case R.id.action_issue_all_anime_menu:
                MenuActions.actionIssue(AnimeDetailActivity.this);
                return true;

            case R.id.action_more_all_anime_menu:
                MenuActions.actionMoreApp(AnimeDetailActivity.this);
                return true;

            case R.id.action_request_all_anime_menu:
                MenuActions.actionRequest(AnimeDetailActivity.this);
                return true;

            case android.R.id.home:
                finish();
                return true;

            case R.id.action_share_all_anime_menu:
                MenuActions.actionShare(mAnimeName, mUrl, AnimeDetailActivity.this);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private boolean isCurrentAnimeFavorite() {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        Cursor cursor = null;
        String sql ="SELECT "+ FavouriteContract.FavouriteEntry.COLUMN_ANIME_NAME +" FROM "+ TABLE_NAME+" WHERE " + FavouriteContract.FavouriteEntry.COLUMN_ANIME_NAME +" = '"+mAnimeName + "'";
        cursor= db.rawQuery(sql,null);

        if(cursor.getCount()>0){
            //PID Found
            cursor.close();
            return true;
        }else{
            //PID Not Found
            cursor.close();
            return false;

        }

    }
}




