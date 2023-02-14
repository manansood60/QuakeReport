/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.quakereport;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;

import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Loader;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class EarthquakeActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Earthquake>> {

    public static final String LOG_TAG = EarthquakeActivity.class.getName();
    /** URL for earthquake data from the USGS dataset */
    private static final String USGS_REQUEST_URL = "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&orderby=time&minmag=5&limit=20";
    /**
     * Constant value for the earthquake loader ID. We can choose any integer.
     * This really only comes into play if you're using multiple loaders.
     */
    private static final int EARTHQUAKE_LOADER_ID = 1;
    /** Adapter for the list of earthquakes */
    private EarthquakeAdapter mAdapter;
    // Empty text View for later references



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_activity);

        //AsyncTask method of making network request
//        // Start the AsyncTask to fetch the earthquake data
//        EarthquakeAsyncTask task = new EarthquakeAsyncTask();
//        task.execute(USGS_REQUEST_URL);
        Log.i(LOG_TAG,"In Earthquake Activity");
        // Get a reference to the LoaderManager, in order to interact with loaders.
        LoaderManager loaderManager = getLoaderManager();

        // Initialize the loader. Pass in the int ID constant defined above and pass in null for
        // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
        // because this activity implements the LoaderCallbacks interface).
        loaderManager.initLoader(EARTHQUAKE_LOADER_ID, null, this);

        // Find a reference to the {@link ListView} in the layout
        ListView earthquakeListView = (ListView) findViewById(R.id.list);

        // Create a new {@link ArrayAdapter} of earthquakes
        mAdapter = new EarthquakeAdapter(this, new ArrayList<Earthquake>());

        // Set Empty state view to the ListView
        TextView emptyView = (TextView) findViewById(R.id.empty_text_view);
        earthquakeListView.setEmptyView(emptyView);

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        earthquakeListView.setAdapter(mAdapter);
        earthquakeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Earthquake currentEarthquake = mAdapter.getItem(position);
                Uri webpage = Uri.parse(currentEarthquake.getUrl());
                Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    public Loader<List<Earthquake>> onCreateLoader(int i, Bundle bundle) {
        Log.i(LOG_TAG,"In onCreateLoader");

        // Create a new loader for the given URL
        return new EarthquakeLoader(this, USGS_REQUEST_URL);

    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<Earthquake>> loader, List<Earthquake> earthquakes) {
        Log.i(LOG_TAG,"In onLoadFinished");


        // Hide loading indicator because the data has been loaded
        findViewById(R.id.loading_spinner).setVisibility(View.GONE);
        // Clear the adapter of previous earthquake data
        mAdapter.clear();

        // If there is a valid list of {@link Earthquake}s, then add them to the adapter's
        // data set. This will trigger the ListView to update.
        if (earthquakes != null && !earthquakes.isEmpty()) {
            mAdapter.addAll(earthquakes);
        }
        TextView emptyView = (TextView) findViewById(R.id.empty_text_view);
        // Get a reference to the ConnectivityManager to check state of network connectivity
        ConnectivityManager cm = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        // Get details on the currently active default data network
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if (networkInfo!=null && networkInfo.isConnected()){

            // Set empty state text to display "No earthquakes found."
            emptyView.setText(R.string.no_earthquakes);
        }else{
            // Set empty state text to display "No Internet Connection."
            emptyView.setText(R.string.no_internet);
        }
    }


    @Override
    public void onLoaderReset(@NonNull Loader<List<Earthquake>> loader) {
        Log.i(LOG_TAG,"In onLoaderReset");

        // Loader reset, so we can clear out our existing data.
        mAdapter.clear();
    }

    // AsyncTask method of making network request
//    /**
//     * {@link AsyncTask} to perform the network request on a background thread, and then
//     * update the UI with the list of earthquakes in the response.
//     *
//     * AsyncTask has three generic parameters: the input type, a type used for progress updates, and
//     * an output type. Our task will take a String URL, and return an Earthquake. We won't do
//     * progress updates, so the second generic is just Void.
//     *
//     * We'll only override two of the methods of AsyncTask: doInBackground() and onPostExecute().
//     * The doInBackground() method runs on a background thread, so it can run long-running code
//     * (like network activity), without interfering with the responsiveness of the app.
//     * Then onPostExecute() is passed the result of doInBackground() method, but runs on the
//     * UI thread, so it can use the produced data to update the UI.
//     */
//    private class EarthquakeAsyncTask extends AsyncTask<String, Void, List<Earthquake>>{
//        /**
//         * This method runs on a background thread and performs the network request.
//         * We should not update the UI from a background thread, so we return a list of
//         * {@link Earthquake}s as the result.
//         */
//        @Override
//        protected List<Earthquake> doInBackground(String... urls) {
//            // Don't perform the request if there are no URLs, or the first URL is null.
//            if (urls.length < 1 || urls[0] == null) {
//                return null;
//            }
//            // Create a fake list of earthquake locations.
//            final List<Earthquake> earthquakes = QueryUtils.fetchEarthquakeData(urls[0]);
//            return earthquakes;
//        }
//        /**
//         * This method runs on the main UI thread after the background work has been
//         * completed. This method receives as input, the return value from the doInBackground()
//         * method. First we clear out the adapter, to get rid of earthquake data from a previous
//         * query to USGS. Then we update the adapter with the new list of earthquakes,
//         * which will trigger the ListView to re-populate its list items.
//         */
//        @Override
//        protected void onPostExecute(List<Earthquake> earthquakes) {
//            // Clear the adapter of previous earthquake data
//            mAdapter.clear();
//
//            // If there is a valid list of {@link Earthquake}s, then add them to the adapter's
//            // data set. This will trigger the ListView to update.
//            if (earthquakes != null && !earthquakes.isEmpty()) {
//                mAdapter.addAll(earthquakes);
//            }
//        }
//    }
}
