package org.dylangraham.popularmovies;

import java.util.ArrayList;
import java.util.List;

public class MovieResponse {
    List<Movie> movies;

    public MovieResponse() {
        movies = new ArrayList<>();
    }
}