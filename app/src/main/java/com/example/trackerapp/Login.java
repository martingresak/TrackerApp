package com.example.trackerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;

public class Login extends AppCompatActivity {

    Button callSignUp, login_btn;
    ImageView image;
    TextView logoText, sloganText;
    TextInputLayout username, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);


        //Hooks
        callSignUp = findViewById(R.id.signup_screen);
        image = findViewById(R.id.login_image);
        sloganText = findViewById(R.id.login_slogan);
        logoText = findViewById(R.id.login_signin);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        login_btn = findViewById(R.id.login_button);


        callSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, SignUp.class);

                Pair[] pairs = new Pair[7];

                pairs[0] = new Pair<View,String>(image,"signup_image");
                pairs[1] = new Pair<View,String>(sloganText,"signup_slogan");
                pairs[2] = new Pair<View,String>(logoText,"signup_text");
                pairs[3] = new Pair<View,String>(username,"username");
                pairs[4] = new Pair<View,String>(password,"password");
                pairs[5] = new Pair<View,String>(login_btn,"signup_button");
                pairs[6] = new Pair<View,String>(callSignUp,"back_button");

                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Login.this, pairs);
                startActivity(intent, options.toBundle());

            }
        });
    }
}