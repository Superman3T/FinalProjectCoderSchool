package com.tam.joblinks.activities;

import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.tam.joblinks.R;
import com.tam.joblinks.helpers.MessageHelper;

/**
 * Created by toan on 4/21/2016.
 */
public class BaseActivity extends AppCompatActivity {
    protected void showToast(String message) {
        MessageHelper.showToast(this, message);
    }

    protected void showWrong() {
        showToast(getString(R.string.something_wrong));
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
