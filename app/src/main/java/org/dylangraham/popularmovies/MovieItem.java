package org.dylangraham.popularmovies;

import android.os.Parcel;
import android.os.Parcelable;

public class MovieItem implements Parcelable {
    String movieName;
    String rating;
    String imageURL;
    String backdropPath;
    String overview;
    String average;
    String releaseDate;

    public MovieItem(String id, String title, String rating, String imageURL,
                     String backdropPath, String overview, String average, String releaseDate) {
        this.movieName = title;
        this.rating = rating;
        this.imageURL = imageURL;
        this.backdropPath = backdropPath;
        this.overview = overview;
        this.average = average;
        this.releaseDate = releaseDate;
    }

    private MovieItem(Parcel in) {
        movieName = in.readString();
        rating = in.readString();
        imageURL = in.readString();
        backdropPath = in.readString();
        overview = in.readString();
        average = in.readString();
        releaseDate = in.readString();
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
            dest.writeString(backdropPath);
            dest.writeString(overview);
            dest.writeString(average);
            dest.writeString(releaseDate);
        }


    }