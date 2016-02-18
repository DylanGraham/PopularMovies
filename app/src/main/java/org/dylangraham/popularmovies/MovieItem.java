package org.dylangraham.popularmovies;

public class MovieItem {
    String movieName;
    String rating;
    String imageURL;
    int imageResource;

    public MovieItem(String movieName, String rating, String imageURL) {
        this.movieName = movieName;
        this.rating = rating;
        this.imageURL = imageURL;
    }

    public void addImageResource(int imageResource) {
        this.imageResource = imageResource;
    }
}
