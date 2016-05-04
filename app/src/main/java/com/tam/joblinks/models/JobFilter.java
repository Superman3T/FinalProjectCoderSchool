package com.tam.joblinks.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by toan on 5/5/2016.
 */
public class JobFilter implements Parcelable {
    public String city;
    public String keywork;

    protected JobFilter(Parcel in) {
        city = in.readString();
        keywork = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(city);
        dest.writeString(keywork);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<JobFilter> CREATOR = new Parcelable.Creator<JobFilter>() {
        @Override
        public JobFilter createFromParcel(Parcel in) {
            return new JobFilter(in);
        }

        @Override
        public JobFilter[] newArray(int size) {
            return new JobFilter[size];
        }
    };
}
