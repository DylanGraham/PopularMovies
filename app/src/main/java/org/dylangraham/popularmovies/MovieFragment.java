package org.dylangraham.popularmovies;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MovieFragment extends Fragment implements Callback<MovieResult> {

    public static final String LOG_TAG = MovieFragment.class.getSimpleName();
    private ArrayList<MovieItem> movieItems;
    private boolean sortByPopular = true;
    private MovieAdapter movieAdapter;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.movie_recycler_view, container, false);

        RecyclerView movieRecyclerView = (RecyclerView) rootView.findViewById(R.id.movie_recycler_view);
        movieRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));

        movieAdapter = new MovieAdapter(movieItems);
        movieRecyclerView.setAdapter(movieAdapter);

        if (movieItems.size() == 0) updateMovies();

        return rootView;
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

            for (Result m : response.body().getResults()) {
                average = df.format(m.getVote_average()) + "/10";
                imageURL = "http://image.tmdb.org/t/p/w185" + m.getPoster_path();
                backdropURL = "http://image.tmdb.org/t/p/w342/" + m.getBackdrop_path();

                movieItems.add(new MovieItem(m.getId().toString(), m.getTitle(), m.getVote_average().toString(),
                        imageURL, backdropURL, m.getOverview(), average, m.getRelease_date()));
            }
        }

        movieAdapter.notifyDataSetChanged();
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
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MDBAPI mdbapi = retrofit.create(MDBAPI.class);

        Call<MovieResult> call = mdbapi.getMovies(API_VERSION, SORT_BY, BuildConfig.MOVIEDB_API_KEY, VOTE_COUNT);

        call.enqueue(this);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("MOVIE_ITEMS", movieItems);
    }
}
