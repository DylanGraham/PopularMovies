package org.dylangraham.popularmovies;

import android.os.Parcel;
import android.os.Parcelable;

public class MovieItem implements Parcelable {
    String movieName;
    String rating;
    String imageURL;

    public MovieItem(String movieName, String rating, String imageURL) {
        this.movieName = movieName;
        this.rating = rating;
        this.imageURL = imageURL;
    }

    private MovieItem(Parcel in) {
        movieName = in.readString();
        rating = in.readString();
        imageURL = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<MovieItem> CREATOR = new Parcelable.Creator<MovieItem>() {
        @Override
        public MovieItem[] newArray(int size) {
            return new MovieItem[size];
        }

        public MovieItem createFromParcel(Parcel in) {
            return new MovieItem(in);
        }
    };

        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(movieName);
            dest.writeString(rating);
            dest.writeString(imageURL);
        }


    }