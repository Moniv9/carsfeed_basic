package com.quickr.cars.Helper;


import com.quickr.cars.Models.Car;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class ParseJson {

    public ArrayList<Car> DeserializeJson(String jsonString) {

        ArrayList<Car> cars = new ArrayList<Car>();

        try {
            JSONArray jsonArray = new JSONArray(jsonString);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                Car car = new Car();
                car.name = jsonObject.getString("name");
                car.brand = jsonObject.getString("brand");
                car.type = jsonObject.getString("type");
                car.abs_exist = jsonObject.getString("abs_exist");
                car.color = jsonObject.getString("color");
                car.engine = jsonObject.getString("engine_cc");
                car.description = jsonObject.getString("description");
                car.image = jsonObject.getString("image");
                car.link = jsonObject.getString("link");
                car.mileage = jsonObject.getString("mileage");
                car.rating = jsonObject.getString("rating");

                cars.add(car);

            }

        } catch (Exception exception) {
            //Todo : Handle exception

        }


        return cars;
    }
}
