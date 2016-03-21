package org.dylangraham.popularmovies;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MovieFragment extends Fragment implements Callback<MovieResult> {

    public static final String LOG_TAG = MovieFragment.class.getSimpleName();
    private MovieAdapter movieAdapter;
    private ArrayList<MovieItem> movieItems;
    private boolean sortByPopular = true;

    public MovieFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if(savedInstanceState == null) {
            movieItems = new ArrayList<>();
        } else {
            movieItems = savedInstanceState.getParcelableArrayList("MOVIE_ITEMS");
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_sort:
                sortByPopular = !sortByPopular;
                if (sortByPopular) {
                    item.setTitle("Sort by popularity");
                } else {
                    item.setTitle("Sort by rating");
                }
                updateMovies();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResponse(Call<MovieResult> call, Response<MovieResult> response) {
        DecimalFormat df = new DecimalFormat("#.#");
        df.setRoundingMode(RoundingMode.HALF_UP);

        String average;
        String imageURL;
        String backdropURL;

        if (response.body() != null) {
            movieItems.clear();
            movieAdapter.clear();

            for (Result m : response.body().getResults()) {
                average = df.format(m.getVote_average()) + "/10";
                imageURL = "http://image.tmdb.org/t/p/w185" + m.getPoster_path();
                backdropURL = "http://image.tmdb.org/t/p/w342/" + m.getBackdrop_path();

                movieItems.add(new MovieItem(m.getId().toString(), m.getTitle(), m.getVote_average().toString(),
                        imageURL, backdropURL, m.getOverview(), average, m.getRelease_date()));
            }

/*            for (MovieItem movieItem : movieItems) {
                movieAdapter.add(movieItem);
            }*/
            for (int i = 0; i < 20; i++) {
                movieAdapter.add(movieItems.get(i));
            }
        }
    }

    @Override
    public void onFailure(Call<MovieResult> call, Throwable t) {
        Log.v(LOG_TAG, "Retrofit FAIL :( " + t.getLocalizedMessage());
    }

    private void updateMovies() {
        final String BASE_URL = "http://api.themoviedb.org/";
        final String API_VERSION = "3";
        final String VOTE_COUNT = "100";
        String SORT_BY;

        if (sortByPopular) {
            SORT_BY = "popularity.desc";
        } else {
            SORT_BY = "vote_average.desc";
        }

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MDBAPI mdbapi = retrofit.create(MDBAPI.class);

        Call<MovieResult> call = mdbapi.getMovies(API_VERSION, SORT_BY, BuildConfig.MOVIEDB_API_KEY, VOTE_COUNT);

        call.enqueue(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        movieAdapter = new MovieAdapter(getActivity(), movieItems);

        GridView gridView = (GridView) rootView.findViewById(R.id.gridview);
        gridView.setAdapter(movieAdapter);

        if (movieItems.size() == 0) {
            updateMovies();
        }

        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("MOVIE_ITEMS", movieItems);
    }
}
