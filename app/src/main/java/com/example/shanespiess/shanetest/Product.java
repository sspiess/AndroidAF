package com.example.shanespiess.shanetest;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by Shane Spiess on 2/23/18.
 */

public class Product implements Parcelable {

    public String title;
    public String backgroundImage;
    public ContentItem[] content;
    public String promoMessage;
    public String topDescription;
    public String bottomDescription;

    private Product(Parcel source) {
        title = source.readString();
        backgroundImage = source.readString();
        content = source.createTypedArray(ContentItem.CREATOR);
        promoMessage = source.readString();
        topDescription = source.readString();
        bottomDescription = source.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(backgroundImage);
        dest.writeArray(content);
        dest.writeString(promoMessage);
        dest.writeString(topDescription);
        dest.writeString(bottomDescription);
    }

    public static final Parcelable.Creator<Product> CREATOR =
            new Parcelable.Creator<Product>() {

                @Override
                public Product createFromParcel(Parcel source) {
                    return new Product(source);
                }

                @Override
                public Product[] newArray(int size) {
                    return new Product[size];
                }
            };
}
