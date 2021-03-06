package com.tam.joblinks.fragments;

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
import com.tam.joblinks.adapters.MessageAdapter;
import com.tam.joblinks.interfaces.UserRepositoryInterface;
import com.tam.joblinks.listeners.EndlessRecyclerViewScrollListener;
import com.tam.joblinks.models.Job;
import com.tam.joblinks.models.Message;
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
public class MessageFragment extends BaseFragment {


    @Bind(R.id.rvMessages)
    RecyclerView rvMessages;

    @Bind(R.id.swipeContainerMessage)
    SwipeRefreshLayout swipeContainerMessage;

    private String TAG = MessageFragment.class.getSimpleName();
    private UserRepositoryInterface userRepo;
    private BackendlessCollection<Job> backendlessCollection;
    private ArrayList<Message> totalMessages = new ArrayList<>();

    private MessageAdapter adapter;

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
        super.onCreateView(inflater, container, savedInstanceState);
        this.view = inflater.inflate(R.layout.fragment_message, container, false);
        ButterKnife.bind(this, view);
        linearLayout = new LinearLayoutManager(getActivity());
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvMessages.setItemAnimator(new SlideInUpAnimator());
        adapter = new MessageAdapter(totalMessages);
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
                getMoreData(page);
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

    public void getDefaultData() {

    }

    public void getMoreData(int page) {

    }
}
