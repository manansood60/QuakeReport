package com.example.android.quakereport;

import retrofit2.Call;
import retrofit2.http.GET;

public interface DataRequestInterface {
    @GET("query?format=geojson&orderby=time&minmag=6")
    Call<EarthquakeData> getEarthquakeData();
}
