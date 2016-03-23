package org.dylangraham.popularmovies;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieHolder> {
    private static final String LOG_TAG = MovieAdapter.class.getSimpleName();
    private List<MovieItem> movieItems;

    public MovieAdapter(List<MovieItem> movieItems) {
        this.movieItems = movieItems;
    }

    @Override
    public MovieHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View v = layoutInflater.inflate(R.layout.movie_item, parent, false);

        return new MovieHolder(v);
    }

    @Override
    public void onBindViewHolder(MovieHolder holder, int position) {
        MovieItem movie = movieItems.get(position);
        String url = movie.imageURL;
        Context context = holder.itemView.getContext();
        holder.bindMovie(movie);

        Picasso.with(context)
                .load(url)
                .error(R.mipmap.ic_launcher)
                .into(holder.getMovieImage());
    }

    @Override
    public int getItemCount() {
        return movieItems.size();
    }
}
