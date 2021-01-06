package com.example.trackerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

public class Ratings extends AppCompatActivity {

    TextView rateCount_sleep, showRating_sleep, rateCount_feeling, showRating_feeling, rateCount_mood, showRating_mood;
    EditText review_sleep, review_feeling, review_mood;
    Button submit_sleep, submit_feeling, submit_mood;
    RatingBar ratingBar_sleep, ratingBar_feeling, ratingBar_mood;
    float rateValue_sleep; String temp_sleep;
    float rateValue_feeling; String temp_feeling;
    float rateValue_mood; String temp_mood;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ratings);


        //Sleep ratings
        rateCount_sleep = findViewById(R.id.rateCount_sleep);
        ratingBar_sleep = findViewById(R.id.ratingBar_sleep);
        review_sleep = findViewById(R.id.review_sleep);
        submit_sleep = findViewById(R.id.submit_sleep);
        showRating_sleep = findViewById(R.id.showRating_sleep);

        ratingBar_sleep.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {

                rateValue_sleep = ratingBar_sleep.getRating();

                if (rateValue_sleep<=1 && rateValue_sleep>0)
                    rateCount_sleep.setText("Bad " +rateValue_sleep +"/5");
                else if (rateValue_sleep<=2 && rateValue_sleep>1)
                    rateCount_sleep.setText("OK " +rateValue_sleep +"/5");
                else if (rateValue_sleep<=3 && rateValue_sleep>2)
                    rateCount_sleep.setText("Good " +rateValue_sleep +"/5");
                else if (rateValue_sleep<=4 && rateValue_sleep>3)
                    rateCount_sleep.setText("Very good " +rateValue_sleep +"/5");
                else if (rateValue_sleep<=5 && rateValue_sleep>4)
                    rateCount_sleep.setText("Best " +rateValue_sleep +"/5");
            }
        });

        submit_sleep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                temp_sleep = rateCount_sleep.getText().toString();
                showRating_sleep.setText("Your Rating: \n" +temp_sleep +"\n" +review_sleep.getText());
                review_sleep.setText("");
                ratingBar_sleep.setRating(0);
                rateCount_sleep.setText("");
            }
        });

        //Feeling ratings
        rateCount_feeling = findViewById(R.id.rateCount_feeling);
        ratingBar_feeling = findViewById(R.id.ratingBar_feeling);
        review_feeling = findViewById(R.id.review_feeling);
        submit_feeling = findViewById(R.id.submit_feeling);
        showRating_feeling = findViewById(R.id.showRating_feeling);

        ratingBar_sleep.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {

                rateValue_feeling = ratingBar_feeling.getRating();

                if (rateValue_feeling<=1 && rateValue_feeling>0)
                    rateCount_feeling.setText("Bad " +rateValue_feeling +"/5");
                else if (rateValue_feeling<=2 && rateValue_feeling>1)
                    rateCount_feeling.setText("OK " +rateValue_feeling +"/5");
                else if (rateValue_feeling<=3 && rateValue_feeling>2)
                    rateCount_feeling.setText("Good " +rateValue_feeling +"/5");
                else if (rateValue_feeling<=4 && rateValue_feeling>3)
                    rateCount_feeling.setText("Very good " +rateValue_feeling +"/5");
                else if (rateValue_feeling<=5 && rateValue_feeling>4)
                    rateCount_feeling.setText("Best " +rateValue_feeling +"/5");
            }
        });

        submit_sleep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                temp_feeling = rateCount_feeling.getText().toString();
                showRating_feeling.setText("Your Rating: \n" +temp_feeling +"\n" +review_feeling.getText());
                review_feeling.setText("");
                ratingBar_feeling.setRating(0);
                rateCount_feeling.setText("");
            }
        });


        //Mood ratings
        rateCount_mood = findViewById(R.id.rateCount_mood);
        ratingBar_mood = findViewById(R.id.ratingBar_mood);
        review_mood = findViewById(R.id.review_mood);
        submit_mood = findViewById(R.id.submit_mood);
        showRating_mood = findViewById(R.id.showRating_mood);

        ratingBar_sleep.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {

                rateValue_mood = ratingBar_mood.getRating();

                if (rateValue_mood<=1 && rateValue_mood>0)
                    rateCount_mood.setText("Bad " +rateValue_mood +"/5");
                else if (rateValue_mood<=2 && rateValue_mood>1)
                    rateCount_mood.setText("OK " +rateValue_mood +"/5");
                else if (rateValue_mood<=3 && rateValue_mood>2)
                    rateCount_mood.setText("Good " +rateValue_mood +"/5");
                else if (rateValue_mood<=4 && rateValue_mood>3)
                    rateCount_mood.setText("Very good " +rateValue_mood +"/5");
                else if (rateValue_mood<=5 && rateValue_mood>4)
                    rateCount_mood.setText("Best " +rateValue_mood +"/5");
            }
        });

        submit_sleep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                temp_mood = rateCount_mood.getText().toString();
                showRating_mood.setText("Your Rating: \n" +temp_mood +"\n" +review_mood.getText());
                review_mood.setText("");
                ratingBar_mood.setRating(0);
                rateCount_mood.setText("");
            }
        });


    }
}