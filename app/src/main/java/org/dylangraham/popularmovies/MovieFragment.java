package org.dylangraham.popularmovies;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import java.util.Arrays;

public class MovieFragment extends Fragment {

    private MovieAdapter movieAdapter;

    MovieItem[] movieItems = {
            new MovieItem("The Martian", "7.1", R.drawable.the_martian),
            new MovieItem("The Martian", "7.1", R.drawable.the_martian),
            new MovieItem("The Martian", "7.1", R.drawable.the_martian),
            new MovieItem("The Martian", "7.1", R.drawable.the_martian),
            new MovieItem("The Martian", "7.1", R.drawable.the_martian),
            new MovieItem("The Martian", "7.1", R.drawable.the_martian)
    };

    public MovieFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onStart() {
        super.onStart();
        updateMovies();
    }

    private void updateMovies() {
        FetchMovieDataTask movieDataTask = new FetchMovieDataTask();
        movieDataTask.execute();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        movieAdapter = new MovieAdapter(getActivity(), Arrays.asList(movieItems));

        GridView gridView = (GridView) rootView.findViewById(R.id.gridview);
        gridView.setAdapter(movieAdapter);

        return rootView;
    }


}
