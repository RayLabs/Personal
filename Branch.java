package app.rayyan.com.bcma.myapplication.Models;

import java.util.ArrayList;

/**
 * Created by rayyansiddiqui on 2018-07-26.
 */

public class Branch {

    public String mBranchName;
    public String mBranchSubtitle;
    public int mBranchImageUrl;
    public String mBranchEmail;
    public String mBranchPhoneNumber;
    public double mBranchlat;
    public double mBranchlng;
    public String mBranchAddress;
    public String mBranchWebsite;
    public String mBranchDesc;
    public ArrayList<Executive> mTeamMembers;

    public Branch(String mBranchName, String mBranchSubtitle, int mBranchImageUrl, String mBranchEmail, String mBranchPhoneNumber, double mBranchlat, double mBranchlng, String mBranchAddress, String mBranchWebsite) {
        this.mBranchName = mBranchName;
        this.mBranchSubtitle = mBranchSubtitle;
        this.mBranchImageUrl = mBranchImageUrl;
        this.mBranchEmail = mBranchEmail;
        this.mBranchPhoneNumber = mBranchPhoneNumber;
        this.mBranchlat = mBranchlat;
        this.mBranchlng = mBranchlng;
        this.mBranchAddress = mBranchAddress;
        this.mBranchWebsite = mBranchWebsite;
    }


    public ArrayList<Executive> getmTeamMembers() {
        return mTeamMembers;
    }

    public Branch(String mBranchName, String mBranchSubtitle, int mBranchImageUrl, String mBranchEmail, String mBranchPhoneNumber, String mBranchAddress, String mBranchWebsite, String mBranchDesc) {
        this.mBranchName = mBranchName;
        this.mBranchSubtitle = mBranchSubtitle;
        this.mBranchImageUrl = mBranchImageUrl;
        this.mBranchEmail = mBranchEmail;
        this.mBranchPhoneNumber = mBranchPhoneNumber;
        this.mBranchAddress = mBranchAddress;
        this.mBranchWebsite = mBranchWebsite;
        this.mBranchDesc = mBranchDesc;
    }



    public Branch(String mBranchName, String mBranchSubtitle, int mBranchImageUrl, String mBranchEmail, String mBranchPhoneNumber, String mBranchAddress, String mBranchWebsite, String mBranchDesc, ArrayList<Executive> mTeamMembers, double mBranchlat, double mBranchlng) {
        this.mBranchName = mBranchName;
        this.mBranchSubtitle = mBranchSubtitle;
        this.mBranchImageUrl = mBranchImageUrl;
        this.mBranchEmail = mBranchEmail;
        this.mBranchPhoneNumber = mBranchPhoneNumber;
        this.mBranchAddress = mBranchAddress;
        this.mBranchWebsite = mBranchWebsite;
        this.mBranchDesc = mBranchDesc;
        this.mTeamMembers = mTeamMembers;
        this.mBranchlat = mBranchlat;
        this.mBranchlng = mBranchlng;
    }

    public String getmBranchDesc() {
        return mBranchDesc;
    }

    public void setmBranchName(String mBranchName) {
        this.mBranchName = mBranchName;
    }

    public void setmBranchSubtitle(String mBranchSubtitle) {
        this.mBranchSubtitle = mBranchSubtitle;
    }

    public void setmBranchImageUrl(int mBranchImageUrl) {
        this.mBranchImageUrl = mBranchImageUrl;
    }

    public void setmBranchEmail(String mBranchEmail) {
        this.mBranchEmail = mBranchEmail;
    }

    public void setmBranchPhoneNumber(String mBranchPhoneNumber) {
        this.mBranchPhoneNumber = mBranchPhoneNumber;
    }

    public void setmBranchlat(double mBranchlat) {
        this.mBranchlat = mBranchlat;
    }

    public void setmBranchlng(double mBranchlng) {
        this.mBranchlng = mBranchlng;
    }

    public void setmBranchAddress(String mBranchAddress) {
        this.mBranchAddress = mBranchAddress;
    }

    public void setmBranchWebsite(String mBranchWebsite) {
        this.mBranchWebsite = mBranchWebsite;
    }

    public String getmBranchName() {
        return mBranchName;
    }

    public String getmBranchSubtitle() {
        return mBranchSubtitle;
    }

    public int getmBranchImageUrl() {
        return mBranchImageUrl;
    }

    public String getmBranchEmail() {
        return mBranchEmail;
    }

    public String getmBranchPhoneNumber() {
        return mBranchPhoneNumber;
    }

    public double getmBranchlat() {
        return mBranchlat;
    }

    public double getmBranchlng() {
        return mBranchlng;
    }

    public String getmBranchAddress() {
        return mBranchAddress;
    }

    public String getmBranchWebsite() {
        return mBranchWebsite;
    }
}
