package com.quickr.cars.Helper;


import android.app.Activity;
import android.os.AsyncTask;
import android.widget.ListView;
import com.quickr.cars.Activities.HomeActivity;
import com.quickr.cars.Adapters.CarAdapter;
import com.quickr.cars.Adapters.CarDetailAdapter;
import com.quickr.cars.Models.Car;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class Remote extends AsyncTask<String, String, String> {

    private Activity _activity;
    private ListView _listView;
    private int _resourceId;
    private int _operationType;
    private int _index;
    private String _searchParameter;

    public Remote(Activity activity, ListView listView, int resourceId, int operationType, int index, String searchParameter) {
        _activity = activity;
        _listView = listView;
        _resourceId = resourceId;
        _operationType = operationType;
        _index = index;
        _searchParameter = searchParameter;
    }

    @Override
    protected String doInBackground(String[] uri) {

        HttpClient httpclient = new DefaultHttpClient();
        HttpResponse response;
        String responseString = null;

        try {

            response = httpclient.execute(new HttpGet(uri[0]));
            StatusLine statusLine = response.getStatusLine();

            if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                response.getEntity().writeTo(out);
                responseString = out.toString();
                out.close();

            } else {
                response.getEntity().getContent().close();
                throw new IOException(statusLine.getReasonPhrase());
            }

        } catch (ClientProtocolException e) {
            //TODO Handle problems..

        } catch (IOException e) {
            System.out.print(e);

        }

        return responseString;
    }


    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        ParseJson parseJson = new ParseJson();


        switch (_operationType) {
            case HomeActivity.NORMAL_VIEW:
                CarAdapter carAdapter = new CarAdapter(_activity, _resourceId, parseJson.DeserializeJson(result));
                _listView.setAdapter(carAdapter);

                break;

            case HomeActivity.DETAIL_VIEW:
                ArrayList<Car> singleCar = new ArrayList<Car>();
                singleCar.add(parseJson.DeserializeJson(result).get(_index));

                CarDetailAdapter carDetailAdapter = new CarDetailAdapter(_activity, _resourceId, singleCar);
                _listView.setAdapter(carDetailAdapter);

                break;

            case HomeActivity.SEARCH_VIEW:
                ArrayList<Car> searchedCars = new ArrayList<Car>();
                ArrayList<Car> allCars = new ArrayList<Car>();
                allCars = parseJson.DeserializeJson(result);

                // show all cars if nothing is searched
                if (_searchParameter != null || _searchParameter != "") {
                    for (Car car : allCars) {
                        if (car.name.toLowerCase().contains(_searchParameter) || car.brand.toLowerCase().contains(_searchParameter) || car.type.toLowerCase().contains(_searchParameter)) {
                            searchedCars.add(car);
                        }
                    }

                    CarAdapter searchAdapter = new CarAdapter(_activity, _resourceId, searchedCars);
                    _listView.setAdapter(searchAdapter);
                } else {
                    CarAdapter searchAdapter = new CarAdapter(_activity, _resourceId, allCars);
                    _listView.setAdapter(searchAdapter);
                }

                break;

        }


    }
}
