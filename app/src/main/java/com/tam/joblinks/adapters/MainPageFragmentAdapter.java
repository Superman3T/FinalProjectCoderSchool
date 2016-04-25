package com.tam.joblinks.adapters;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.tam.joblinks.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by toan on 4/24/2016.
 */
public class MainPageFragmentAdapter extends FragmentPagerAdapter {

    private final List<Fragment> fragments = new ArrayList<>();
    private final List<String> fragmentTitles = new ArrayList<>();
    private static int[] imageResId = {
            R.drawable.tab_jobs,
            R.drawable.tab_notif,
            R.drawable.tab_profile,
            R.drawable.tab_chat,
            R.drawable.tab_friend
    };
    Context context;

    public MainPageFragmentAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    public void addFragment(Fragment fragment, String title) {
        fragments.add(fragment);
        fragmentTitles.add(title);
    }

    //https://guides.codepath.com/android/sliding-tabs-with-pagerslidingtabstrip

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return null;
    }

    public String getTitle(int position) {
        return fragmentTitles.get(position);
    }

    private View getTabView(int position) {
        // Given you have a custom layout in `res/layout/custom_tab.xml` with a TextView and ImageView
        View v = LayoutInflater.from(context).inflate(R.layout.custome_tab_item, null);
        ImageView img = (ImageView) v.findViewById(R.id.ivTabIcon);
        img.setImageResource(imageResId[position]);
        return v;
    }

    public void setTabIcons(TabLayout tabLayout) {
        for (int i = 0; i < fragments.size(); i++) {
            tabLayout.getTabAt(i).setCustomView(getTabView(i));
        }
    }
}
