package com.tam.joblinks.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.tam.joblinks.R;
import com.tam.joblinks.activities.PublishJobActivity;
import com.tam.joblinks.helpers.NetworkHelper;

/**
 * Created by toan on 4/27/2016.
 */
public abstract class BaseFragment extends Fragment {
    boolean hasInternet;
    protected View view;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hasInternet = NetworkHelper.isOnline();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main_fragment, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_publish_job:
                //Snackbar.make(view, item.getTitle() + " Clicked", Snackbar.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), PublishJobActivity.class);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // activate fragment menu
        setHasOptionsMenu(true);
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
