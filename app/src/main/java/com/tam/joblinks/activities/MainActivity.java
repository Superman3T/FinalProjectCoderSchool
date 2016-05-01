package com.tam.joblinks.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.Nameable;
import com.tam.joblinks.R;
import com.tam.joblinks.adapters.MainPageFragmentAdapter;
import com.tam.joblinks.applications.JobApplication;
import com.tam.joblinks.fragments.JobsFragment;
import com.tam.joblinks.fragments.MessageFragment;
import com.tam.joblinks.fragments.ProfileFragment;
import com.tam.joblinks.helpers.DefaultCallback;
import com.tam.joblinks.helpers.SessionPreferencesHelper;
import com.tam.joblinks.helpers.StringHelper;
import com.tam.joblinks.interfaces.UserRepositoryInterface;
import com.tam.joblinks.repositories.UserRepository;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {


    private FloatingActionButton fab;
    private View parent_view;
    private ActionBar actionbar;
    private MainPageFragmentAdapter pagerAdapter;
    private ProfileFragment profileFragment;
    private JobsFragment jobsFragment;
    private MessageFragment messageFragment;
    private Drawer result = null;

    @Bind(R.id.tabs)
    TabLayout tabLayout;

    @Bind(R.id.viewpager)
    ViewPager viewPager;

    @Bind(R.id.fabSearch)
    FloatingActionButton fabSearch;

    Toolbar toolbar;
    private UserRepositoryInterface userRepo;

    public MainActivity() {
        userRepo = new UserRepository(this);
    }

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
        initToolbar();

        buildDrawnerMenu(toolbar);
        addTabs();
        addTabIcons();
        setupTabClick();

        fabSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast("pp");
            }
        });
    }

    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
    }

    private void buildDrawnerMenu(Toolbar toolbar) {
        String fullName = "";
//        AccountHeader headerResult = new AccountHeaderBuilder()
//                .withActivity(this)
//                .withHeaderBackground(R.drawable.header)
//                .addProfiles(
//                        new ProfileDrawerItem().withName(fullName)
//                                .withEmail(JobApplication.currentMail).withIcon(getResources().getDrawable(R.drawable.ic_people))
//                )
//                .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
//                    @Override
//                    public boolean onProfileChanged(View view, IProfile profile, boolean currentProfile) {
//                        return false;
//                    }
//                })
//                .build();
        PrimaryDrawerItem itemHome = new PrimaryDrawerItem().withName(R.string.drawer_item_home)
                .withIcon(getResources().getDrawable(R.drawable.ic_home_black_24dp));
        PrimaryDrawerItem itemSettings = new PrimaryDrawerItem().withName(R.string.drawer_item_settings);
        PrimaryDrawerItem itemApplyJob = new PrimaryDrawerItem().withName(R.string.drawer_item_applied_job)
                .withIcon(getResources().getDrawable(R.drawable.ic_nav_saved));
        PrimaryDrawerItem itemSaveJob = new PrimaryDrawerItem().withName(R.string.drawer_item_saved_job)
                .withIcon(getResources().getDrawable(R.drawable.ic_nav_saved));
        List<IDrawerItem> items = new ArrayList<>();

//        Drawer result = new DrawerBuilder()
//                //.withAccountHeader(headerResult)
//                .withActivity(this)
//                .withToolbar(toolbar)
//                .addDrawerItems(
//                        itemHome,
//                        new DividerDrawerItem()
//                        ,
//                        itemSettings
//                )
//                .addDrawerItems()
//                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
//                    @Override
//                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
//                        // do something with the clicked item :D
//                        Toast.makeText(MainActivity.this, ((Nameable) drawerItem).getName().getText(MainActivity.this), Toast.LENGTH_SHORT).show();
//                        return false;
//                    }
//                }).build();
        DrawerBuilder drawerBuilder = new DrawerBuilder()
                //.withAccountHeader(headerResult)
                .withActivity(this)
                .withToolbar(toolbar)
                .addDrawerItems(
                        itemHome,
                        new DividerDrawerItem(),
                        itemApplyJob,
                        itemSaveJob,
                        new DividerDrawerItem()
                )
                .addDrawerItems()
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        // do something with the clicked item :D
                        //Toast.makeText(MainActivity.this, ((Nameable) drawerItem).getName().getText(MainActivity.this), Toast.LENGTH_SHORT).show();
                        String name = ((Nameable) drawerItem).getName().getText(MainActivity.this);
                        onActionDrawnerItem(name);
                        return true;
                    }
                });
        PrimaryDrawerItem logInOutItem = null;
        PrimaryDrawerItem registerItem = null;
        if (StringHelper.isNullOrEmpty(JobApplication.currentMail)) { // not login yet
            logInOutItem = new PrimaryDrawerItem().withName(R.string.drawer_item_login);
            registerItem = new PrimaryDrawerItem().withName(R.string.drawer_item_register);
            drawerBuilder.addDrawerItems(logInOutItem);
            drawerBuilder.addDrawerItems(registerItem);
        } else {
            logInOutItem = new PrimaryDrawerItem().withName(R.string.drawer_item_logout);
            drawerBuilder.addDrawerItems(logInOutItem);
        }
        drawerBuilder.build();
    }

    private void onActionDrawnerItem(String name) {
        if (name.equals(getString(R.string.drawer_item_logout))) {
            JobApplication.currentMail = "";

            this.userRepo.logoutAsync(new DefaultCallback<Void>(MainActivity.this) {
                @Override
                public void handleResponse(Void response) {
                    super.handleResponse(response);
                    JobApplication.currentMail = "";
                    SessionPreferencesHelper session = new SessionPreferencesHelper(MainActivity.this);
                    session.logoutUser();
                    finish();
                    startActivity(getIntent());
                }
            });
//            if (result != null) {
//                result.closeDrawer();
//                buildDrawnerMenu(toolbar);
//            }
        } else if (name.equals(getString(R.string.drawer_item_saved_job))) {
            Intent intent = new Intent(MainActivity.this, JobStatusActivity.class);
            intent.putExtra(JobApplication.USER_ACTION_JOB, JobApplication.SAVE_JOB);
            startActivity(intent);
        } else if (name.equals(getString(R.string.drawer_item_applied_job))) {
            Intent intent = new Intent(MainActivity.this, JobStatusActivity.class);
            intent.putExtra(JobApplication.USER_ACTION_JOB, JobApplication.APPLY_JOB);
            startActivity(intent);
        } else if (name.equals(getString(R.string.drawer_item_register))) {
            Intent intent = new Intent(MainActivity.this, RegisterAccountActivity.class);
            startActivity(intent);

        } else if (name.equals(getString(R.string.drawer_item_login))) {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        }
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
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }
}
