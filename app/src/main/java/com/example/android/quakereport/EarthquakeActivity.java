
package com.example.android.quakereport;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

//import android.support.v7.app.AppCompatActivity;

public class EarthquakeActivity extends AppCompatActivity implements EarthquakeItemClicked {

    public static Context context ;
    /** URL for earthquake data from the USGS dataset */
    private static final String USGS_BASE_URL =
            "https://earthquake.usgs.gov/fdsnws/event/1/";

    public static final String LOG_TAG = EarthquakeActivity.class.getName();

    /**
     * Constant value for the earthquake loader ID. We can choose any integer.
     * This really only comes into play if you're using multiple loaders.
     */
    private static final int EARTHQUAKE_LOADER_ID = 1;

    /** Adapter for the list of earthquakes */
    private RecyclerAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private List<Earthquake> earthquakes;

    private TextView mEmptyStateTextView=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_activity);
        // Empty state TextView used when Recycler View will be empty
        mEmptyStateTextView = findViewById(R.id.empty_state_text_view);

        // Creating Recycler View object
        mRecyclerView = findViewById(R.id.recycler_list);
        // Setting Layout Manager for Recycler View
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Create a new adapter
        mAdapter = new RecyclerAdapter(this);

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        mRecyclerView.setAdapter(mAdapter);

        // Get a reference to the ConnectivityManager to check state of network connectivity
        ConnectivityManager cm = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);

        // Get details on the currently active default data network
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();

        // If there is a network connection, fetch data
        if(networkInfo != null && networkInfo.isConnected()) {
            // Network Is Connected
            // Making Network Request using Retrofit
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(USGS_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            DataRequestInterface dataRequest = retrofit.create(DataRequestInterface.class);
            dataRequest.getEarthquakeData().enqueue(new Callback<EarthquakeData>() {
                @Override
                public void onResponse(Call<EarthquakeData> call, Response<EarthquakeData> response) {
                    earthquakes = QueryUtils.extractEarthquakes(response.body());
                    if (earthquakes != null && !earthquakes.isEmpty()) {
                        Log.e("GOT RESPONSE: ", earthquakes.get(1).getLocation());
                        mAdapter.setNewDataSet(earthquakes);
                        mAdapter.notifyDataSetChanged();
                        mEmptyStateTextView.setVisibility(View.GONE);
                    }else{
                        mRecyclerView.setVisibility(View.GONE);
//                      if(mEmptyStateTextView.getText() == null)
                        mEmptyStateTextView.setText(R.string.no_earthquakes);
                    }
                    ProgressBar pb = findViewById(R.id.loading_spinner);
                    pb.setVisibility(View.GONE);
                }

                @Override
                public void onFailure(Call<EarthquakeData> call, Throwable t) {
                    ProgressBar pb = findViewById(R.id.loading_spinner);
                    pb.setVisibility(View.GONE);
                    mRecyclerView.setVisibility(View.GONE);
                    Log.e(LOG_TAG, t.getMessage() );
                }
            });
        }else{
            // Network is Not Connected
            // First, hide loading indicator so error message will be visible
            View loadingIndicator = findViewById(R.id.loading_spinner);
            loadingIndicator.setVisibility(View.GONE);

            // Update empty state with no connection error message
            mEmptyStateTextView.setText(R.string.no_internet_connection);
        }

    }

    @Override
    public void onItemClicked(int position) {
        Earthquake earthquake = earthquakes.get(position);
        String url = earthquake.getUrl();
        Uri webpage = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }
}
