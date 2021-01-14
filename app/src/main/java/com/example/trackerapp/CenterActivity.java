package com.example.trackerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.Semaphore;

public class CenterActivity extends AppCompatActivity {

    Slider typeSlider;
    BarChart mainBarChart;
    LineChart mainLineChart;
    ScatterChart mainScatterChart;
    ArrayList<BarLineChartBase> charts;

    float x1, x2, y1, y2;

    ArrayList<Entry> dataEntry; //teeeeeemp!!!!!!
    ArrayList<BarEntry> dataBarEntry; //temp as well
    ArrayList<Long> data;

    static int state;

    String userID;

    private FirebaseAuth mAuth;
    private DatabaseReference ref;

    String test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_center);
        mAuth = FirebaseAuth.getInstance();
        userID = mAuth.getUid();


        charts = new ArrayList<>();

        typeSlider = findViewById(R.id.typeSlider);
        mainBarChart = findViewById(R.id.mainBarChart);
        mainLineChart = findViewById(R.id.mainLineChart);
        mainScatterChart = findViewById(R.id.mainScatterChart);

        charts.add(mainBarChart);
        charts.add(mainLineChart);
        charts.add(mainScatterChart);



        data = new ArrayList<>();




        typeSlider.addOnChangeListener((slider, value, fromUser) -> {
            int v = (int) (value);
            setState(v);
        });








    }

    protected void onResume() {

        super.onResume();

        dataEntry = new ArrayList<>();
        dataBarEntry = new ArrayList<>();


        ref = FirebaseDatabase.getInstance("https://trackerapp-emp-default-rtdb.europe-west1.firebasedatabase.app/").getReference().child(userID);
        ref.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Map<String, Object> days = (Map<String, Object>) dataSnapshot.getValue();

                        for (Map.Entry<String, Object> entry : days.entrySet()) {

                            //Get user map
                            Map singleUser = (Map) entry.getValue();
                            //Get phone field and append to list
                            data.add((Long) singleUser.get("sleep"));
                            for (int i = 0; i < data.size(); i++) {
                                dataEntry.add(new Entry(i, data.get(i)));
                                dataBarEntry.add(new BarEntry(i, data.get(i)));
                            }

                            drawChart();

                        }


                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        //handle databaseError
                    }
                });


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

    public boolean onTouchEvent(MotionEvent touchEvent){
        switch(touchEvent.getAction()){
            case MotionEvent.ACTION_DOWN:
                x1 = touchEvent.getX();
                y1 = touchEvent.getY();
                break;
            case MotionEvent.ACTION_UP:
                x2 = touchEvent.getX();
                y2 = touchEvent.getY();
                if(x1 <  x2){
                Intent i = new Intent(CenterActivity.this, Ratings.class);
                startActivity(i);
            }else if(x1 >  x2){
                Intent i = new Intent(CenterActivity.this, UserProfile.class);
                startActivity(i);
            }
            break;
        }
        return false;
    }
}