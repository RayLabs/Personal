package app.rayyan.com.bcma.myapplication.Models;

import java.io.Serializable;

/**
 * Created by rayyansiddiqui on 2018-08-06.
 */

public class Executive implements Serializable {
    private String mPersonName;
    private String mPersonPosition;
    private String mPersonNumber;
    private String mPersonEmail;


    public Executive(String mPersonName, String mPersonPosition, String mPersonNumber, String mPersonEmail) {
        this.mPersonName = mPersonName;
        this.mPersonPosition = mPersonPosition;
        this.mPersonNumber = mPersonNumber;
        this.mPersonEmail = mPersonEmail;
    }


    public String getmPersonName() {
        return mPersonName;
    }

    public String getmPersonPosition() {
        return mPersonPosition;
    }

    public String getmPersonNumber() {
        return mPersonNumber;
    }

    public String getmPersonEmail() {
        return mPersonEmail;
    }
}
