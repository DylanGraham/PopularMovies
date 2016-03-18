package org.dylangraham.popularmovies;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.github.florent37.picassopalette.BitmapPalette;
import com.github.florent37.picassopalette.PicassoPalette;
import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        setContentView(R.layout.activity_detail);

        Bundle b = getIntent().getExtras();
        MovieItem movieItem = b.getParcelable("movieItemsParcel");
        if (getSupportActionBar() != null && movieItem != null) {
            getSupportActionBar().setTitle(movieItem.movieName);

            ScrollView detailScroll = (ScrollView) findViewById(R.id.scrollview_detail);

            TextView title = (TextView) findViewById(R.id.detail_title);
            title.setText(movieItem.movieName);

            TextView year = (TextView) findViewById(R.id.detail_year);
            String[] date = movieItem.releaseDate.split("-");
            year.setText(date[0]);

            TextView average = (TextView) findViewById(R.id.detail_average);
            average.setText(movieItem.average);

            TextView overview = (TextView) findViewById(R.id.detail_overview);
            overview.setText(movieItem.overview);

            ImageView poster = (ImageView) findViewById(R.id.detail_poster);
            Picasso.with(this).load(movieItem.imageURL).error(R.mipmap.ic_launcher).into(poster,
                    PicassoPalette.with(movieItem.imageURL, poster)
                            .use(PicassoPalette.Profile.MUTED)
                            .intoBackground(detailScroll)
                            .intoTextColor(title, BitmapPalette.Swatch.TITLE_TEXT_COLOR)
                            .intoTextColor(year, BitmapPalette.Swatch.BODY_TEXT_COLOR)
                            .intoTextColor(average, BitmapPalette.Swatch.BODY_TEXT_COLOR)
                            .intoTextColor(overview, BitmapPalette.Swatch.BODY_TEXT_COLOR));

            ImageView detailImage = (ImageView) findViewById(R.id.detail_image);
            Picasso.with(this)
                    .load(movieItem.backdropURL)
                    .error(R.mipmap.ic_launcher)
                    .into(detailImage);
        }

    }
}