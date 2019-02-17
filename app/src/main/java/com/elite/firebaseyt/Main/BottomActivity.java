package com.elite.firebaseyt.Main;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.elite.firebaseyt.Download.DownloadActivity;
import com.elite.firebaseyt.FavoriteActivity;
import com.elite.firebaseyt.PlayList.PlaylistActivity;
import com.elite.firebaseyt.R;

public class BottomActivity extends AppCompatActivity {

    private ButtomNavHome buttomNavHome;
    private ButtomNavCategory buttomNavCategory;
    private ButtomNavPopular buttomNavPopular;
    private ButtomNavUpload buttomNavUpload;

    private BottomNavigationView mbottomNavigationView;
    private FrameLayout mframeLayout;

    private DrawerLayout mDrawerLayout;
    public static NavigationView navigationView;
    int navigationselected = 0;
    Toolbar toolbarmain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom);

//        toolbarmain = (Toolbar) findViewById(R.id.toolbar_main);
//        ImageView search = (ImageView) findViewById(R.id.toolbar_search);
//        ImageView upload = (ImageView) findViewById(R.id.toolbar_upload);
////        if (toolbarmain != null) {
//            setSupportActionBar(toolbarmain);
//            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//            getSupportActionBar().setDisplayShowTitleEnabled(false);
//            search.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Toast.makeText(BottomActivity.this, "Search", Toast.LENGTH_SHORT).show();
//                }
//            });
//            upload.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Toast.makeText(BottomActivity.this, "Uplaod", Toast.LENGTH_SHORT).show();
//                }
//            });
//        }

        buttomNavHome = new ButtomNavHome();
        buttomNavCategory = new ButtomNavCategory();
        buttomNavPopular = new ButtomNavPopular();
        buttomNavUpload = new ButtomNavUpload();

        mDrawerLayout = findViewById(R.id.drawer_layout);
        NavigationViewFunc();

        setFragment(buttomNavHome);
        mframeLayout = (FrameLayout) findViewById(R.id.id_framelayout);
        mbottomNavigationView = (BottomNavigationView) findViewById(R.id.id_buttom_navigation);

        setFragment(buttomNavHome);
        mbottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.id_home:
                        setFragment(buttomNavHome);
                        return true;

                    case R.id.id_category:
                        setFragment(buttomNavCategory);
                        return true;

                    case R.id.id_upload:
                        setFragment(buttomNavUpload);
                        return true;

                    case R.id.id_user:
                        setFragment(buttomNavPopular);
                        return true;

                    default:
                        return false;
                }
            }
        });
    }

    private void setFragment(Fragment fragment) {

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.id_framelayout, fragment);
        fragmentTransaction.commit();
    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case android.R.id.home:
//                mDrawerLayout.openDrawer(GravityCompat.START);
//                return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }

    @SuppressLint("ResourceAsColor")
    private void NavigationViewFunc() {
        mDrawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);

        navigationView.getMenu().getItem(0).setChecked(true);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                        menuItem.setChecked(true);
//                        Toast.makeText(context, "" + menuItem.getItemId(), Toast.LENGTH_SHORT).show();
                        switch (menuItem.getItemId()) {

                            case R.id.id_dra_favorite: {
                                startActivity(new Intent(BottomActivity.this, FavoriteActivity.class));
                                break;
                            }

                            case R.id.id_dra_download: {
                                navigationselected = 0;
                                startActivity(new Intent(BottomActivity.this, DownloadActivity.class));
                                break;
                            }

                            case R.id.id_dra_playslist: {
                                navigationselected = 0;
                                startActivity(new Intent(BottomActivity.this, PlaylistActivity.class));
                                break;
                            }

                            case R.id.id_dra_share: {
                                navigationselected = 0;
                                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                                sharingIntent.setType("text/plain");
                                String shareBody = "Download this Amezing App.\n Where you can watch the 30 second videos...and also Download it and share it. \n\n" +
                                        "https://play.google.com/store/apps/details?id=" + getPackageName();
                                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject Here");
                                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                                startActivity(Intent.createChooser(sharingIntent, "Share via"));
                                break;
                            }

                            case R.id.id_dra_rateus: {
                                navigationselected = 0;
                                Intent rateIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + getApplicationContext().getPackageName()));
                                startActivity(rateIntent);
                                break;
                            }
                        }
                        mDrawerLayout.closeDrawers();
                        return true;
                    }
                });
//        ActionBar actionbar = getSupportActionBar();
//        if (actionbar != null) {
//            actionbar.setDisplayHomeAsUpEnabled(true);
//            actionbar.setHomeAsUpIndicator(R.drawable.share);
//        }
    }

}
