package com.tam.joblinks.helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;
import android.util.Log;

import com.tam.joblinks.applications.JobApplication;
import com.tam.joblinks.models.Job;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by toan on 4/23/2016.
 */
public class JobDbHelper extends SQLiteOpenHelper {

    // http://www.androidhive.info/2013/09/android-sqlite-database-with-multiple-tables/

    private static JobDbHelper curInstance;
    private static final String DATABASE_NAME = "JobDatabase";
    private static final int DATABASE_VERSION = 2;
    private static final String TABLE_JOBS = "jobs";
    private static final String COL_RECORD_ID = "recordId";
    public static final String COL_JOB_CITY = "city";
    public static final String COL_OBJECT_ID = "objectId";
    public static final String COL_TITLE = "title";
    public static final String COL_CREATED_DATE = "createdDate";
    private static final String COL_EXPIRATION_DATE = "expirationDate";
    public static final String COL_MAX_SALARY = "maxSalary";
    public static final String COL_SALARY = "salary";
    public static final String COL_MIN_SALARY = "minSalary";

    private static final String COL_UPDATED_DATE = "updatedDate";
    private static final String COL_OWNER_ID = "ownerId";
    public static final String COL_DESCRIPTION = "description";
    public static final String COL_CREATED_BY = "createdBy";
    public static final String COL_SHORT_DESCRIPTION = "shortDescription";
    private static final String COL_IS_VIEWED = "isViewed";
    public static final String COL_IS_APPLIED = "isApplied";
    public static final String COL_IS_SAVED = "isSaved";
    private static final String COL_CURRENT_EMAIL = "email";
    private static final String TAG = "JobDbHelper";
    private static final String FORMAT_DATE = "yyyy-MM-dd HH:mm:ss";

    public static synchronized JobDbHelper getInstance(Context context) {
        if (curInstance == null) {
            curInstance = new JobDbHelper(context.getApplicationContext());
        }
        return curInstance;
    }

    private JobDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        StringBuilder builder = new StringBuilder();
        builder.append("CREATE TABLE IF NOT EXISTS " + TABLE_JOBS + " (");
        builder.append(COL_RECORD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, ");
        builder.append(COL_OBJECT_ID + " TEXT, ");
        builder.append(COL_JOB_CITY + " TEXT, ");
        //builder.append(COL_OBJECT_ID + " TEXT, ");
        builder.append(COL_TITLE + " TEXT, ");
        builder.append(COL_EXPIRATION_DATE + " TEXT, ");
        builder.append(COL_MAX_SALARY + " INTEGER , ");
        builder.append(COL_MIN_SALARY + " INTEGER , ");
        builder.append(COL_SALARY + " INTEGER , ");
        builder.append(COL_CREATED_DATE + " DATETIME, ");
        builder.append(COL_UPDATED_DATE + " DATETIME, ");
        builder.append(COL_OWNER_ID + " TEXT, ");
        builder.append(COL_DESCRIPTION + " TEXT, ");
        builder.append(COL_CREATED_BY + " TEXT, ");
        builder.append(COL_IS_VIEWED + " INTEGER, ");
        builder.append(COL_IS_APPLIED + " INTEGER, ");
        builder.append(COL_IS_SAVED + " INTEGER, ");
        builder.append(COL_CURRENT_EMAIL + " TEXT, ");
        builder.append(COL_SHORT_DESCRIPTION + " TEXT )");
        db.execSQL(builder.toString());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_JOBS);
            onCreate(db);
        }
    }


    public void addJob(Job job, boolean isViewed, boolean isSaved, boolean isApplied) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        int viewed = (isViewed ? 1 : 0);
        int applied = (isApplied ? 1 : 0);
        int saved = (isSaved ? 1 : 0);
        try {
            String expirationDate = getDateTime(job.getExpirationDate());
            ContentValues values = new ContentValues();
            values.put(COL_JOB_CITY, job.getCity());
            values.put(COL_OBJECT_ID, job.getObjectId());
            values.put(COL_TITLE, job.getTitle());
            values.put(COL_EXPIRATION_DATE, expirationDate);
            values.put(COL_MAX_SALARY, job.getMax_salary());
            values.put(COL_MIN_SALARY, job.getMin_salary());
            values.put(COL_SALARY, job.getSalary());
            values.put(COL_CREATED_DATE, getDateTime(job.getCreated()));
            values.put(COL_UPDATED_DATE, getDateTime(job.getUpdated()));
            values.put(COL_OWNER_ID, job.getOwnerId());
            values.put(COL_DESCRIPTION, job.getDescription());
            values.put(COL_CREATED_BY, job.getCreatedBy());
            values.put(COL_IS_VIEWED, viewed);
            values.put(COL_IS_APPLIED, applied);
            values.put(COL_IS_SAVED, saved);
            values.put(COL_SHORT_DESCRIPTION, job.getShortDescription());
            values.put(COL_CURRENT_EMAIL, JobApplication.currentMail);
            db.insertOrThrow(TABLE_JOBS, null, values);
            db.setTransactionSuccessful();
        } catch (Exception ex) {
            Log.d(TAG, "Error while trying to add job. Error: " + ex.getMessage());
        } finally {
            db.endTransaction();
        }
    }

    public void deleteJob(String objectId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        try {
            String whereClause = COL_OBJECT_ID + " = ?";
            String[] whereArgs = new String[]{String.valueOf(objectId)};
            db.delete(TABLE_JOBS, whereClause, whereArgs);
            db.setTransactionSuccessful();
        } catch (Exception ex) {
            Log.d(TAG, "Error while trying to delete job. Error: " + ex.getMessage());
        } finally {
            db.endTransaction();
        }
    }

    public void viewJob(String objectId) {
        updateStatus(objectId, JobStatus.viewed);
    }

    public void saveJob(String objectId) {
        updateStatus(objectId, JobStatus.saved);
    }

    public void saveJob(Job job) {
        addJob(job, false, true, false);
    }

    public void applyJob(String objectId) {
        updateStatus(objectId, JobStatus.applied);
    }

    private void updateStatus(String objectId, JobStatus status) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        try {
            String whereClause = COL_OBJECT_ID + " = ?";
            ContentValues values = new ContentValues();
            if (status == JobStatus.applied) {
                values.put(COL_IS_APPLIED, 1);
            } else if (status == JobStatus.saved) {
                values.put(COL_IS_SAVED, 1);
            } else {
                values.put(COL_IS_VIEWED, 1);
            }
            String[] whereArgs = new String[]{objectId};
            db.update(TABLE_JOBS, values, whereClause, whereArgs);
            db.setTransactionSuccessful();
        } catch (Exception ex) {
            Log.d(TAG, "Error while trying to update job. Error: " + ex.getMessage());
        } finally {
            db.endTransaction();
        }
    }

    public List<Job> getSavedJobs() {
//        String sql = "SELECT * FROM " + TABLE_JOBS + " WHERE " + COL_IS_SAVED + " = 1 AND "
//                + COL_CURRENT_EMAIL + " = '" + JobApplication.currentMail + "'";
        String sql = "SELECT * FROM " + TABLE_JOBS + " WHERE " + COL_IS_SAVED + " = 1";
        return getJobs(sql);
    }

    public List<Job> getViewedJob() {
//        String sql = "SELECT * FROM " + TABLE_JOBS + " WHERE " + COL_IS_VIEWED + " = 1 AND "
//                + COL_CURRENT_EMAIL + " = '" + JobApplication.currentMail + "'";
        String sql = "SELECT * FROM " + TABLE_JOBS + " WHERE " + COL_IS_VIEWED + " = 1";
        return getJobs(sql);
    }

    public List<Job> getAppliedJob() {
//        String sql = "SELECT * FROM " + TABLE_JOBS + " WHERE " + COL_IS_APPLIED + " = 1 AND "
//                + COL_CURRENT_EMAIL + " = '" + JobApplication.currentMail + "'";
        String sql = "SELECT * FROM " + TABLE_JOBS + " WHERE " + COL_IS_APPLIED + " = 1";
        return getJobs(sql);
    }

    private List<Job> getJobs(String sql) {
        List<Job> jobs = new ArrayList<Job>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        try {
            if (cursor.moveToFirst()) {
                do {
                    Job job = setJob(cursor);
                    jobs.add(job);
                } while (cursor.moveToNext());
            }
        } catch (Exception ex) {
            Log.d(TAG, "Error while trying to get all getSavedJobss. Error: " + ex.getMessage());
        } finally {
            if (cursor != null && cursor.isClosed() == false) {
                cursor.close();
            }
            if (db != null && db.isOpen()) {
                db.close();
            }
        }
        return jobs;
    }

    public boolean isExistingJob(String objectId) {
        SQLiteDatabase db = getReadableDatabase();
        String Query = "Select * from " + TABLE_JOBS + " where " + COL_OBJECT_ID + " = '" + objectId + "'";
        Cursor cursor = db.rawQuery(Query, null);
        try {

            if (cursor.getCount() <= 0) {
                cursor.close();
                return false;
            }
        } catch (Exception ex) {
            Log.d(TAG, "Error while trying to get all getSavedJobss. Error: " + ex.getMessage());
        } finally {
            if (cursor != null && cursor.isClosed() == false) {
                cursor.close();
            }
            if (db != null && db.isOpen()) {
                db.close();
            }
        }
        return true;
    }

    @NonNull
    private Job setJob(Cursor cursor) {
        Job job = new Job();
        job.setCity(getString(cursor, COL_JOB_CITY));
        job.setObjectId(getString(cursor, COL_OBJECT_ID));
        job.setTitle(getString(cursor, COL_TITLE));
        job.setExpirationDate(getDateTime(cursor, COL_EXPIRATION_DATE));
        job.setMax_salary(getInt(cursor, COL_MAX_SALARY));
        job.setMin_salary(getInt(cursor, COL_MIN_SALARY));
        job.setSalary(getInt(cursor, COL_SALARY));
        job.setCreated(getDateTime(cursor, COL_CREATED_DATE));
        job.setUpdated(getDateTime(cursor, COL_UPDATED_DATE));
        job.setOwnerId(getString(cursor, COL_OWNER_ID));
        job.setDescription(getString(cursor, COL_DESCRIPTION));
        job.setCreatedBy(getString(cursor, COL_CREATED_BY));
        job.setShortDescription(getString(cursor, COL_SHORT_DESCRIPTION));
        return job;
    }

    private String getString(Cursor cursor, String columnName) {
        return cursor.getString(cursor.getColumnIndex(columnName));
    }

    private int getInt(Cursor cursor, String columnName) {
        return cursor.getInt(cursor.getColumnIndex(columnName));
    }

    private Date getDateTime(Cursor cursor, String columnName) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat(FORMAT_DATE);
            return dateFormat.parse(cursor.getString(cursor.getColumnIndex(columnName)));
        } catch (ParseException ex) {
            return DateHelper.now();
        }
    }

    private String getDateTime(Date date) {
        if (date == null) {
            return DateHelper.formatDate(DateHelper.now());
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                FORMAT_DATE, Locale.getDefault());
        return dateFormat.format(date);
    }

    private enum JobStatus {
        saved, viewed, applied;
    }
}
