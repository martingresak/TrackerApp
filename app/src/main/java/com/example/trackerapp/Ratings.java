package com.example.trackerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

public class Ratings extends AppCompatActivity {

    float x1, x2, y1, y2;

    TextView showRating_sleep, showRating_exercise, rateCount_mood, showRating_mood;
    EditText review_sleep, review_exercise, review_mood;
    Button submit_sleep, submit_exercise, submit_mood;
    RatingBar ratingBar_mood;
    String db_sleep, db_mood, db_exercise, userID, curentDate;
    float rateValue_mood; String temp_mood;
    private DatabaseReference userDBref;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ratings);

        //Database connect
        mAuth = FirebaseAuth.getInstance();
        userID = mAuth.getUid();
        DateFormat formatter = new SimpleDateFormat("ddMMyyyy");
        curentDate = formatter.format(new Date());
        userDBref = FirebaseDatabase.getInstance("https://trackerapp-emp-default-rtdb.europe-west1.firebasedatabase.app/").getReference().child(userID).child(curentDate);


        //Mood ratings
        rateCount_mood = findViewById(R.id.rateCount_mood);
        ratingBar_mood = findViewById(R.id.ratingBar_mood);
        review_mood = findViewById(R.id.review_mood);
        submit_mood = findViewById(R.id.submit_mood);
        showRating_mood = findViewById(R.id.showRating_mood);

        //Sleep input
        review_sleep = findViewById(R.id.review_sleep);
        submit_sleep = findViewById(R.id.submit_sleep);
        showRating_sleep = findViewById(R.id.showRating_sleep);

        //Exercise input
        review_exercise = findViewById(R.id.review_exercise);
        submit_exercise = findViewById(R.id.submit_exercise);
        showRating_exercise = findViewById(R.id.showRating_exercise);


        ratingBar_mood.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
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

        submit_mood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                temp_mood = rateCount_mood.getText().toString();
                showRating_mood.setText("Your mood Rating: \n" +temp_mood +"\n" +review_mood.getText());
                review_mood.setText("");
                ratingBar_mood.setRating(0);
                rateCount_mood.setText("");
                db_mood = rateCount_mood.getText().toString();
                userDBref.child("mood").setValue(Integer.parseInt(db_mood));
            }
        });

        submit_sleep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRating_sleep.setText("Your Sleep: \n" +review_sleep.getText() +" hours");
                db_sleep = review_sleep.getText().toString();
                review_sleep.setText("");
                userDBref.child("sleep").setValue(Integer.parseInt(db_sleep));
            }
        });

        submit_exercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRating_exercise.setText("Your Exercise: \n" +review_exercise.getText() +" minutes");
                db_exercise = review_exercise.getText().toString();
                review_exercise.setText("");
                userDBref.child("exercise").setValue(Integer.parseInt(db_exercise));
            }
        });
        Button homeBtn = (Button)findViewById(R.id.ratings_home_btn);

        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (Ratings.this, CenterActivity.class);
                startActivity(intent);

            }
        });


    }

}