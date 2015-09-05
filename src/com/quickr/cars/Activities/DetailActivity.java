package com.quickr.cars.Activities;


import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import com.quickr.cars.Helper.Remote;
import com.quickr.cars.R;

public class DetailActivity extends Activity {

    private ListView _listView;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            int index = Integer.parseInt(bundle.get(HomeActivity.EXTRA_MESSAGE).toString());

            _listView = (ListView) this.findViewById(R.id.carDetailListView);
            new Remote(this, _listView, R.layout.adapter_cars, HomeActivity.DETAIL_VIEW, index, null).execute(HomeActivity._carsListUrl);
        }

    }
}
