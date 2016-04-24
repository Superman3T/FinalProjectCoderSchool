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
import com.tam.joblinks.fragments.MainPageFragmentAdapter;
import com.tam.joblinks.fragments.ProfileFragment;

public class MainActivity extends AppCompatActivity {

    //    @Bind(R.id.toolbar)
//    Toolbar toolbar;
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    private FloatingActionButton fab;
    private View parent_view;
    private ActionBar actionbar;
    private MainPageFragmentAdapter adapter;
    private ProfileFragment profileFragment;


//    private PageFragmentAdapter adapter;

//    private PageFeedFragment f_feed;
//    private PageFriendFragment f_friend;
//    private PageMessageFragment f_message;
//    private PageNotifFragment f_notif;
//    private PageProfileFragment f_profile;
//    private static int[] imageResId = {
//            R.drawable.tab_feed,
//            R.drawable.tab_friend,
//            R.drawable.tab_chat,
//            R.drawable.tab_notif,
//            R.drawable.tab_profile
//    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);;parent_view = findViewById(android.R.id.content);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        addTabs();

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        addTabIcons();

    }

    private void addTabs() {
        adapter = new MainPageFragmentAdapter(getSupportFragmentManager());
        profileFragment = ProfileFragment.newInstance();
        adapter.addFragment(profileFragment, getString(R.string.tab_profile));
        viewPager.setAdapter(adapter);
    }

    private void addTabIcons() {
        tabLayout.getTabAt(0).setIcon(R.drawable.tab_profile);
    }
}
