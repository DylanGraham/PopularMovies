package org.dylangraham.popularmovies;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.github.florent37.picassopalette.PicassoPalette;
import com.squareup.picasso.Picasso;

import org.dylangraham.popularmovies.model.MovieItem;

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
            getSupportActionBar().setTitle(movieItem.getMovieName());

            ScrollView detailScroll = (ScrollView) findViewById(R.id.scrollview_detail);

            TextView title = (TextView) findViewById(R.id.detail_title);
            title.setText(movieItem.getMovieName());

            TextView year = (TextView) findViewById(R.id.detail_year);
            String[] date = movieItem.getReleaseDate().split("-");
            if (date[0] != null) {
                year.setText(date[0]);
            } else {
                year.setText("-");
            }

            TextView average = (TextView) findViewById(R.id.detail_average);
            average.setText(movieItem.getAverage());

            TextView overview = (TextView) findViewById(R.id.detail_overview);
            overview.setText(movieItem.getOverview());

            ImageView poster = (ImageView) findViewById(R.id.detail_poster);
            Picasso.with(this).load(movieItem.getImageURL()).error(R.mipmap.ic_launcher).into(poster,
                    PicassoPalette.with(movieItem.getImageURL(), poster)
                            .use(PicassoPalette.Profile.MUTED_LIGHT)
                            .intoBackground(detailScroll)
                            .intoTextColor(title, PicassoPalette.Swatch.BODY_TEXT_COLOR)
                            .intoTextColor(year, PicassoPalette.Swatch.BODY_TEXT_COLOR)
                            .intoTextColor(average, PicassoPalette.Swatch.BODY_TEXT_COLOR)
                            .intoTextColor(overview, PicassoPalette.Swatch.BODY_TEXT_COLOR));

            ImageView detailImage = (ImageView) findViewById(R.id.detail_image);
            Picasso.with(this)
                    .load(movieItem.getBackdropURL())
                    .error(R.mipmap.ic_launcher)
                    .into(detailImage);
        }

    }
}