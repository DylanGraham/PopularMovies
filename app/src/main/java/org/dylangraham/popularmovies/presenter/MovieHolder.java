package org.dylangraham.popularmovies.presenter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import org.dylangraham.popularmovies.R;
import org.dylangraham.popularmovies.view.DetailActivity;
import org.dylangraham.popularmovies.model.MovieItem;

public class MovieHolder extends RecyclerView.ViewHolder {

    private MovieItem movieItem;
    private ImageView movieImage;

    public MovieHolder(final View itemView) {
        super(itemView);

        movieImage = (ImageView) itemView.findViewById(R.id.movie_image);

        itemView.setOnClickListener(v -> {
            Context context = itemView.getContext();

            Intent detailIntent = new Intent(context, DetailActivity.class);
            detailIntent.putExtra("movieItemsParcel", movieItem);
            context.startActivity(detailIntent);
        });
    }

    public void bindMovie(MovieItem movieItem) {
        this.movieItem = movieItem;
    }

    public ImageView getMovieImage() {
        return movieImage;
    }
}
