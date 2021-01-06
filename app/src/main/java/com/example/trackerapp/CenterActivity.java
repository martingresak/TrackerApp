package com.example.trackerapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.BarLineChartBase;
import com.github.mikephil.charting.charts.CandleStickChart;
import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.ScatterChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.CandleData;
import com.github.mikephil.charting.data.CandleDataSet;
import com.github.mikephil.charting.data.CandleEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.RadarDataSet;
import com.github.mikephil.charting.data.RadarEntry;
import com.github.mikephil.charting.data.ScatterData;
import com.github.mikephil.charting.data.ScatterDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.material.color.MaterialColors;
import com.google.android.material.slider.RangeSlider;
import com.google.android.material.slider.Slider;

import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class CenterActivity extends AppCompatActivity {

    Slider typeSlider;
    BarChart mainBarChart;
    LineChart mainLineChart;
    ScatterChart mainScatterChart;
    ArrayList<BarLineChartBase> charts;
    Button profile;

    ArrayList<Entry> sleep; //teeeeeemp!!!!!!
    ArrayList<BarEntry> barSleep; //temp as well

    static int state;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_center);
        charts = new ArrayList<>();

        typeSlider = findViewById(R.id.typeSlider);

        mainBarChart = findViewById(R.id.mainBarChart);
        mainLineChart = findViewById(R.id.mainLineChart);
        mainScatterChart = findViewById(R.id.mainScatterChart);

        charts.add(mainBarChart);
        charts.add(mainLineChart);
        charts.add(mainScatterChart);

        profile = findViewById(R.id.profile);
        profile.setOnClickListener(this::onClick);

        //temp list remove pls

        sleep = new ArrayList<>();
        sleep.add(new Entry(0,60*6));
        sleep.add(new Entry(1,60*7));
        sleep.add(new Entry(2,60*8));
        sleep.add(new Entry(3,60*4));
        sleep.add(new Entry(4,60*6));

        barSleep = new ArrayList<>();

        for(int i = 0; i < sleep.size(); i++) {
            float x = sleep.get(i).getX();
            float y = sleep.get(i).getY();
            barSleep.add(new BarEntry(x,y));
        }

        //end of temp

        typeSlider.addOnChangeListener((slider, value, fromUser) -> {
            int v = (int) (value);
            setState(v);
        });

        drawChart();




    }

    public void onClick(View v) {
        int i = v.getId();

        if(i == R.id.profile) {
            Intent intent =
                     new Intent(CenterActivity.this, UserProfile.class);
            startActivity(intent);
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
        BarDataSet barDataSet = new BarDataSet(barSleep, "");
        LineDataSet lineDataSet = new LineDataSet(sleep, "");
        ScatterDataSet scatterDataSet = new ScatterDataSet(sleep, "");

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