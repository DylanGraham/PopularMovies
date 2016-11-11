package org.dylangraham.popularmovies;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import timber.log.Timber;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        if (BuildConfig.DEBUG) Timber.plant(new Timber.DebugTree());
    }
}