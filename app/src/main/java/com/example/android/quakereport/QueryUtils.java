 package com.example.android.quakereport;

import android.util.Log;

import com.example.android.quakereport.Earthquake;
import com.example.android.quakereport.EarthquakeData.Features;
import com.example.android.quakereport.EarthquakeData.Features.Properties;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Helper methods related to requesting and receiving earthquake data from USGS.
 */
public final class QueryUtils {

    /** Tag for the log messages */
    public static final String LOG_TAG = QueryUtils.class.getSimpleName();

    private QueryUtils() {
    }
    /**
     * Return a list of {@link Earthquake} objects that has been built up from
     * parsing a JSON response.
     */
    public static List<Earthquake> extractEarthquakes(EarthquakeData earthquakeData) {

        // Create an empty ArrayList that we can start adding earthquakes to
        List<Earthquake> earthquakes = new ArrayList<>();
        List<Features> featuresList = earthquakeData.getFeatures();
        for(int i=0; i<featuresList.size(); i++){
            Features feature = featuresList.get(i);
            Properties properties = feature.getProperties();
            double mag = properties.getMag();
            String place = properties.getPlace();
            long time = properties.getTime();
            String url = properties.getUrl();
            earthquakes.add(new Earthquake(mag,place,time,url));
        }

        // Return the list of earthquakes
        return earthquakes;
    }

}