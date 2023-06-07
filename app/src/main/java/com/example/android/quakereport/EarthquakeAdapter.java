package com.example.android.quakereport;

import android.app.Activity;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class EarthquakeAdapter extends ArrayAdapter<Earthquake> {

    public EarthquakeAdapter(Activity context, ArrayList<Earthquake> earthquakes){
        super(context, 0, earthquakes);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if(listItemView == null){
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }
        // Get the {@link Earthquake} object located at this position in the list
        Earthquake currentEarthquake = getItem(position);

        DecimalFormat formatter = new DecimalFormat("0.0");
        String mag = formatter.format(currentEarthquake.getMagnitude());
        TextView magnitudeView = listItemView.findViewById(R.id.magnitude);
        // Set the proper background color on the magnitude circle.
        // Fetch the background from the TextView, which is a GradientDrawable.
        GradientDrawable magnitudeCircle = (GradientDrawable) magnitudeView.getBackground();

        // Get the appropriate background color based on the current earthquake magnitude
        int magnitudeColor = getMagnitudeColor(currentEarthquake.getMagnitude());

        // Set the color on the magnitude circle
        magnitudeCircle.setColor(magnitudeColor);
        magnitudeView.setText(mag);

        String location = currentEarthquake.getLocation();
        TextView locationOffset = listItemView.findViewById(R.id.location_offset);
        locationOffset.setText(getLocationOffset(location));
        TextView primaryLocation = listItemView.findViewById(R.id.location_primary);
        primaryLocation.setText(getPrimaryLocation(location));

        long tim = currentEarthquake.getTimeInMiliseconds();
        Date dobj = new Date(tim);

        TextView dateView = listItemView.findViewById(R.id.date);
        String date = formatDate(dobj);
        dateView.setText(date);

        TextView timeView = listItemView.findViewById(R.id.time);
        String time = formatTime(dobj);
        timeView.setText(time);

        return listItemView;
    }

    private int getMagnitudeColor(double magnitude) {
        int magnitudeColorResourceId;
        int mag = (int) Math.floor(magnitude);
        switch (mag){
            case 0:
            case 1:
                magnitudeColorResourceId = R.color.magnitude1;
                break;
            case 2:
                magnitudeColorResourceId = R.color.magnitude2;
                break;
            case 3:
                magnitudeColorResourceId = R.color.magnitude3;
                break;
            case 4:
                magnitudeColorResourceId = R.color.magnitude4;
                break;
            case 5:
                magnitudeColorResourceId = R.color.magnitude5;
                break;
            case 6:
                magnitudeColorResourceId = R.color.magnitude6;
                break;
            case 7:
                magnitudeColorResourceId = R.color.magnitude7;
                break;
            case 8:
                magnitudeColorResourceId = R.color.magnitude8;
                break;
            case 9:
                magnitudeColorResourceId = R.color.magnitude9;
                break;
            default:
                magnitudeColorResourceId = R.color.magnitude10plus;
                break;
        }
        return ContextCompat.getColor(getContext(), magnitudeColorResourceId);

    }

    /**
     * Return the formatted date string (i.e. "Mar 3, 1984") from a Date object.
     */
    private String formatDate(Date dateObject) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("LLL dd, yyyy");
        return dateFormat.format(dateObject);
    }

    /**
     * Return the formatted date string (i.e. "4:30 PM") from a Date object.
     */
    private String formatTime(Date dateObject) {
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");
        return timeFormat.format(dateObject);
    }

    private String getLocationOffset(String place){
        int x = place.indexOf(" of ");
        if(x == -1)
            return "Near the";
        else
            return place.substring(0,x+4);
    }
    private String getPrimaryLocation(String place){
        int x = place.indexOf(" of ");
        if(x == -1)
            return place;
        else
            return place.substring(x+4,place.length());
    }
}
