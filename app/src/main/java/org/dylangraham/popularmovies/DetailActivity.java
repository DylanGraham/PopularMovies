package org.dylangraham.popularmovies;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle b = getIntent().getExtras();
        MovieItem movieItem = b.getParcelable("movieItemsParcel");
        if (getSupportActionBar() != null && movieItem != null) {
            getSupportActionBar().setTitle(movieItem.movieName);
        }
        setContentView(R.layout.activity_detail);
    }
}
