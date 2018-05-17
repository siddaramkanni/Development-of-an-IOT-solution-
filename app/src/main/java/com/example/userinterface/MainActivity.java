package com.example.userinterface;

import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private SectionsPageAdapter mSectionsPageAdapter;

    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate: Starting.");

        /////

        //////
        mSectionsPageAdapter = new SectionsPageAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        setupViewPager(mViewPager);

        ///
        mViewPager.setOffscreenPageLimit(20);
        ///

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
    }

    private void setupViewPager(ViewPager viewPager) {
        SectionsPageAdapter adapter = new SectionsPageAdapter(getSupportFragmentManager());
        adapter.addFragment(new Tab1Fragment(), "PING");
        adapter.addFragment(new Tab4Fragment(), "DISCOVERY");
        adapter.addFragment(new Tab5Fragment(), "JOIN");
        adapter.addFragment(new Tab6Fragment(), "RULES");
        adapter.addFragment(new Tab7Fragment(), "DEVICES CONNECTED");
        //adapter.addFragment(new Tab8Fragment(), "RULES SENT");
        adapter.addFragment(new Tab9Fragment(), "STATUS");
        adapter.addFragment(new Tab10Fragment(), "CONTROL");
        //adapter.addFragment(new Tab11Fragment(), "GET");
        adapter.addFragment(new Tab3Fragment(), "RX");
        adapter.addFragment(new Tab2Fragment(), "TX");


        viewPager.setAdapter(adapter);
    }

}
