package com.tam.joblinks.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.backendless.BackendlessCollection;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.BackendlessDataQuery;
import com.backendless.persistence.QueryOptions;
import com.tam.joblinks.R;
import com.tam.joblinks.adapters.JobsAdapter;
import com.tam.joblinks.applications.Setting;
import com.tam.joblinks.helpers.NetworkHelper;
import com.tam.joblinks.helpers.ProgressDialogCallBack;
import com.tam.joblinks.helpers.ProgressDialogCollectionCallBack;
import com.tam.joblinks.interfaces.JobRepositoryInterface;
import com.tam.joblinks.interfaces.UserRepositoryInterface;
import com.tam.joblinks.listeners.EndlessRecyclerViewScrollListener;
import com.tam.joblinks.models.Job;
import com.tam.joblinks.repositories.JobRepository;
import com.tam.joblinks.repositories.UserRepository;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class HomeActivity extends BaseActivity {

    @Bind(R.id.rvJobs)
    RecyclerView rvJobs;
//    private boolean isLoadingItems = false;
    private UserRepositoryInterface userRepo;
    private JobRepositoryInterface jobRepo;
    private BackendlessCollection<Job> BackendCollectionJobs;
    private ArrayList<Job> totalJobs = new ArrayList<>();

    private JobsAdapter adapter;

    private LinearLayoutManager linearLayout;

    public HomeActivity() {
        this.jobRepo = new JobRepository(this);
        this.userRepo = new UserRepository(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ButterKnife.bind(this);
        adapter = new JobsAdapter(totalJobs);
        rvJobs.setAdapter(adapter);
        linearLayout = new LinearLayoutManager(this);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout();
            }
        });
        rvJobs.addOnScrollListener(new EndlessRecyclerViewScrollListener(linearLayout) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                getMoreData();
            }


        });
        getDefaultData();
    }

    private void getMoreData() {
        try {
            if (!NetworkHelper.isOnline()) {
                showToast(getString(R.string.cannot_connect_internet));
                return;
            }
            BackendCollectionJobs.nextPage(new ProgressDialogCollectionCallBack<Job>(HomeActivity.this) {
                @Override
                public void handleResponse(BackendlessCollection<Job> nextPageJobs) {
                    BackendCollectionJobs = nextPageJobs;
                    addItemstoAdapter(nextPageJobs);
                    // swipeContainer.setRefreshing(false);
                    super.handleResponse(nextPageJobs);
                }
            });
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void getDefaultData() {
        try {
            if (!NetworkHelper.isOnline()) {
                showToast(getString(R.string.cannot_connect_internet));
                return;
            }
            QueryOptions queryOptions = new QueryOptions();
            queryOptions.setPageSize(Setting.PAGESIZE);
            BackendlessDataQuery query = new BackendlessDataQuery(queryOptions);
            this.jobRepo.pagingAsync(query, new ProgressDialogCollectionCallBack<Job>(this) {
                @Override
                public void handleResponse(BackendlessCollection<Job> jobsBackendlessCollection) {
                    BackendCollectionJobs = jobsBackendlessCollection;
                    addItemstoAdapter(jobsBackendlessCollection);
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

    private void logout() {
        this.userRepo.logoutAsync(new ProgressDialogCallBack<Void>(this) {

            @Override
            public void handleFault(BackendlessFault fault) {
                if (fault.getCode().equals("3023")) // Unable to logout: not logged in (session expired, etc.)
                    handleResponse(null);
                else
                    super.handleFault(fault);
            }
        });
    }

    private void saveJob() {

    }

}
