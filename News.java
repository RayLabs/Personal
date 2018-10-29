package app.rayyan.com.bcma.myapplication.Models;

/**
 * Created by rayyansiddiqui on 2018-08-05.
 */

public class News {

    private String mEventName;
    private String mEventTime;
    private String mEventBranchName;
    private String mEventID;

    public String getmEventName() {
        return mEventName;
    }

    public String getmEventTime() {
        return mEventTime;
    }

    public String getmEventBranchName() {
        return mEventBranchName;
    }

    public String getmEventID() {
        return mEventID;
    }

    public News(String mEventName, String mEventTime, String mEventBranchName, String mEventID) {
        this.mEventName = mEventName;
        this.mEventTime = mEventTime;
        this.mEventBranchName = mEventBranchName;
        this.mEventID = mEventID;
    }
}
