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
import android.widget.Toast;

import com.backendless.BackendlessCollection;
import com.backendless.persistence.BackendlessDataQuery;
import com.backendless.persistence.QueryOptions;
import com.tam.joblinks.R;
import com.tam.joblinks.adapters.JobsAdapter;
import com.tam.joblinks.applications.JobApplication;
import com.tam.joblinks.helpers.NetworkHelper;
import com.tam.joblinks.helpers.ProgressDialogCollectionCallBack;
import com.tam.joblinks.helpers.StringHelper;
import com.tam.joblinks.interfaces.JobRepositoryInterface;
import com.tam.joblinks.interfaces.UserRepositoryInterface;
import com.tam.joblinks.listeners.EndlessRecyclerViewScrollListener;
import com.tam.joblinks.models.Job;
import com.tam.joblinks.models.JobFilter;
import com.tam.joblinks.repositories.JobRepository;
import com.tam.joblinks.repositories.UserRepository;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link JobsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link JobsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class JobsFragment extends BaseFragment {

    @Bind(R.id.rvJobs)
    RecyclerView rvJobs;

    @Bind(R.id.swipeContainer)
    SwipeRefreshLayout swipeContainer;

    private String TAG = JobsFragment.class.getSimpleName();
    private UserRepositoryInterface userRepo;
    private JobRepositoryInterface jobRepo;
    private BackendlessCollection<Job> backendlessCollection;
    private ArrayList<Job> totalJobs = new ArrayList<>();
    private JobFilter jobFilter;
    private JobsAdapter adapter;

    private LinearLayoutManager linearLayout;
    private String whereClause;
    public void setWhereClause(String whereClause) {
        this.whereClause = whereClause;
    }

    public JobsFragment() {
        // Required empty public constructor
        this.jobRepo = new JobRepository(getActivity());
        this.userRepo = new UserRepository(getActivity());
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment JobsFragment.
     */
    // TODO: Rename and change types and number of parameters
//    public static JobsFragment newInstance(JobFilter filter) {
//        JobsFragment fragment = new JobsFragment();
//        if (filter != null) {
//            Bundle args = new Bundle();
//            args.putParcelable(JobFilter.class.getSimpleName(), filter);
//            fragment.setArguments(args);
//        }
//        return fragment;
//    }

    public static JobsFragment newInstance() {
        JobsFragment fragment = new JobsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            jobFilter = getArguments().getParcelable(JobFilter.class.getSimpleName());
////            mParam1 = getArguments().getString(ARG_PARAM1);
////            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        super.onCreateView(inflater, container, savedInstanceState);
        this.view = inflater.inflate(R.layout.fragment_jobs, container, false);
        ButterKnife.bind(this, view);
        linearLayout = new LinearLayoutManager(getActivity());
        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvJobs.setItemAnimator(new SlideInUpAnimator());
        adapter = new JobsAdapter(totalJobs);
        rvJobs.setAdapter(adapter);
        linearLayout.setOrientation(LinearLayoutManager.VERTICAL);
//        linearLayout.scrollToPosition(0);
        rvJobs.setLayoutManager(linearLayout);
        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        rvJobs.addOnScrollListener(new EndlessRecyclerViewScrollListener(linearLayout) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                getMoreData(page);
            }
        });
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getDefaultData();
            }
        });
        getDefaultData();
    }

    public void getMoreData(int page) {
        try {
//            if (!NetworkHelper.isOnline()) {
//                Toast.makeText(getActivity(), getString(R.string.cannot_connect_internet), Toast.LENGTH_SHORT).show();
//                return;
//            }
            QueryOptions queryOptions = new QueryOptions();
            queryOptions.setPageSize(JobApplication.PAGESIZE);
            if (page > 0) {
                queryOptions.setOffset(JobApplication.PAGESIZE * page);
            }
            BackendlessDataQuery query = new BackendlessDataQuery(queryOptions);
//            String whereClause = "name = 'Jack Daniels'";
            if (StringHelper.isNullOrEmpty(this.whereClause)) {
                query.setWhereClause(this.whereClause);
            }
            this.jobRepo.pagingAsync(query, new ProgressDialogCollectionCallBack<Job>(getActivity()) {
                @Override
                public void handleResponse(BackendlessCollection<Job> jobsBackendlessCollection) {
                    backendlessCollection = jobsBackendlessCollection;
                    addItemstoAdapter(jobsBackendlessCollection);
                    swipeContainer.setRefreshing(false);
                    super.handleResponse(jobsBackendlessCollection);
                }
            });
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void getDefaultData() {
        try {
            if (!NetworkHelper.isOnline()) {
                Toast.makeText(getActivity(), getString(R.string.cannot_connect_internet), Toast.LENGTH_SHORT).show();
                return;
            }
            adapter.clear();
            totalJobs.clear();
            adapter.notifyDataSetChanged();
            QueryOptions queryOptions = new QueryOptions();
            queryOptions.setPageSize(JobApplication.PAGESIZE);

            BackendlessDataQuery query = new BackendlessDataQuery(queryOptions);
            if (StringHelper.isNullOrEmpty(this.whereClause)) {
                query.setWhereClause(whereClause);
            }
            this.jobRepo.pagingAsync(query, new ProgressDialogCollectionCallBack<Job>(getActivity()) {
                @Override
                public void handleResponse(BackendlessCollection<Job> jobsBackendlessCollection) {
                    backendlessCollection = jobsBackendlessCollection;
                    addItemstoAdapter(jobsBackendlessCollection);
                    swipeContainer.setRefreshing(false);
                    super.handleResponse(jobsBackendlessCollection);
                }
            });
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Adds more items to adapter and notifies Android that dataset has changed.
     *
     * @param nextPage list of new items
     */
    private void addItemstoAdapter(BackendlessCollection<Job> nextPage) {
//        totalJobs.addAll(nextPage.getCurrentPage());
//        adapter.notifyDataSetChanged();
        adapter.addRange(nextPage.getCurrentPage());
    }
}
