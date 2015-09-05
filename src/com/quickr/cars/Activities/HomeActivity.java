package com.quickr.cars.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import com.quickr.cars.Helper.Remote;
import com.quickr.cars.R;

public class HomeActivity extends Activity {

    public final static String _carsListUrl = "http://quikr.0x10.info/api/cars?type=json&query=list_cars";
    public final static String _carApiHit = "http://quikr.0x10.info/api/cars?type=json&query=api_hits";
    public final static String EXTRA_MESSAGE = "com.quickr.cars.MESSAGE";
    public final static int NORMAL_VIEW = 0;
    public final static int DETAIL_VIEW = 1;
    public final static int SEARCH_VIEW = 2;

    private ListView _listView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        _listView = (ListView) this.findViewById(R.id.carListView);

        new Remote(this, _listView, R.layout.adapter_cars, NORMAL_VIEW, 0, null).execute(_carsListUrl);

        _listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(HomeActivity.this, DetailActivity.class);
                intent.putExtra(EXTRA_MESSAGE, id);
                startActivity(intent);

            }

        });

    }

    public void searchCars(View view) {
        EditText editText = (EditText) this.findViewById(R.id.searchTextView);
        new Remote(this, _listView, R.layout.adapter_cars, SEARCH_VIEW, 0, editText.getText().toString()).execute(_carsListUrl);

    }

}
