package com.example.trackerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class centerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_center);

        BarChart mainStatChart = findViewById(R.id.mainStatChart);
        

        ArrayList<BarEntry> sleep = new ArrayList<>();
        sleep.add(new BarEntry(0,60*6));
        sleep.add(new BarEntry(1,60*7));
        sleep.add(new BarEntry(2,60*8));
        sleep.add(new BarEntry(3,60*4));
        sleep.add(new BarEntry(4,60*6));

        BarDataSet barDataSet = new BarDataSet(sleep, "Sleep");
        barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        barDataSet.setValueTextColor(Color.BLACK);
        barDataSet.setValueTextSize(16f);

        BarData barData = new BarData(barDataSet);

        mainStatChart.setFitBars(true);
        mainStatChart.setData(barData);
        mainStatChart.getDescription().setText("Example");
        mainStatChart.animateY(500);

    }
}