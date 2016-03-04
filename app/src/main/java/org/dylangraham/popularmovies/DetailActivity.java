package org.dylangraham.popularmovies;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Bundle b = getIntent().getExtras();
        MovieItem movieItem = b.getParcelable("movieItemsParcel");
        if (getSupportActionBar() != null && movieItem != null) {
            getSupportActionBar().setTitle(movieItem.movieName);

            TextView title = (TextView) findViewById(R.id.detail_title);
            title.setText(movieItem.movieName);

            ImageView detailImage = (ImageView) findViewById(R.id.detail_image);
            Picasso.with(this)
                    .load(movieItem.backdropURL)
                    .error(R.mipmap.ic_launcher)
                    .into(detailImage);
        }

    }
}