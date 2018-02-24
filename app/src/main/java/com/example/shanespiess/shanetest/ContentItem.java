package com.example.shanespiess.shanetest;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by Shane Spiess on 2/23/18.
 */

public class ContentItem implements Parcelable {

    public String target;
    public String title;

    private ContentItem(Parcel source) {
        target = source.readString();
        title = source.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(target);
        dest.writeString(title);
    }

    public static final Parcelable.Creator<ContentItem> CREATOR =
            new Parcelable.Creator<ContentItem>() {

                @Override
                public ContentItem createFromParcel(Parcel source) {
                    return new ContentItem(source);
                }

                @Override
                public ContentItem[] newArray(int size) {
                    return new ContentItem[size];
                }
            };
}
