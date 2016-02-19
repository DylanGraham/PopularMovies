package org.dylangraham.popularmovies;


import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

public class MovieFragment extends Fragment {

    private MovieAdapter movieAdapter;

    ArrayList<MovieItem> movieItems = new ArrayList<>();

    public MovieFragment() {
        movieItems.add(new MovieItem("Test", "8.8", "http://some/path/to/poster.png"));
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    private void updateMovies(ArrayList<MovieItem> movieItems, View rootView, Context context) {
        FetchMovieDataTask movieDataTask = new FetchMovieDataTask(context, rootView);
        movieDataTask.execute();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        movieAdapter = new MovieAdapter(getActivity(), movieItems);

        GridView gridView = (GridView) rootView.findViewById(R.id.gridview);
        gridView.setAdapter(movieAdapter);

        updateMovies(movieItems, rootView, getContext());

        return rootView;
    }

    public class FetchMovieDataTask extends AsyncTask<ArrayList<MovieItem>, Void, ArrayList<MovieItem>> {

        private final String LOG_TAG = FetchMovieDataTask.class.getSimpleName();
        private Context mContext;
        private View rootView;

        public FetchMovieDataTask(Context context, View rootView) {
            this.mContext = context;
            this.rootView = rootView;
        }

        @Override
        protected ArrayList<MovieItem> doInBackground(ArrayList<MovieItem>... params) {
            // These two need to be declared outside the try/catch
            // so that they can be closed in the finally block.
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            // Will contain the raw JSON response as a string.
            String movieJsonStr = null;

            // Values that can be changed by the user in prefs eventually
            String sort = "popularity.desc";

            try {
                // Construct the URL
                final String BASE_URL = "http://api.themoviedb.org/3/discover/movie/?";
                final String SORT_PARAM = "sort_by";
                final String API_PARAM = "api_key";
                final String API_KEY = BuildConfig.MOVIEDB_API_KEY;

                Uri builtUri = Uri.parse(BASE_URL).buildUpon()
                        .appendQueryParameter(SORT_PARAM, sort)
                        .appendQueryParameter(API_PARAM, API_KEY)
                        .build();

                URL url = new URL(builtUri.toString());

                Log.v(LOG_TAG, "Query URI: " + url);

                // Create the request and open the connection
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                // Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    // Nothing to do.
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                    // But it does make debugging a *lot* easier if you print out the completed
                    // buffer for debugging.
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    // Stream was empty. No point in parsing.
                    return null;
                }
                movieJsonStr = buffer.toString();

                //Log.v(LOG_TAG, "JSON String: " + movieJsonStr);

            } catch (IOException e) {
                Log.e(LOG_TAG, "Error ", e);
                // If the code didn't successfully get the weather data, there's no point in attemping
                // to parse it.
                return null;
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e(LOG_TAG, "Error closing stream", e);
                    }
                }
            }

            try {
                return getMovieDataFromJson(movieJsonStr, movieItems);
            } catch (JSONException e) {
                Log.e(LOG_TAG, e.getMessage(), e);
                e.printStackTrace();
            }

            // This will only happen if there was an error getting or parsing the forecast.
            return null;
        }

        private ArrayList<MovieItem> getMovieDataFromJson(String movieJsonStr, ArrayList<MovieItem> movieItems) throws JSONException {

            final String MDB_LIST = "results";
            final String MDB_ID = "id";
            final String MDB_TITLE = "original_title";
            final String MDB_POPULARITY = "popularity";
            final String MDB_POSTER_PATH = "poster_path";
            final String MDB_BACKDROP_PATH = "backdrop_path";
            final String MDB_OVERVIEW = "overview";
            final String MDB_VOTE_AVERAGE = "vote_average";
            final String MDB_RELEASE_DATE = "release_date";

            JSONObject movieJson = new JSONObject(movieJsonStr);
            JSONArray movieArray = movieJson.getJSONArray(MDB_LIST);

            for (int i = 0; i < movieArray.length(); i++) {
                // Get the current JSON object
                JSONObject movieObject = movieArray.getJSONObject(i);

                String title = movieObject.getString(MDB_TITLE);
                String rating = movieObject.getString(MDB_POPULARITY);
                String posterPath = movieObject.getString(MDB_POSTER_PATH);
                String imageURL = "http://image.tmdb.org/t/p/w185" + posterPath;

                movieItems.add(new MovieItem(title, rating, imageURL));

                Log.v(LOG_TAG, title + rating + imageURL);

            }
            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<MovieItem> result) {
            super.onPostExecute(result);
            if (result != null) {
                movieAdapter.clear();
                for (int i = 0; i < result.size(); i++) {
                    ImageView imageView = (ImageView) rootView.findViewById(R.id.movie_image);
                    Picasso.with(mContext).load(result.get(i).imageURL).into(imageView);
                    movieAdapter.add(result.get(i));
                }
            }
        }
    }
}
