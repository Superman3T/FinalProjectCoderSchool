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
import com.tam.joblinks.adapters.MainPageFragmentAdapter;
import com.tam.joblinks.fragments.MessageFragment;
import com.tam.joblinks.fragments.ProfileFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {


    private FloatingActionButton fab;
    private View parent_view;
    private ActionBar actionbar;
    private MainPageFragmentAdapter pagerAdapter;
    private ProfileFragment profileFragment;
    private JobsFragment jobsFragment;
    private MessageFragment messageFragment;

    @Bind(R.id.tabs)
    TabLayout tabLayout;

    @Bind(R.id.viewpager)
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(false);

        addTabs();
        addTabIcons();
        setupTabClick();
    }
    private void addTabs() {
        pagerAdapter = new MainPageFragmentAdapter(getSupportFragmentManager(), MainActivity.this);
        profileFragment = ProfileFragment.newInstance();
        jobsFragment = JobsFragment.newInstance();
        messageFragment = MessageFragment.newInstance();
        pagerAdapter.addFragment(jobsFragment, getString(R.string.tab_home));
        pagerAdapter.addFragment(messageFragment, getString(R.string.tab_message));
        pagerAdapter.addFragment(profileFragment, getString(R.string.tab_profile));
        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void addTabIcons() {
        pagerAdapter.setTabIcons(tabLayout);
    }
    private void setupTabClick() {
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                viewPager.setCurrentItem(position);
                actionbar.setTitle(pagerAdapter.getTitle(position));
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) { }
            @Override
            public void onTabReselected(TabLayout.Tab tab) { }
        });
    }
}
