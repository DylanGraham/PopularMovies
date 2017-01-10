package org.dylangraham.popularmovies.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import org.dylangraham.popularmovies.BuildConfig;
import org.dylangraham.popularmovies.R;

import timber.log.Timber;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        if (BuildConfig.DEBUG) Timber.plant(new Timber.DebugTree());
    }
}