package com.example.android.quakereport;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Array;
import java.security.AccessControlContext;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static java.security.AccessController.getContext;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {
    private ArrayList<Earthquake> earthquakes;
    private EarthquakeItemClicked itemClickedListener;

    public RecyclerAdapter(ArrayList<Earthquake> earthquakes, EarthquakeItemClicked listener){
        this.earthquakes = earthquakes;
        this.itemClickedListener = listener;
    }
    public void setNewDataSet(List<Earthquake> earthquakes){
        this.earthquakes.clear();
        this.earthquakes = (ArrayList)earthquakes;
    }

    // It holds the view so the Views are not created everytime, so memory can be saved.
    public static class MyViewHolder extends RecyclerView.ViewHolder{

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    // Return the size of the dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        Log.d("Size of ArrayList", ""+earthquakes.size());
        return earthquakes.size();
    }
    // It Creates new views (invoked by the layout manager)
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(view);
        return new MyViewHolder(view);
    }

    // It Replaces the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        // Get the {Earthquake} object located at this position in the list
        Earthquake currentEarthquake = earthquakes.get(position);
        // Get the list_item view object
        View listItemView = holder.itemView;

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

        // setting up onclick listener
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemClickedListener.onItemClicked(holder.getAdapterPosition());
            }
        });

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
        int color = ContextCompat.getColor(App.context,magnitudeColorResourceId);
        return color;

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

interface EarthquakeItemClicked{
    public void onItemClicked(int position);
}