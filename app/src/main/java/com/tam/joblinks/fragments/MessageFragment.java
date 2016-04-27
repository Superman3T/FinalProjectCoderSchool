package com.tam.joblinks.fragments;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.backendless.BackendlessCollection;
import com.tam.joblinks.R;
import com.tam.joblinks.adapters.JobsAdapter;
import com.tam.joblinks.interfaces.JobRepositoryInterface;
import com.tam.joblinks.interfaces.UserRepositoryInterface;
import com.tam.joblinks.listeners.EndlessRecyclerViewScrollListener;
import com.tam.joblinks.models.Job;
import com.tam.joblinks.repositories.UserRepository;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MessageFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MessageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MessageFragment extends Fragment {


    @Bind(R.id.rvMessages)
    RecyclerView rvMessages;

    @Bind(R.id.swipeContainerMessage)
    SwipeRefreshLayout swipeContainerMessage;

    private String TAG = MessageFragment.class.getSimpleName();
    private UserRepositoryInterface userRepo;
    private BackendlessCollection<Job> backendlessCollection;
    private ArrayList<Job> totalJobs = new ArrayList<>();

    private JobsAdapter adapter;

    private LinearLayoutManager linearLayout;

    public MessageFragment() {
        // Required empty public constructor
        this.userRepo = new UserRepository(getActivity());
    }

    public static MessageFragment newInstance() {
        MessageFragment fragment = new MessageFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_jobs, container, false);
        ButterKnife.bind(this, view);
        linearLayout = new LinearLayoutManager(getActivity());
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvMessages.setItemAnimator(new SlideInUpAnimator());
        adapter = new JobsAdapter(totalJobs);
        rvMessages.setAdapter(adapter);
        linearLayout.setOrientation(LinearLayoutManager.VERTICAL);
        linearLayout.scrollToPosition(0);
        rvMessages.setLayoutManager(linearLayout);
        // Configure the refreshing colors
        swipeContainerMessage.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        rvMessages.addOnScrollListener(new EndlessRecyclerViewScrollListener(linearLayout) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                getMoreData();
            }
        });
        swipeContainerMessage.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getDefaultData();
            }
        });
        getDefaultData();
    }

    private void getMoreData() {
    }


    private void getDefaultData() {

    }
}
