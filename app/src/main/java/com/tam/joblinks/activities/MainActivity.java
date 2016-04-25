package com.tam.joblinks.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.tam.joblinks.R;
import com.tam.joblinks.fragments.JobsFragment;
import com.tam.joblinks.fragments.MainPageFragmentAdapter;
import com.tam.joblinks.fragments.ProfileFragment;

public class MainActivity extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager viewPager;

    private FloatingActionButton fab;
    private View parent_view;
    private ActionBar actionbar;
    private MainPageFragmentAdapter adapter;
    private ProfileFragment profileFragment;
    private JobsFragment jobsFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

//        fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(false);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        addTabs();

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        addTabIcons();
        setupTabClick();
    }
    private void addTabs() {
        adapter = new MainPageFragmentAdapter(getSupportFragmentManager());
        profileFragment = ProfileFragment.newInstance();
        jobsFragment = JobsFragment.newInstance();
        adapter.addFragment(jobsFragment, getString(R.string.tab_home));
        adapter.addFragment(profileFragment, getString(R.string.tab_profile));
        viewPager.setAdapter(adapter);
    }

    private void addTabIcons() {
        tabLayout.getTabAt(0).setIcon(R.drawable.tab_jobs);
        tabLayout.getTabAt(1).setIcon(R.drawable.tab_profile);
    }
    private void setupTabClick() {
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                viewPager.setCurrentItem(position);
                actionbar.setTitle(adapter.getTitle(position));
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) { }
            @Override
            public void onTabReselected(TabLayout.Tab tab) { }
        });
    }
}
