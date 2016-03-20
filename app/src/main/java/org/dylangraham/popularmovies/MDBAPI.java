package org.dylangraham.popularmovies;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MDBAPI {
    @GET("{version}/discover/movie/")
    Call<MovieResult> getMovies(
            @Path("version") String API_VERSION,
            @Query("sort_by") String SORT_BY,
            @Query("api_key") String API_KEY,
            @Query("vote_count.gte") String VOTE_COUNT
    );
}

