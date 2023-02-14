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

public class EarthquakeAdapter extends ArrayAdapter<Earthquake>{
    public EarthquakeAdapter(Activity context, ArrayList<Earthquake> earthquakes){
        super(context, 0, earthquakes);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }
        // Get the {@link Earthquake} object located at this position in the list
        Earthquake currentEarthquake = getItem(position);

        // Get the magnitude from the current Earthquake object
        double magnitude = currentEarthquake.getMagnitude();
        // Find the TextView in the list_item.xml layout with the ID magnitude_view
        TextView magnitudeTextView = (TextView) listItemView.findViewById(R.id.magnitude_view);
        // set the formatted magnitude text on the Magnitude TextView
        magnitudeTextView.setText(formatMagnitude(magnitude));

        // Set the proper background color on the magnitude circle.
        // Fetch the background from the TextView, which is a GradientDrawable.
        GradientDrawable magnitudeCircle = (GradientDrawable) magnitudeTextView.getBackground();

        // Get the appropriate background color based on the current earthquake magnitude
        int magnitudeColor = getMagnitudeColor(magnitude);

        // Set the color on the magnitude circle
        magnitudeCircle.setColor(magnitudeColor);

        String offsetLocation;
        String primaryLocation;
        // Get the location from the current Earthquake object
        String earthquakeLocation = currentEarthquake.getLocation();
        if(earthquakeLocation.indexOf("of") == -1){
            offsetLocation = "Near the";
            primaryLocation = earthquakeLocation;
        }else {
            int index = earthquakeLocation.indexOf("of");
            offsetLocation = earthquakeLocation.substring(0, index + 2);
            primaryLocation = earthquakeLocation.substring(index + 3, earthquakeLocation.length() - 1);
        }
        // Find the TextView in the list_item.xml layout with the ID location_offset_view
        TextView locationOffsetTextView = (TextView) listItemView.findViewById(R.id.location_offset_view);
        // set the offsetLocation on the Location Offset TextView
        locationOffsetTextView.setText(offsetLocation);

        // Find the TextView in the list_item.xml layout with the ID primary_location_view
        TextView primaryLocationTextView = (TextView) listItemView.findViewById(R.id.primary_location_view);
        // set the primaryLocation on the Primary Location TextView
        primaryLocationTextView.setText(primaryLocation);

        // Create a new Date object from the time in milliseconds of the earthquake
        Date dateObject = new Date(currentEarthquake.getTimeInMiliseconds());

        // Find the TextView in the list_item.xml layout with the ID date_view
        TextView dateTextView = (TextView) listItemView.findViewById(R.id.date_view);
        // Format the date string (i.e. "Mar 3, 1984")
        String formattedDate = formatDate(dateObject);
        // Display the date of the current earthquake in the Date TextView
        dateTextView.setText(formattedDate);

        // Find the TextView in the list_item.xml layout with the ID time_view
        TextView timeTextView = (TextView) listItemView.findViewById(R.id.time_view);
        // Format the time string (i.e. "4:30PM")
        String formattedTime = formatTime(dateObject);
        // Display the time of the current earthquake in the time TextView
        timeTextView.setText(formattedTime);

        // Return the whole list item layout (containing 3 TextViews)
        // so that it can be shown in the ListView
        return listItemView;
    }
    /**
     * Return the formatted Decimal Number (i.e. "9.2").
     */
    private String formatMagnitude(double magnitude) {
        // DecimalFormat class handles formatting of all types of decimal numbers.
        DecimalFormat formatter = new DecimalFormat("0.0");
        // Now we call the format() function on DecimalFormat object and pass the magnitude double number as parameter and
        // it will return the Decimal number in required format.
        return formatter.format(magnitude);
    }
    /**
     * Return the formatted date string (i.e. "Mar 3, 1984") from a Date object.
     */
    private String formatDate(Date dateObject) {
        // SimpleDateFormat class knows all about time zones and how dates are written in different parts of the world.
        SimpleDateFormat dateFormat = new SimpleDateFormat("LLL dd, yyyy");
        // Now we call the format() function on SimpleDateFormat object and pass the Date object as parameter and
        // it will return the date in required format.
        return dateFormat.format(dateObject);
    }

    /**
     * Return the formatted date string (i.e. "4:30 PM") from a Date object.
     */
    private String formatTime(Date dateObject) {
        // SimpleDateFormat class knows all about time zones and how dates are written in different parts of the world.
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");
        // Now we call the format() function on SimpleDateFormat object and pass the Date object as parameter and
        // it will return the time in required format.
        return timeFormat.format(dateObject);
    }
    /**
     * Return the formatted date string (i.e. "4:30 PM") from a Date object.
     */
    private int getMagnitudeColor(double magnitude){
        int magnitudeColorResourceId;
        int magnitudeFloor = (int) Math.floor(magnitude);
        switch (magnitudeFloor){
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
}
