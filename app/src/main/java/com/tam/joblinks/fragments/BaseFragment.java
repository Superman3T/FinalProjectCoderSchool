package com.tam.joblinks.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.tam.joblinks.helpers.NetworkHelper;

/**
 * Created by toan on 4/27/2016.
 */
public abstract class BaseFragment extends Fragment {
    boolean hasInternet;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hasInternet = NetworkHelper.isOnline();
    }

//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        View view = inflater.inflate(R.layout.fragment_tweets_list, container, false);
//        ButterKnife.bind(this, view);
//        linearLayout = new LinearLayoutManager(getActivity());
//        return view;
//    }
//
//    @Override
//    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//
//        btTryAgain.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                getDefaultData();
//            }
//        });
//    }
//
    public abstract void getDefaultData();
}
