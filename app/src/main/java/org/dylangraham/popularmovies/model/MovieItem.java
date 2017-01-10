package org.dylangraham.popularmovies.model;

import android.os.Parcel;
import android.os.Parcelable;

public class MovieItem implements Parcelable {
    private String id;
    private String movieName;
    private String rating;
    private String imageURL;
    private String backdropURL;
    private String overview;
    private String average;
    private String releaseDate;

    public static final Parcelable.Creator<MovieItem> CREATOR = new Parcelable.Creator<MovieItem>() {
        @Override
        public MovieItem[] newArray(int size) {
            return new MovieItem[size];
        }

        public MovieItem createFromParcel(Parcel in) {
            return new MovieItem(in);
        }
    };

    public MovieItem(String id, String title, String rating, String imageURL,
                     String backdropURL, String overview, String average, String releaseDate) {
        this.id = id;
        this.movieName = title;
        this.rating = rating;
        this.imageURL = imageURL;
        this.backdropURL = backdropURL;
        this.overview = overview;
        this.average = average;
        this.releaseDate = releaseDate;
    }

    private MovieItem(Parcel in) {
        id = in.readString();
        movieName = in.readString();
        rating = in.readString();
        imageURL = in.readString();
        backdropURL = in.readString();
        overview = in.readString();
        average = in.readString();
        releaseDate = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(movieName);
        dest.writeString(rating);
        dest.writeString(imageURL);
        dest.writeString(backdropURL);
        dest.writeString(overview);
        dest.writeString(average);
        dest.writeString(releaseDate);
    }

    public String getId() {
        return id;
    }

    public String getMovieName() {
        return movieName;
    }

    public String getRating() {
        return rating;
    }

    public String getImageURL() {
        return imageURL;
    }

    public String getBackdropURL() {
        return backdropURL;
    }

    public String getOverview() {
        return overview;
    }

    public String getAverage() {
        return average;
    }

    public String getReleaseDate() {
        return releaseDate;
    }
}