package com.chatapp.share;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class RecentModel implements Parcelable {
    String userName;
    ArrayList<String> imageList = new ArrayList<>();

    protected RecentModel(Parcel in) {
        userName = in.readString();
        imageList = in.createStringArrayList();
    }

    public RecentModel() {

    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(userName);
        dest.writeStringList(imageList);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<RecentModel> CREATOR = new Creator<RecentModel>() {
        @Override
        public RecentModel createFromParcel(Parcel in) {
            return new RecentModel(in);
        }

        @Override
        public RecentModel[] newArray(int size) {
            return new RecentModel[size];
        }
    };

    public String getUserName() {
        return userName;
    }

    public ArrayList<String> getImageList() {
        return imageList;
    }
}
