package com.quickr.cars.Adapters;


import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.quickr.cars.Helper.RemoteImage;
import com.quickr.cars.Models.Car;
import com.quickr.cars.R;

import java.util.ArrayList;

public class CarDetailAdapter extends ArrayAdapter<Car> {

    private Activity _myContext;
    private ArrayList<Car> _cars;

    public CarDetailAdapter(Context context, int resourceId, ArrayList<Car> cars) {
        super(context, resourceId, cars);

        _myContext = (Activity) context;
        _cars = cars;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = _myContext.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.adapter_cardetail, null);

        if (_cars.get(position) != null) {
            TextView carNameView = (TextView) rowView.findViewById(R.id.carDetailNameView);
            TextView carDescriptionView = (TextView) rowView.findViewById(R.id.carDescView);
            ImageView carBigImageView = (ImageView) rowView.findViewById(R.id.carBigImageView);
            TextView ratingBar = (TextView) rowView.findViewById(R.id.ratingView);
            TextView engineView = (TextView) rowView.findViewById(R.id.engineView);
            TextView mileageView = (TextView) rowView.findViewById(R.id.mileageView);
            TextView priceView = (TextView) rowView.findViewById(R.id.priceView);
            TextView brandView = (TextView) rowView.findViewById(R.id.carBrandView);
            TextView carAbsView = (TextView) rowView.findViewById(R.id.carAbsView);

            carNameView.setText("About " + _cars.get(position).name);
            carDescriptionView.setText(_cars.get(position).description);
            mileageView.setText(_cars.get(position).mileage);
            engineView.setText(_cars.get(position).engine);
            priceView.setText(_cars.get(position).price);
            ratingBar.setText(_cars.get(position).rating);
            brandView.setText(_cars.get(position).brand);
            carAbsView.setText(_cars.get(position).abs_exist);

            new RemoteImage(carBigImageView).execute(_cars.get(position).image);

        }

        return rowView;
    }

}
