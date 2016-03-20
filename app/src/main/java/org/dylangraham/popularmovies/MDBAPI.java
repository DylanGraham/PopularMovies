package org.dylangraham.popularmovies;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MDBAPI {
    @GET("{version}/discover/movie/")
    Call<MovieResponse> getMovies(
            @Path("version") String API_VERSION,
            @Query("sort_by") String SORT_BY,
            @Query("api_key") String API_KEY,
            @Query("vote_count.gte") String VOTE_COUNT
    );
}

/*
               final String BASE_URL = "http://api.themoviedb.org/3/discover/movie/?";
                final String SORT_PARAM = "sort_by";
                final String API_PARAM = "api_key";
                final String VOTE_COUNT = "vote_count.gte";
                final String API_KEY = BuildConfig.MOVIEDB_API_KEY;

                Uri builtUri = Uri.parse(BASE_URL).buildUpon()
                        .appendQueryParameter(SORT_PARAM, sort)
                        .appendQueryParameter(API_PARAM, API_KEY)
                        .appendQueryParameter(VOTE_COUNT, "100")
                        .build();
 */
