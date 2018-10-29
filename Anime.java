package com.myanime.rayyan.raylab.myanime.Models;

import java.util.ArrayList;

/**
 * Created by Kashif on 11/23/2017.
 */

public class Anime {

    private String mAnimeName;
    private String mAnimeStatus;
    private String mAnimeGenres;
    private String mAnimeRating;
    private String mAnimeFirstAiredDate;
    private String mAnimeFeaturedImage;
    private String mAnimeRatingScale;
    private String mAnimeDescription;
    private ArrayList<Episode> mAnimeEpisodes;

    public Anime(String mAnimeName, String mAnimeStatus, String mAnimeGenres, String mAnimeRating, String mAnimeFirstAiredDate, String mAnimeFeaturedImage, String mAnimeRatingScale, String mAnimeDescription, ArrayList<Episode> mAnimeEpisodes) {
        this.mAnimeName = mAnimeName;
        this.mAnimeStatus = mAnimeStatus;
        this.mAnimeGenres = mAnimeGenres;
        this.mAnimeRating = mAnimeRating;
        this.mAnimeFirstAiredDate = mAnimeFirstAiredDate;
        this.mAnimeFeaturedImage = mAnimeFeaturedImage;
        this.mAnimeRatingScale = mAnimeRatingScale;
        this.mAnimeDescription = mAnimeDescription;
        this.mAnimeEpisodes = mAnimeEpisodes;
    }

    public String getmAnimeName() {
        return mAnimeName;
    }

    public String getmAnimeStatus() {
        return mAnimeStatus;
    }

    public String getmAnimeGenres() {
        return mAnimeGenres;
    }

    public String getmAnimeRating() {
        return mAnimeRating;
    }

    public String getmAnimeFirstAiredDate() {
        return mAnimeFirstAiredDate;
    }

    public String getmAnimeFeaturedImage() {
        return mAnimeFeaturedImage;
    }

    public String getmAnimeRatingScale() {
        return mAnimeRatingScale;
    }

    public String getmAnimeDescription() {
        return mAnimeDescription;
    }

    public ArrayList<Episode> getmAnimeEpisodes() {
        return mAnimeEpisodes;
    }

    //TODO: IGNORE THIS!!!!!!!!

    public void setmAnimeName(String mAnimeName) {
        this.mAnimeName = mAnimeName;
    }

    public void setmAnimeStatus(String mAnimeStatus) {
        this.mAnimeStatus = mAnimeStatus;
    }

    public void setmAnimeGenres(String mAnimeGenres) {
        this.mAnimeGenres = mAnimeGenres;
    }

    public void setmAnimeRating(String mAnimeRating) {
        this.mAnimeRating = mAnimeRating;
    }

    public void setmAnimeFirstAiredDate(String mAnimeFirstAiredDate) { this.mAnimeFirstAiredDate = mAnimeFirstAiredDate; }

    public void setmAnimeFeaturedImage(String mAnimeFeaturedImage) {
        this.mAnimeFeaturedImage = mAnimeFeaturedImage;}

    public void setmAnimeRatingScale(String mAnimeRatingScale) {
        this.mAnimeRatingScale = mAnimeRatingScale;
    }

    public void setmAnimeDescription(String mAnimeDescription) {
        this.mAnimeDescription = mAnimeDescription;
    }

    public void setmAnimeEpisodes(ArrayList<Episode> mAnimeEpisodes) {
        this.mAnimeEpisodes = mAnimeEpisodes;
    }
}
