package com.tam.joblinks.applications;

import android.content.Context;

import com.tam.joblinks.adapters.JobsAdapter;

/**
 * Created by toan on 4/21/2016.
 */
public class Setting {
    public static final int PAGESIZE = 12;
    Context context;
    JobsAdapter jobsAdapter;
    public Setting(Context context, JobsAdapter jobsAdapter) {
        this.context = context;
        this.jobsAdapter = jobsAdapter;
    }
    private boolean firstReponse = true;

//    public ProgressDialogCollectionCallBack<Job> pagingAsyncCallback(int page) {
//        if (page < 0) {
//            page = 0;
//        }
//        final int offset = page * PAGESIZE;
//        final CountDownLatch latch = new CountDownLatch(1);
//
//        final ProgressDialogCollectionCallBack<Job> pagingConback = new ProgressDialogCollectionCallBack<Job>(this.context) {
//            @Override
//            public void handleResponse(BackendlessCollection<Job> jobs) {
//
//                if (firstReponse) {
//                    firstReponse = false;
//                }
//                int size = jobs.getCurrentPage().size();
//                if (size > 0) {
//                    // offset+=restaurants.getCurrentPage().size();
//                    jobs.getPage(PAGESIZE, offset, this);
//                } else {
//                    latch.countDown();
//                }
//                super.handleResponse(jobs);
//            }
//
//        };
//        return  pagingConback;
//    }


}
