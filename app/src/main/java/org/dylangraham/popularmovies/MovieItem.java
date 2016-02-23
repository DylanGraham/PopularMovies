package org.dylangraham.popularmovies;

public class MovieItem {
    String movieName;
    String rating;
    String imageURL;

    public MovieItem(String movieName, String rating, String imageURL) {
        this.movieName = movieName;
        this.rating = rating;
        this.imageURL = imageURL;
    }

}