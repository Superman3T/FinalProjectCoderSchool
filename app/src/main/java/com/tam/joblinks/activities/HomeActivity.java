package com.tam.joblinks.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.tam.joblinks.R;

import butterknife.ButterKnife;

public class HomeActivity extends BaseActivity {

//    @Bind(R.id.rvJobs)
//    RecyclerView rvJobs;
//    private boolean isLoadingItems = false;
//    private UserRepositoryInterface userRepo;
//    private JobRepositoryInterface jobRepo;
//    private BackendlessCollection<Job> BackendCollectionJobs;
//    private ArrayList<Job> totalJobs = new ArrayList<>();
//
//    private JobsAdapter adapter;
//
//    private LinearLayoutManager linearLayout;

    public HomeActivity() {
//        this.jobRepo = new JobRepository(this);
//        this.userRepo = new UserRepository(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setLogo(R.drawable.ic_account_white_24dp);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

//        adapter = new JobsAdapter(totalJobs);
//        rvJobs.setAdapter(adapter);
//        linearLayout = new LinearLayoutManager(this);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout();
            }
        });
//        rvJobs.addOnScrollListener(new EndlessRecyclerViewScrollListener(linearLayout) {
//            @Override
//            public void onLoadMore(int page, int totalItemsCount) {
//                getMoreData();
//            }
//
//
//        });
//        getDefaultData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        showToast(String.valueOf(item.getItemId()));
        switch (item.getItemId()) {
            case android.R.id.home:
                showToast("Click home page");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }



//    private void getMoreData() {
//        try {
//            if (!NetworkHelper.isOnline()) {
//                showToast(getString(R.string.cannot_connect_internet));
//                return;
//            }
//            BackendCollectionJobs.nextPage(new ProgressDialogCollectionCallBack<Job>(HomeActivity.this) {
//                @Override
//                public void handleResponse(BackendlessCollection<Job> nextPageJobs) {
//                    BackendCollectionJobs = nextPageJobs;
//                    addItemstoAdapter(nextPageJobs);
//                    // swipeContainer.setRefreshing(false);
//                    super.handleResponse(nextPageJobs);
//                }
//            });
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//    }
//
//    private void getDefaultData() {
//        try {
//            if (!NetworkHelper.isOnline()) {
//                showToast(getString(R.string.cannot_connect_internet));
//                return;
//            }
//            QueryOptions queryOptions = new QueryOptions();
//            queryOptions.setPageSize(Setting.PAGESIZE);
//            BackendlessDataQuery query = new BackendlessDataQuery(queryOptions);
//            this.jobRepo.pagingAsync(query, new ProgressDialogCollectionCallBack<Job>(this) {
//                @Override
//                public void handleResponse(BackendlessCollection<Job> jobsBackendlessCollection) {
//                    BackendCollectionJobs = jobsBackendlessCollection;
//                    addItemstoAdapter(jobsBackendlessCollection);
//                    super.handleResponse(jobsBackendlessCollection);
//                }
//            });
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//    }



    private void logout() {
//        this.userRepo.logoutAsync(new ProgressDialogCallBack<Void>(this) {
//
//            @Override
//            public void handleFault(BackendlessFault fault) {
//                if (fault.getCode().equals("3023")) // Unable to logout: not logged in (session expired, etc.)
//                    handleResponse(null);
//                else
//                    super.handleFault(fault);
//            }
//        });
    }

    private void saveJob() {

    }

}
