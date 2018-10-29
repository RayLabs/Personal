package app.rayyan.com.bcma.myapplication.Models;

/**
 * Created by rayyansiddiqui on 2018-09-15.
 */

public class Name {

    private String mIndex;
    private String mName;
    private String mNameMeaning;
    private int mNameImage;

    public Name(String mIndex, String mName, String mNameMeaning, int mNameImage) {
        this.mIndex = mIndex;
        this.mName = mName;
        this.mNameMeaning = mNameMeaning;
        this.mNameImage = mNameImage;
    }

    public Name(String mName, String mNameMeaning, int mNameImage) {
        this.mName = mName;
        this.mNameMeaning = mNameMeaning;
        this.mNameImage = mNameImage;
    }

    public String getmIndex() {
        return mIndex;
    }

    public String getmName() {
        return mName;
    }

    public String getmNameMeaning() {
        return mNameMeaning;
    }

    public int getmNameImage() {
        return mNameImage;
    }
}
