package app.rayyan.com.bcma.myapplication;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.view.menu.MenuAdapter;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.github.msarhan.ummalqura.calendar.UmmalquraCalendar;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.SectionDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import java.text.Normalizer;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import app.rayyan.com.bcma.myapplication.MainPages.HomeFragment;
import app.rayyan.com.bcma.myapplication.MainPages.MorePages.BCMA_Branches;
import app.rayyan.com.bcma.myapplication.MainPages.MorePages.FormActivity;
import app.rayyan.com.bcma.myapplication.MainPages.MorePages.NewsActivity;
import app.rayyan.com.bcma.myapplication.MainPages.MorePages.ProjectActivity;
import app.rayyan.com.bcma.myapplication.Utils.Codes;
import app.rayyan.com.bcma.myapplication.Utils.MenuActions;

public class Main_Activity extends AppCompatActivity {

    private CharSequence mCurrentUserDate;
    private String mCurrentHijrihDate;
    private Drawer result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar mActionBar = getSupportActionBar();

//
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
//                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        drawer.addDrawerListener(toggle);
//        toggle.syncState();

        AccountHeader headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.drawable.side_nav_bar)
                .addProfiles(
                        new ProfileDrawerItem().withName(getString(R.string.bcma_full)).withEmail(getString(R.string.slogan)).withIcon(getResources().getDrawable(R.mipmap.ic_launcher_round)),
                        new ProfileDrawerItem().withName(getString(R.string.bcma_full)).withEmail(getString(R.string.slogan_2)).withIcon(getResources().getDrawable(R.mipmap.ic_launcher_round)),
                        new ProfileDrawerItem().withName(getString(R.string.bcma_full)).withEmail(getString(R.string.slogan_3)).withIcon(getResources().getDrawable(R.mipmap.ic_launcher_round)),
                        new ProfileDrawerItem().withName(getString(R.string.bcma_full)).withEmail(getString(R.string.slogan__4)).withIcon(getResources().getDrawable(R.mipmap.ic_launcher_round))
                )
                .build();

        new DrawerBuilder().withActivity(this).build();


        result = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .withAccountHeader(headerResult)
                .addDrawerItems(
                        //Todo : Navigation Menu Brothaa!!

                        new PrimaryDrawerItem().withName(getString(R.string.title_home)).withIcon(R.drawable.ic_home_black_24dp_nav).withIdentifier(1).withSelectable(false),
                        new PrimaryDrawerItem().withName(getString(R.string.news_act_title)).withIcon(R.drawable.news_icon_noti_nav).withIdentifier(2).withSelectable(false),


                        new SectionDrawerItem().withName(getString(R.string.app_name)).withDivider(true),
                        new PrimaryDrawerItem().withName(getString(R.string.branches)).withIcon(R.drawable.ic_branches_nav).withIdentifier(3).withSelectable(false),
                        new PrimaryDrawerItem().withName(getString(R.string.about_bcma)).withIcon(R.drawable.ic_about_bcma_nav).withSelectable(false)
                                .withSubItems(
                                        new SecondaryDrawerItem().withName(getString(R.string.our_team)).withIdentifier(4).withSelectable(false),
                                        new SecondaryDrawerItem().withName(getString(R.string.women_chapter)).withIdentifier(5).withSelectable(false),
                                        new SecondaryDrawerItem().withName(getString(R.string.current_projects)).withIdentifier(6).withSelectable(false),
                                        new SecondaryDrawerItem().withName(getString(R.string.forms_pub)).withIdentifier(7).withSelectable(false),
                                        new SecondaryDrawerItem().withName(getString(R.string.program_service)).withIdentifier(8).withSelectable(false)
                                ),

                        new SectionDrawerItem().withName(getString(R.string.general)).withDivider(true),

                        new PrimaryDrawerItem().withName(getString(R.string.settings)).withIcon(R.drawable.ic_settings).withIdentifier(9).withSelectable(false),
                        new PrimaryDrawerItem().withName(getString(R.string.update)).withIcon(R.drawable.ic_update_24dp).withIdentifier(10).withSelectable(false),
                        new PrimaryDrawerItem().withName(getString(R.string.share)).withIcon(R.drawable.ic_share).withIdentifier(11).withSelectable(false),
                        new PrimaryDrawerItem().withName(getString(R.string.rate_us)).withIcon(R.drawable.ic_rate_24dp).withIdentifier(12).withSelectable(false),
                        new PrimaryDrawerItem().withName(getString(R.string.report_bug)).withIcon(R.drawable.ic_bug).withIdentifier(13).withSelectable(false),
                        new PrimaryDrawerItem().withName(getString(R.string.contact_us)).withIcon(R.drawable.ic_contact).withIdentifier(14).withSelectable(false)

                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        // do something with the clicked item :D
                        long i = drawerItem.getIdentifier();
                        if (i == 1) {
                            return true;
                        } else if (i == 2) {
                            Intent intent2 = new Intent(Main_Activity.this, NewsActivity.class);
                            startActivity(intent2);
                            return true;
                        } else if (i == 3) {
                            Intent intent = new Intent(Main_Activity.this, BCMA_Branches.class);
                            startActivity(intent);
                            return true;
                        } else if (i == 4) {
                            Intent intent1234 = new Intent(Main_Activity.this, BCMA_Branches.class);
                            startActivity(intent1234);
                            return true;
                        } else if (i == 5) {
                            Toast.makeText(Main_Activity.this, getResources().getString(R.string.page_coming), Toast.LENGTH_SHORT).show();
                            return true;
                        } else if (i == 6) {
                            Intent inten12 = new Intent(Main_Activity.this, ProjectActivity.class);
                            startActivity(inten12);
                            return true;
                        } else if (i == 7) {
                            Intent intent123 = new Intent(Main_Activity.this, FormActivity.class);
                            startActivity(intent123);
                            return true;
                        } else if (i == 8) {
                            Toast.makeText(Main_Activity.this, getResources().getString(R.string.page_coming), Toast.LENGTH_SHORT).show();
                            return true;
                        } else if (i == 9) {
                            Intent intent124 = new Intent(Main_Activity.this, SettingsActivity.class);
                            startActivity(intent124);
                            return true;
                        } else if (i == 10) {
                            MenuActions.updateApp(Main_Activity.this, Codes.GOOGLE_PLAY_STORE_URL);
                            return true;
                        } else if (i == 11) {
                            MenuActions.shareUs(Main_Activity.this, Codes.GOOGLE_PLAY_STORE_URL);
                            return true;
                        } else if (i == 12) {
                            MenuActions.rateUs(Main_Activity.this);
                            return true;
                        } else if (i == 13) {
                            MenuActions.reportIssue(Main_Activity.this);
                            return true;
                        } else if (i == 14) {
                            MenuActions.contactUs(Main_Activity.this);
                            return true;
                        } else {
                            return true;
                        }

                    }
                })
                .build();


        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        result.getActionBarDrawerToggle().setDrawerIndicatorEnabled(true);



        setUpDate(mActionBar);



        switchToFragmentHome();
//
//        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
//        navigationView.setNavigationItemSelectedListener(this);




    }

    private void setUpDate(ActionBar mActionBar) {
        Date currentTime = Calendar.getInstance().getTime();


        mCurrentUserDate = android.text.format.DateFormat.format("EEEE, MMMM dd", currentTime);

        UmmalquraCalendar cal = new UmmalquraCalendar();
        cal.get(Calendar.YEAR);         // 1436
        cal.get(Calendar.MONTH);        // 5 <=> Jumada al-Akhirah
        cal.get(Calendar.DAY_OF_MONTH);

        int mHijrihDate = cal.get(Calendar.DAY_OF_MONTH);
        String mMonthName = cal.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH);
        int mHijrihYear = cal.get(Calendar.YEAR);

        mCurrentHijrihDate = "" + mHijrihDate + " " + mMonthName + ", " + mHijrihYear + " AH";

        mActionBar.setTitle(mCurrentUserDate);
        mActionBar.setSubtitle(mCurrentHijrihDate);

    }

//    @Override
//    public void onBackPressed() {
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        if (drawer.isDrawerOpen(GravityCompat.START)) {
//            drawer.closeDrawer(GravityCompat.START);
//        } else {
//            super.onBackPressed();
//        }
//    }

    @Override
    public void onBackPressed() {
        //handle the back press :D close the drawer first and if the drawer is closed close the activity
        if (result != null && result.isDrawerOpen()) {
            result.closeDrawer();
        } else {
            super.onBackPressed();
        }
    }

//    @Override
//    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//        // Handle navigation view item clicks here.
//        int id = item.getItemId();
//
//        if (id == R.id.nav_home) {
//            // Handle the camera action
//            switchToFragmentHome();
//        } else if (id == R.id.nav_news) {
//            Intent intent = new Intent(Main_Activity.this, NewsActivity.class);
//            startActivity(intent);
//
//        } else if (id == R.id.nav_share) {
//            Toast.makeText(this, "Share the APP!!", Toast.LENGTH_SHORT).show();
//
//        } else if (id == R.id.nav_branches) {
//            Intent intent = new Intent(Main_Activity.this, BCMA_Branches.class);
//            startActivity(intent);
//
//        } else if (id == R.id.nav_about) {
//            Toast.makeText(this, "About BCMA", Toast.LENGTH_SHORT).show();
//
//        } else if (id == R.id.nav_settings) {
//            Toast.makeText(this, "Settings", Toast.LENGTH_SHORT).show();
//            Intent intent = new Intent(Main_Activity.this, SettingsActivity.class);
//            startActivity(intent);
//
//        } else if (id == R.id.nav_rate) {
//            Toast.makeText(this, "Rate Us", Toast.LENGTH_SHORT).show();
//
//        } else if (id == R.id.nav_report) {
//            Toast.makeText(this, "Report Issue", Toast.LENGTH_SHORT).show();
//
//        } else if (id == R.id.nav_contact) {
//            Toast.makeText(this, "Contact US!!", Toast.LENGTH_SHORT).show();
//
//        }
//
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        drawer.closeDrawer(GravityCompat.START);
//        return true;
//    }



    public void switchToFragmentHome() {
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.container, new HomeFragment()).commit();
    }

}
