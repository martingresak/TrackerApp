package com.example.trackerapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.telephony.CellIdentityNr;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.view.MotionEvent;
import android.view.View;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.BarLineChartBase;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.ScatterChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.ScatterData;
import com.github.mikephil.charting.data.ScatterDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.material.slider.Slider;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.Semaphore;

import cz.msebera.android.httpclient.Header;

public class CenterActivity extends AppCompatActivity {

    Slider typeSlider;
    BarChart mainBarChart;
    LineChart mainLineChart;
    ScatterChart mainScatterChart;
    ArrayList<BarLineChartBase> charts;


    ArrayList<Entry> dataEntry; //teeeeeemp!!!!!!
    ArrayList<BarEntry> dataBarEntry; //temp as well
    ArrayList<Long> data;

    static int state;
    final String APP_ID = "1f755381a8c86a0fe7e6ac380197689c";
    final String WEATHER_URL = "https://api.openweathermap.org/data/2.5/weather";

    final long MIN_TIME = 5000;
    final float MIN_DISTANCE = 1000;
    final int REQUEST_CODE = 101;

    String Location_Provider = LocationManager.GPS_PROVIDER;

    TextView WeatherCity, WeatherType, WeatherTemp;
    ImageView WeatherIcon;

    EditText cityInput;

    RelativeLayout mCityFinder;


    LocationManager mLocationManager;
    LocationListener mLocationListener;

    String userID;

    private FirebaseAuth mAuth;
    private DatabaseReference ref;

    private Object lock = new Object();
    String test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_center);

        synchronized (lock) {
            mAuth = FirebaseAuth.getInstance();
            lock.notify();
        }

        cityInput = findViewById(R.id.searchCity);

        typeSlider = findViewById(R.id.typeSlider);

        mainBarChart = findViewById(R.id.mainBarChart);
        mainLineChart = findViewById(R.id.mainLineChart);
        mainScatterChart = findViewById(R.id.mainScatterChart);

        WeatherType = findViewById(R.id.weather_type);
        WeatherCity = findViewById(R.id.weather_city);
        WeatherTemp = findViewById(R.id.weather_temp);
        WeatherIcon = findViewById(R.id.weather_icon);


        charts = new ArrayList<>();
        charts.add(mainBarChart);
        charts.add(mainLineChart);
        charts.add(mainScatterChart);




        typeSlider.addOnChangeListener((slider, value, fromUser) -> {
            int v = (int) (value);
            setState(v);
        });


        //WEATHER








    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent mIntent = getIntent();
        String city = mIntent.getStringExtra("City");
        if (city != null) {
            getWeatherForNewCity(city);
        } else {
            getWeatherForCurrentLocation();
        }

        data = new ArrayList<>();

        dataEntry = new ArrayList<>();
        dataBarEntry = new ArrayList<>();

        String dataType = getIntent().getStringExtra("EXTRA_STRING");

        synchronized (lock) {
            while (mAuth == null) {
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            userID = mAuth.getUid();
        }
        ref = FirebaseDatabase.getInstance("https://trackerapp-emp-default-rtdb.europe-west1.firebasedatabase.app/").getReference().child(userID);
        ref.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Map<String, Object> days = (Map<String, Object>) dataSnapshot.getValue();

                        for (Map.Entry<String, Object> entry : days.entrySet()) {
                            if(entry.getKey().matches("[0-9]+")) {

                                Map<String, Object> day = (Map<String, Object>) entry.getValue();
                                data.add((Long) day.get(dataType));
                            }
                        }

                        for (int i = 0; i < data.size(); i++) {
                            dataEntry.add(new Entry(i, data.get(i)));
                            dataBarEntry.add(new BarEntry(i, data.get(i)));
                        }
                        drawChart();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        //handle databaseError
                    }
                });

        cityInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                String newCity = cityInput.getText().toString();
                Intent intent = new Intent(CenterActivity.this, CenterActivity.class);
                intent.putExtra("City", newCity);
                intent.putExtra("EXTRA_STRING", mIntent.getStringExtra("EXTRA_STRING"));
                CenterActivity.this.finish();
                startActivity(intent);

                return false;
            }
        });


    }

    private void getWeatherForNewCity(String city) {
        RequestParams params = new RequestParams();
        params.put("q", city);
        params.put("appid", APP_ID);
        letsdoSomeNetworking(params);

    }

    private void getWeatherForCurrentLocation() {

        mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        mLocationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

                String Latitude = String.valueOf(location.getLatitude());
                String Longitude = String.valueOf(location.getLongitude());

                RequestParams params = new RequestParams();
                params.put("lat", Latitude);
                params.put("lon", Longitude);
                params.put("appid", APP_ID);
                letsdoSomeNetworking(params);

            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {
                //not able to get location
            }
        };

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
            return;
        }
        mLocationManager.requestLocationUpdates(Location_Provider, MIN_TIME, MIN_DISTANCE, mLocationListener);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(CenterActivity.this, "Locationget Succesffully", Toast.LENGTH_SHORT).show();
                getWeatherForCurrentLocation();
            } else {
                //user denied the permission
            }
        }


    }

    private void letsdoSomeNetworking(RequestParams params) {
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(WEATHER_URL, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                Toast.makeText(CenterActivity.this, "Data Get Success", Toast.LENGTH_SHORT).show();

                WeatherData weatherD = WeatherData.fromJson(response);
                updateUI(weatherD);
                //super.onSuccess(statusCode, headers, response);
            }


            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                //super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });
    }

    private void updateUI(WeatherData weather) {

        WeatherTemp.setText(weather.getWeatherTemp());
        WeatherCity.setText(weather.getWeatherCity());
        WeatherType.setText(weather.getWeatherType());
        int recourseID = getResources().getIdentifier(weather.getWeatherIcon(), "drawable", getPackageName());
        WeatherIcon.setImageResource(recourseID);

    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mLocationManager != null) {
            mLocationManager.removeUpdates(mLocationListener);
        }
    }


    void setState(int state) {
        switch (state) {
            case 0:
                state = 0;
                mainBarChart.setVisibility(View.VISIBLE);
                mainBarChart.animateY(300);
                mainLineChart.setVisibility(View.INVISIBLE);
                mainScatterChart.setVisibility(View.INVISIBLE);
                break;

            case 1:
                state = 1;
                mainBarChart.setVisibility(View.INVISIBLE);
                mainLineChart.setVisibility(View.VISIBLE);
                mainLineChart.animateY(300);
                mainScatterChart.setVisibility(View.INVISIBLE);
                break;

            case 2:
                state = 2;
                mainBarChart.setVisibility(View.INVISIBLE);
                mainLineChart.setVisibility(View.INVISIBLE);
                mainScatterChart.setVisibility(View.VISIBLE);
                mainScatterChart.animateY(300);
                break;


        }
    }

    void drawChart() {
        BarDataSet barDataSet = new BarDataSet(dataBarEntry, "");
        LineDataSet lineDataSet = new LineDataSet(dataEntry, "");
        ScatterDataSet scatterDataSet = new ScatterDataSet(dataEntry, "");

        barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        barDataSet.setValueTextColor(Color.BLACK);
        barDataSet.setValueTextSize(16f);

        lineDataSet.setColor(Color.BLACK);
        lineDataSet.setValueTextColor(Color.BLACK);
        lineDataSet.setValueTextSize(16f);

        scatterDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        scatterDataSet.setValueTextColor(Color.BLACK);
        scatterDataSet.setValueTextSize(16f);

        BarData barData = new BarData(barDataSet);
        LineData lineData = new LineData(lineDataSet);
        ScatterData scatterData = new ScatterData(scatterDataSet);

        mainScatterChart.setData(scatterData);
        mainBarChart.setData(barData);
        mainLineChart.setData(lineData);

        mainBarChart.setFitBars(true);
        setState(0);
    }

}