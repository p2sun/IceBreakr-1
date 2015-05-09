package com.lloydtorres.icebreakr;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.astuetz.PagerSlidingTabStrip;

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
        getSupportActionBar().hide();

        // Initialize the ViewPager and set an adapter
        tabsPager = (ViewPager) findViewById(R.id.pager);
        tabsAdapter = new LayoutAdapter(getSupportFragmentManager());
        tabsPager.setAdapter(tabsAdapter);
        // Bind the tabs to the ViewPager
        tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        tabs.setViewPager(tabsPager);
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
            // contacts
            if (position == 0) {
                return new NearbyFragment();
            }

            // call actions
            else {
                return new ProfileFragment();
            }
        }
    }
}
