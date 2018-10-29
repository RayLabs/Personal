package com.myanime.rayyan.raylab.myanime.Models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Kashif on 11/23/2017.
 */

public class Episode implements Parcelable{

    private String mEpisodeName;
    private String mEpisodeSynopsis;
    private String mEpisodeDescription;
    private String mEpisodeUrl;

    public Episode(String mEpisodeName, String mEpisodeSynopsis, String mEpisodeDescription, String mEpisodeUrl) {
        this.mEpisodeName = mEpisodeName;
        this.mEpisodeSynopsis = mEpisodeSynopsis;
        this.mEpisodeDescription = mEpisodeDescription;
        this.mEpisodeUrl = mEpisodeUrl;
    }

    protected Episode(Parcel in) {
        mEpisodeName = in.readString();
        mEpisodeSynopsis = in.readString();
        mEpisodeDescription = in.readString();
        mEpisodeUrl = in.readString();
    }

    public static final Creator<Episode> CREATOR = new Creator<Episode>() {
        @Override
        public Episode createFromParcel(Parcel in) {
            return new Episode(in);
        }

        @Override
        public Episode[] newArray(int size) {
            return new Episode[size];
        }
    };

    public String getmEpisodeName() {
        return mEpisodeName;
    }

    public String getmEpisodeSynopsis() {
        return mEpisodeSynopsis;
    }

    public String getmEpisodeDescription() {
        return mEpisodeDescription;
    }

    public String getmEpisodeUrl() {
        return mEpisodeUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mEpisodeName);
        dest.writeString(mEpisodeSynopsis);
        dest.writeString(mEpisodeDescription);
        dest.writeString(mEpisodeUrl);
    }
}
