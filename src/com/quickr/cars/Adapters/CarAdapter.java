package com.quickr.cars.Adapters;


import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;
import com.quickr.cars.Helper.RemoteImage;
import com.quickr.cars.Models.Car;
import com.quickr.cars.R;

import java.util.ArrayList;

public class CarAdapter extends ArrayAdapter<Car> {

    private Activity _myContext;
    private ArrayList<Car> _cars;

    public CarAdapter(Context context, int resourceId, ArrayList<Car> cars) {
        super(context, resourceId, cars);

        _myContext = (Activity) context;
        _cars = cars;

    }

    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = _myContext.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.adapter_cars, null);

        if (_cars.get(position) != null) {
            TextView carNameView = (TextView) rowView.findViewById(R.id.carNameView);
            TextView carDescriptionView = (TextView) rowView.findViewById(R.id.carDescriptionView);
            ImageView carThumbnailView = (ImageView) rowView.findViewById(R.id.carThumbnailView);

            carNameView.setText(_cars.get(position).name);
            carDescriptionView.setText(String.valueOf(_cars.get(position).description));
            //new RemoteImage(carThumbnailView).execute(_cars.get(position).image);

            // using volley to populate image
            ImageRequest imgRequest = new ImageRequest(_cars.get(position).image,
                    new Response.Listener<Bitmap>() {
                        @Override
                        public void onResponse(Bitmap response) {
                            carThumbnailView.setImageBitmap(response);
                        }
                    }, 0, 0, Bitmap.Config.ARGB_8888, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    carThumbnailView.setBackgroundColor(Color.parseColor("#ff0000"));

                }
            });

            Volley.newRequestQueue(rowView.getContext()).add(imgRequest);

        }

        return rowView;
    }

}
