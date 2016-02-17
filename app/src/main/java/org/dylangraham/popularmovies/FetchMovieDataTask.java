package org.dylangraham.popularmovies;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class FetchMovieDataTask extends AsyncTask<Void, Void, String[]> {

    private final String LOG_TAG = FetchMovieDataTask.class.getSimpleName();

    @Override
    protected String[] doInBackground(Void... params) {
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
            return getMovieDataFromJson(movieJsonStr);
        } catch (JSONException e) {
            Log.e(LOG_TAG, e.getMessage(), e);
            e.printStackTrace();
        }

        // This will only happen if there was an error getting or parsing the forecast.
        return null;
    }

    private String[] getMovieDataFromJson(String movieJsonStr) throws JSONException {

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

        //Log.v(LOG_TAG, "movieArray.length: " + movieArray.length());

        for (int i = 0; i < movieArray.length(); i++) {
            // Get the current JSON object
            JSONObject movieObject = movieArray.getJSONObject(i);

            String title = movieObject.getString(MDB_TITLE);
            String rating = movieObject.getString(MDB_VOTE_AVERAGE);
            
            //Log.v(LOG_TAG, "Movie poster: " + movieObject.getString(MDB_POSTER_PATH));

            Log.v(LOG_TAG, "Movie title: " + movieObject.getString(MDB_TITLE));
            Log.v(LOG_TAG, "Movie rating: " + movieObject.getString(MDB_VOTE_AVERAGE));
            Log.v(LOG_TAG, "Movie poster: " + movieObject.getString(MDB_POSTER_PATH));
        }



        return null;
    }

    @Override
    protected void onPostExecute(String[] result) {
        super.onPostExecute(result);
/*        if (result != null) {

        }
*/
    }
}
