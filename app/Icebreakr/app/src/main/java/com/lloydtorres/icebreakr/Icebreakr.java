package com.lloydtorres.icebreakr;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.os.RemoteException;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.astuetz.PagerSlidingTabStrip;
import com.estimote.sdk.Beacon;
import com.estimote.sdk.BeaconManager;

import java.util.Collections;

/**
 * Created by Lloyd on 2015-05-09.
 */
public class Icebreakr extends AppCompatActivity {

    // variables used for tabs
    private PagerSlidingTabStrip tabs;
    private ViewPager tabsPager;
    private LayoutAdapter tabsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_icebreakr);

        // get rid of shadow from action bar
        getSupportActionBar().setElevation(0);

        // Initialize the ViewPager and set an adapter
        tabsPager = (ViewPager) findViewById(R.id.pager);
        tabsAdapter = new LayoutAdapter(getSupportFragmentManager());
        tabsPager.setAdapter(tabsAdapter);
        // Bind the tabs to the ViewPager
        tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        tabs.setViewPager(tabsPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.scan_menu, menu);
        MenuItem refreshItem = menu.findItem(R.id.refresh);
        refreshItem.setActionView(R.layout.actionbar_indeterminate_progress);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // For formatting the tab slider
    public class LayoutAdapter extends FragmentPagerAdapter {

        private final String[] TITLES = {getString(R.string.nearby), getString(R.string.profile)};

        public LayoutAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return TITLES[position];
        }

        @Override
        public int getCount() {
            return TITLES.length;
        }

        @Override
        public Fragment getItem(int position) {
            // nearby people
            if (position == 0) {
                return new NearbyFragment();
            }

            // profile
            else {
                return new ProfileFragment();
            }
        }
    }
}
